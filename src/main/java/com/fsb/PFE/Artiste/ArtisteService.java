package com.fsb.PFE.Artiste;


import com.fsb.PFE.UtilisateurSimple.FollowedSimpleUserDTO;
import com.fsb.PFE.UtilisateurSimple.UtilisateurSimple;
import com.fsb.PFE.UtilisateurSimple.UtilisateurSimpleRepository;
import com.fsb.PFE.authentication.dao.RoleDao;
import com.fsb.PFE.authentication.dao.UserDao;
import com.fsb.PFE.authentication.entity.User;
import com.fsb.PFE.mailSender.MailService;
import com.fsb.PFE.verificationCode.CodeRepo;
import com.fsb.PFE.verificationCode.CodeService;
import com.fsb.PFE.verificationCode.CodeVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ArtisteService implements ArtisteServiceImpl{
    @Autowired private ArtisteRepo artisteRepo;
    @Autowired private RoleDao roleDao;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private MailService mailService;
    @Autowired private CodeService codeService;
    @Autowired private CodeRepo codeRepo;
    @Autowired private UserDao userDao;



    private Timer timer;


    @Override
    public Artiste addArtiste(Artiste artiste) {
        artiste.getRoles().add(roleDao.findRoleByRoleName("Artiste"));
        artiste.setUserPassword(getEncodedPassword(artiste.getUserPassword()));
        artiste.setId_abonnement(UUID.randomUUID().toString());
        String s = codeService.GenerateCode();
        CodeVerification code = new CodeVerification(artiste.getUserName(), s);
        codeService.saveCode(code);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (codeRepo.existsById(artiste.getUserName())){
                    if (Objects.equals(codeService.getCode(artiste.getUserName()).getCode(), s)){
                        System.out.println("//////////////////////////////////");
                        codeService.delete(artiste.getUserName());
                    }
                }
            }
        },   3 * 60 * 1000); // set the timer for 3 minutes
        ResponseEntity<Boolean> sent = mailService.sendMailToCustomer(Integer.parseInt(s));
        if (Boolean.TRUE.equals(sent.getBody())){
            return artiste;
        }
        return null;
    }

    public void saveArtiste(Artiste artiste,String code) {
        System.out.println("//////////////////////////////////"+artiste);
        if (codeRepo.existsById(artiste.getUserName())){

            if (codeService.getCode(artiste.getUserName()).getCode().equals(String.valueOf(code))){
                artiste.setUserPassword(getEncodedPassword(artiste.getUserPassword()));
                artiste.getRoles().add(roleDao.findRoleByRoleName("Artiste"));
                artisteRepo.save(artiste);
                codeService.delete(artiste.getUserName());
            }else {
                throw new RuntimeException("verification code false");
            }
        }else {
            throw new RuntimeException("verification code expired");
        }
    }



    @Override
    public void resendCode(String artisteId) {
        String k = codeService.GenerateCode();
        CodeVerification code = new CodeVerification(artisteId, k);
        codeService.saveCode(code);
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                codeService.delete(artisteId);
            }
        }, 3 * 60 * 1000); // set the timer for 3 minutes
        mailService.sendMailToCustomer(Integer.parseInt(k));
    }




    @Override
    public void updateArtiste(Artiste artiste) {
        Artiste art = artisteRepo.findArtisteByUserName(artiste.getUserName());
        art.setName(artiste.getName());
        art.setLastName(artiste.getLastName());
        art.setUserPassword(artiste.getUserPassword());
        art.setSpecialite(artiste.getSpecialite());
        art.setDescription(artiste.getDescription());
        art.setCin(art.getCin());
        artiste.setId_abonnement(artiste.getId_abonnement());
        artisteRepo.save(art);
    }

    @Override
    public Following getAllFollowing() {
        Set<FollowedArtistDTO> followedArtistDTOS =new HashSet<>();
        Set<FollowedSimpleUserDTO> followedSimpleUserDTOS =new HashSet<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Artiste artiste = artisteRepo.findArtisteByUserName(auth.getName());
            artiste.getFollowing().forEach(user -> {
                if (user.getRoles().contains(roleDao.findRoleByRoleName("Artiste"))){
                    followedArtistDTOS.add(mapArtisteToArtisteDTO((Artiste) user));
                } else {
                    System.out.println("/////////////////////////"+user);
                    followedSimpleUserDTOS.add(mapToFollowedSimpleUserDTO((UtilisateurSimple) user));
                }
            });
        return new Following(followedArtistDTOS,followedSimpleUserDTOS);
    }

    @Override
    public Artiste getArtiste(String id) {
        return artisteRepo.findArtisteByUserName(id);
    }

    @Override
    public List<Artiste> getAllArtiste() {
        List<Artiste> list = artisteRepo.findAll();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        list.remove(artisteRepo.findArtisteByUserName(auth.getName()));
        List<String> listFollowingUserName = new ArrayList<>();
        Artiste artiste = artisteRepo.findArtisteByUserName(auth.getName());
        artiste.getFollowing().forEach(user -> {
            listFollowingUserName.add(user.getUserName());
        });
        list.removeIf(user -> listFollowingUserName.contains(user.getUserName()));
        return list;
    }

    @Override
    public void deleteArtiste(Integer id) {
        artisteRepo.deleteById(id);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    FollowedSimpleUserDTO mapToFollowedSimpleUserDTO(UtilisateurSimple utilisateurSimple){
        FollowedSimpleUserDTO simpleUserDTO = new FollowedSimpleUserDTO();
        simpleUserDTO.setName(utilisateurSimple.getName());
        simpleUserDTO.setLastName(utilisateurSimple.getLastName());
        simpleUserDTO.setProfileImg(utilisateurSimple.getProfileImg());
        simpleUserDTO.setProfileId(utilisateurSimple.getProfileId());
        simpleUserDTO.setUserName(utilisateurSimple.getUserName());
        return simpleUserDTO;
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

    @Override
    public void follow(String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userArtist = userDao.findById(auth.getName()).get();
        User userSimple = userDao.findById(id).get();
        if (!Objects.equals(userArtist.getUserName(), userSimple.getUserName())){
            if (!userArtist.getFollowing().contains(userSimple)){
                userArtist.getFollowing().add(userSimple);
            }
        }
        userDao.save(userArtist);
        }


    @Override
    public Following getAllFollowers() {
        Set<FollowedArtistDTO> followedArtistDTOS =new HashSet<>();
        Set<FollowedSimpleUserDTO> followedSimpleUserDTOS =new HashSet<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Artiste artiste = artisteRepo.findArtisteByUserName(auth.getName());
        artiste.getFollower().forEach(user -> {
            if (user.getRoles().contains(roleDao.findRoleByRoleName("Artiste"))){
                followedArtistDTOS.add(mapArtisteToArtisteDTO((Artiste) user));
            } else {
                System.out.println("/////////////////////////"+user);
                followedSimpleUserDTOS.add(mapToFollowedSimpleUserDTO((UtilisateurSimple) user));
            }
        });
        return new Following(followedArtistDTOS,followedSimpleUserDTOS);
    }

    @Override
    public void unfollow(String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findById(auth.getName()).get();
        User unfollowedUser = userDao.findByProfileId(id);
        user.getFollowing().remove(unfollowedUser);
        unfollowedUser.getFollower().remove(user);
        userDao.save(unfollowedUser);
        userDao.save(user);
    }

    @Override
    public void deleteFollower(String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findById(auth.getName()).get();
        User unfollowedUser = userDao.findByProfileId(id);
        user.getFollower().remove(unfollowedUser);
        unfollowedUser.getFollowing().remove(user);
        userDao.save(user);
        userDao.save(unfollowedUser);
    }


}
