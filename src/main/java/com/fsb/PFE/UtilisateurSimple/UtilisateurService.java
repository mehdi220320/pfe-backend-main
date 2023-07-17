package com.fsb.PFE.UtilisateurSimple;

import com.fsb.PFE.Artiste.Artiste;
import com.fsb.PFE.Artiste.ArtisteRepo;
import com.fsb.PFE.Artiste.FollowedArtistDTO;
import com.fsb.PFE.authentication.dao.RoleDao;
import com.fsb.PFE.authentication.dao.UserDao;
import com.fsb.PFE.authentication.entity.User;
import com.fsb.PFE.mailSender.MailService;
import com.fsb.PFE.shared.publication.Publication;
import com.fsb.PFE.verificationCode.CodeRepo;
import com.fsb.PFE.verificationCode.CodeVerification;
import com.fsb.PFE.verificationCode.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class  UtilisateurService implements UtilisateurImpService{


    @Autowired private UtilisateurSimpleRepository utilisateurSimpleRepository;
    @Autowired private RoleDao roleDao;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private MailService mailService;
    @Autowired private CodeService codeService;
    @Autowired private CodeRepo codeRepo;
    @Autowired private UserDao userDao;
    private Timer timer;


    @Override
    public UtilisateurSimple add( UtilisateurSimple user) {
        user.getRoles().add(roleDao.findRoleByRoleName("User"));
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        String k = codeService.GenerateCode();
        CodeVerification code =new CodeVerification(user.getUserName(),k);
        codeService.saveCode(code);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                codeService.delete(user.getUserName());
                timer.cancel();
            }
        },  3 * 60 * 1000); // set the timer for 3 minutes
        ResponseEntity<Boolean> sent = mailService.sendMailToCustomer(Integer.parseInt(k));
        if (Boolean.TRUE.equals(sent.getBody())){
            return user;
        }
        return null;
    }

    public void saveUtilisateur(UtilisateurSimple user, String codeVerf) {
        if (codeRepo.existsById(user.getUserName())){
            System.out.println("code is "+ codeVerf);

            if (codeService.getCode(user.getUserName()).getCode().equals(String.valueOf(codeVerf))){
                user.setUserPassword(getEncodedPassword(user.getUserPassword()));
                user.getRoles().add(roleDao.findRoleByRoleName("User"));
                utilisateurSimpleRepository.save(user);
                codeService.delete(user.getUserName());
            }else {
                throw new RuntimeException("verification code false");
            }
        }else {
            throw new RuntimeException("verification code expired");
        }
    }

    @Override
    public void resendCode(String utilisateurId) {
        String k = codeService.GenerateCode();
        CodeVerification code = new CodeVerification(utilisateurId, k);
        codeService.saveCode(code);
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                codeService.delete(utilisateurId);
            }
        }, 3 * 60 * 1000); // set the timer for 3 minutes
        mailService.sendMailToCustomer(Integer.parseInt(k));
    }




    @Override
    public List<UtilisateurSimple> getAll(){
        return utilisateurSimpleRepository.findAll();
    }

    @Override
    public UtilisateurSimple update(UtilisateurSimple user) {
        UtilisateurSimple ur = utilisateurSimpleRepository.findByUserName(user.getUserName());
        ur.setName(user.getName());
        ur.setLastName(user.getLastName());
        ur.setUserPassword(user.getUserPassword());
        return utilisateurSimpleRepository.save(ur);

    }
    @Override
    public UtilisateurSimple getById(String userName){
        return utilisateurSimpleRepository.findByUserName(userName);
    }



    @Override
    public void delete(String id){utilisateurSimpleRepository.deleteById(id);}

    @Override
    public Set<FollowedArtistDTO> getAllFollowing() {
        Set<FollowedArtistDTO> followedArtistDTOS = new HashSet<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().iterator().next().toString().equals("ROLE_User")){
            UtilisateurSimple user = utilisateurSimpleRepository.findByUserName(auth.getName());
            user.getFollowing().forEach(artiste -> {
                followedArtistDTOS.add(mapArtisteToArtisteDTO((Artiste) artiste));
            });
        }
        return followedArtistDTOS;
    }

    @Override
    public Set<FollowedArtistDTO> getAllFollowers() {
        Set<FollowedArtistDTO> followedArtistDTOS = new HashSet<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().iterator().next().toString().equals("ROLE_User")){
            UtilisateurSimple user = utilisateurSimpleRepository.findByUserName(auth.getName());
            user.getFollower().forEach(artiste -> {
                followedArtistDTOS.add(mapArtisteToArtisteDTO((Artiste) artiste));
            });
        }
        return followedArtistDTOS;
    }



    @Override
    public List<Publication> getFollowingPosts() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findById(auth.getName()).get();
        List<Publication> publications = new ArrayList<>();
        user.getFollowing().forEach(artiste -> {
            publications.addAll(artiste.getPublications());
        });
        return publications;
    }

    @Override
    public void unfollow(String profileId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UtilisateurSimple user = utilisateurSimpleRepository.findByUserName(auth.getName());
        Artiste artiste = (Artiste) user.getFollowing().stream().filter(artiste1 -> artiste1.getProfileId().equals(profileId)).findFirst().get();
        user.getFollowing().remove(artiste);
        artiste.getFollower().remove(user);
        userDao.save(artiste);
        utilisateurSimpleRepository.save(user);
    }

    @Override
    public void deleteFollower(String profileId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UtilisateurSimple user = utilisateurSimpleRepository.findByUserName(auth.getName());
        Artiste artiste = (Artiste) user.getFollower().stream().filter(artiste1 -> artiste1.getProfileId().equals(profileId)).findFirst().get();
        user.getFollower().remove(artiste);
        artiste.getFollowing().remove(user);
        userDao.save(artiste);
        utilisateurSimpleRepository.save(user);
    }

    FollowedArtistDTO mapArtisteToArtisteDTO(Artiste artiste){
        FollowedArtistDTO artisteDTO = new FollowedArtistDTO();
        artisteDTO.setName(artiste.getName());
        artisteDTO.setLastName(artiste.getLastName());
        artisteDTO.setProfileImg(artiste.getProfileImg());
        artisteDTO.setProfileId(artiste.getProfileId());
        artisteDTO.setSpecialite(artiste.getSpecialite());
        artisteDTO.setTelephone(artiste.getTelephone());
        return artisteDTO;
    }



    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

}