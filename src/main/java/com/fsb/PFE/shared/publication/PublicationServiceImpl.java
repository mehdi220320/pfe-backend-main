package com.fsb.PFE.shared.publication;

import com.fsb.PFE.Artiste.ArtisteService;
import com.fsb.PFE.UtilisateurSimple.UtilisateurService;
import com.fsb.PFE.UtilisateurSimple.UtilisateurSimple;
import com.fsb.PFE.UtilisateurSimple.UtilisateurSimpleRepository;
import com.fsb.PFE.authentication.dao.UserDao;
import com.fsb.PFE.authentication.entity.User;
import com.fsb.PFE.authentication.entity.UserDTO;
import com.fsb.PFE.shared.contenue_publication.ContenuePub;
import com.fsb.PFE.shared.contenue_publication.ContenuePubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PublicationServiceImpl implements PublicationService{
    @Autowired PublicationRepo publicationRepo;
    @Autowired ArtisteService artisteService;
    @Autowired UtilisateurService utilisateurService;
    @Autowired ContenuePubService contenuePubService;
    @Autowired UtilisateurSimpleRepository utilisateurSimpleRepository;
    @Autowired
    UserDao userDao;


    @Override
    @Transactional
    public void addPublication(Publication publication) {
        contenuePubService.addContenuePub(publication.getContenu());
        publication.setDate(new Date());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("publication : "+publication);
        if (auth.getAuthorities().toArray()[0].toString().equals("ROLE_Artiste"))
        {
           publication.setOwner(auth.getName());
            userDao.findById(auth.getName()).ifPresent(user -> {
                user.getPublications().add(publication);
                userDao.save(user);
            });
        }
        else if (auth.getAuthorities().toArray()[0].toString().equals("ROLE_User"))
        {
            publication.setOwner(auth.getName());
            userDao.findById(auth.getName()).ifPresent(user -> {
                user.getPublications().add(publication);
                userDao.save(user);
            });

        }
        publicationRepo.save(publication);
    }


    @Override
    public void deletePublication(int idPublication) {
        publicationRepo.deleteById(idPublication);
    }

    @Override
    public List<PublicationDTO> getAllPublications() {
        List<PublicationDTO> publications = new ArrayList<>();
        for (Publication p : publicationRepo.findAll()
             ) {
           publications.add(mapPublicationToDTO(p));
        }

        return publications;
    }

    @Override
    public PublicationDTO getPublicationById(int id) {
        return  mapPublicationToDTO(publicationRepo.findById(id).get());
    }


    @Override
    public List<PublicationDTO> getPublicationsByOwner(String idOwner) {
        List<PublicationDTO> publications = new ArrayList<>();
        for (Publication p : publicationRepo.findAllByOwner(idOwner)
             ) {
            publications.add(mapPublicationToDTO(p));
        }
        return publications;
    }


    @Override
    public List<PublicationDTO> getPublicationsByProfileId(String profileId) {
        return getPublicationsByOwner(userDao.findByProfileId(profileId).getUserName());
    }


    UserDTO mapUserToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(user.getUserName());
        userDTO.setName(user.getName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUserPassword(user.getUserPassword());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }

    PublicationDTO mapPublicationToDTO(Publication publication){
        PublicationDTO publicationDTO = new PublicationDTO();
        publicationDTO.setId(publication.getId());
        publicationDTO.setContenu(publication.getContenu());
        publicationDTO.setDate(publication.getDate());
        //publicationDTO.setOwner(mapUserToDTO(publication.getOwner()));
        return publicationDTO;
    }


}
