package com.fsb.PFE.authentication.service;

import com.fsb.PFE.Artiste.Artiste;
import com.fsb.PFE.UtilisateurSimple.UtilisateurSimple;
import com.fsb.PFE.UtilisateurSimple.UtilisateurSimpleRepository;
import com.fsb.PFE.authentication.dao.RoleDao;
import com.fsb.PFE.authentication.dao.UserDao;
import com.fsb.PFE.authentication.entity.Role;
import com.fsb.PFE.authentication.entity.User;
import com.fsb.PFE.shared.contenue_publication.ContenuePub;
import com.fsb.PFE.shared.contenue_publication.ContenuePubRepo;
import com.fsb.PFE.shared.contenue_publication.likes.Like;
import com.fsb.PFE.shared.contenue_publication.likes.LikeRepo;
import com.fsb.PFE.shared.notification.Notification;
import com.fsb.PFE.shared.notification.NotificationRepo;
import com.fsb.PFE.shared.notification.NotificationService;
import com.fsb.PFE.shared.publication.Publication;
import com.fsb.PFE.shared.publication.PublicationRepo;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired private UserDao userDao;
    @Autowired private RoleDao roleDao;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private ContenuePubRepo contenuePubRepo;
    @Autowired private PublicationRepo publicationRepo;
    @Autowired private NotificationRepo notificationRepo;
    @Autowired private NotificationService notificationService;


    public User registerNewUser(User user) {
        Role role = roleDao.findById("User").get();
        System.out.println(role);
        Set roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        return userDao.save(user);
    }

    public void deleteUser(String id) {
        userDao.deleteById(id);
    }

    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return username;
        } else {
            String username = principal.toString();
            return username;
        }

    }


    public void initRolesAndUser() {
        Role artistRole=new Role();
        artistRole.setRoleName("Artiste");
        artistRole.setRoleDescription("owner of the art");
        roleDao.save(artistRole);

        Role userRole=new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("simple user");
        roleDao.save(userRole);


        Notification notif1 = new Notification();
        notif1.setDescription("poizejpifpziehfpehfkjzhelkfhlezkfhk");
        notif1.setDate(new Date());
        notif1.setImg("https://img.freepik.com/free-psd/3d-illustration-person-with-sunglasses_23-2149436188.jpg?w=2000");
        notificationRepo.save(notif1);

        Notification notif2 = new Notification();
        notif2.setDescription("kjzefhlizehfiuhzeiufhiuezh");
        notif2.setDate(new Date());
        notif2.setImg("https://www.getillustrations.com/photos/pack/3d-avatar-male_lg.png");
        notificationRepo.save(notif2);

        Notification notif3 = new Notification();
        notif3.setDescription("lifherfuheriyfhoh");
        notif3.setDate(new Date());
        notif3.setImg("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcThK4o_a5e4nh4TV71gsvweOxj2rkj39r3d2w&usqp=CAU");
        notificationRepo.save(notif3);



        Artiste artistUser = new Artiste();
        artistUser.setUserName("oussa13@gmail.com");
        artistUser.setName("oussama");
        artistUser.setLastName("saoudi");
        artistUser.setUserPassword(getEncodedPassword("oussami2923"));
        artistUser.setSpecialite("rmùkfrlkf");
        artistUser.setDescription("lkjemfkjrjf");
        artistUser.setCin(12345678);
        artistUser.setId_abonnement("jmlfjlrkjfm");
        artistUser.setTelephone("21299975");
        artistUser.setProfileImg("https://cliply.co/wp-content/uploads/2020/09/442008571_ARTIST_AVATAR_3D_400.png");
        Set<Role> ownerRoles = new HashSet<>();
        ownerRoles.add(artistRole);
        artistUser.setRoles(ownerRoles);
        artistUser.getNotifications().add(notif1);
        artistUser.getNotifications().add(notif2);
        artistUser.getNotifications().add(notif3);
        userDao.save(artistUser);

        Artiste artistUser1 = new Artiste();
        artistUser1.setUserName("moham@gmail.com");
        artistUser1.setName("mohamed");
        artistUser1.setLastName("mak");
        artistUser1.setUserPassword(getEncodedPassword("medmak2923"));
        artistUser1.setSpecialite("zefkhfez");
        artistUser1.setDescription("hazzan t7iin");
        artistUser1.setCin(65659898);
        artistUser1.setId_abonnement("kjfeljflzfjl");
        artistUser1.setTelephone("85858965");
        artistUser1.setProfileImg("https://i.pinimg.com/736x/55/53/f3/5553f39e670eba8fa1395f81aa71c4a5.jpg");
        Set<Role> ownerRoles1 = new HashSet<>();
        ownerRoles1.add(artistRole);
        artistUser1.setRoles(ownerRoles);
        userDao.save(artistUser1);

        Artiste artistUser2 = new Artiste();
        artistUser2.setUserName("soha@gmail.com");
        artistUser2.setName("souhail");
        artistUser2.setLastName("abdoo");
        artistUser2.setUserPassword(getEncodedPassword("souhailgta5"));
        artistUser2.setSpecialite("le3b");
        artistUser2.setDescription("hazzan zcelk");
        artistUser2.setCin(12345687);
        artistUser2.setId_abonnement("ekzjfnkzjenfkz");
        artistUser2.setTelephone("45568978");
        artistUser2.setProfileImg("https://img.freepik.com/premium-psd/artist-meta-people-3d-avatar_698091-324.jpg?w=2000");
        Set<Role> ownerRoles2 = new HashSet<>();
        ownerRoles2.add(artistRole);
        artistUser2.setRoles(ownerRoles);
        userDao.save(artistUser2);

        UtilisateurSimple user = new UtilisateurSimple();
        user.setName("raj");
        user.setLastName("rfefre");
        user.setUserName("raj123@gmail.com");
        user.setUserPassword(getEncodedPassword("raj@pass"));
        user.setProfileImg("https://img.freepik.com/premium-psd/3d-cartoon-character-avatar-isolated-3d-rendering_235528-548.jpg?w=2000");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRoles(userRoles);
       /* user.getFollowing().add(artistUser);
        user.getFollowing().add(artistUser1);
        user.getFollowing().add(artistUser2);*/
        userDao.save(user);

        UtilisateurSimple user1 = new UtilisateurSimple();
        user1.setName("amina");
        user1.setLastName("njr");
        user1.setUserName("ami23@gmail.com");
        user1.setUserPassword(getEncodedPassword("ami24njrode44"));
        user1.setProfileImg("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQsvwStLsd0tDytT0feCG3yylOUvqXlVdWVtBQHatgyTN0-_wRhMTFnYU9rdzxtB1wE61o&usqp=CAU");
        Set<Role> userRoles1 = new HashSet<>();
        userRoles1.add(userRole);
        user1.setRoles(userRoles1);
        userDao.save(user1);

        UtilisateurSimple user2 = new UtilisateurSimple();
        user2.setName("noor");
        user2.setLastName("njr");
        user2.setUserName("noor55@gmail.com");
        user2.setUserPassword(getEncodedPassword("noor44"));
        user2.setProfileImg("https://img.freepik.com/premium-psd/3d-illustration-cartoon-avatar-muslim-woman-premium-psd_611602-56.jpg");
        Set<Role> userRoles2 = new HashSet<>();
        userRoles2.add(userRole);
        user2.setRoles(userRoles2);
        userDao.save(user2);

        UtilisateurSimple user3 = new UtilisateurSimple();
        user3.setName("iheb");
        user3.setLastName("ouinisi");
        user3.setUserName("hooba@gmail.com");
        user3.setUserPassword(getEncodedPassword("hooba@pass"));
        user3.setProfileImg("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHpazUG2Uwuaf_Z3S6PqThfL7Ucpx6R5ALNw&usqp=CAU");
        Set<Role> userRoles3 = new HashSet<>();
        userRoles3.add(userRole);
        user3.setRoles(userRoles3);
        userDao.save(user3);

        UtilisateurSimple user4 = new UtilisateurSimple();
        user4.setName("badr");
        user4.setLastName("ouinisi");
        user4.setUserName("bdr@gmail.com");
        user4.setUserPassword(getEncodedPassword("bdr@pass"));
        user4.setProfileImg("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQQkEteQn_WxP-S9Q1JsCBKgbk8jr8AxB814Q&usqp=CAU");
        Set<Role> userRoles4 = new HashSet<>();
        userRoles4.add(userRole);
        user4.setRoles(userRoles4);
        userDao.save(user4);

        UtilisateurSimple user5 = new UtilisateurSimple();
        user5.setName("jesser");
        user5.setLastName("harabi");
        user5.setUserName("harabi@gmail.com");
        user5.setUserPassword(getEncodedPassword("harabi@pass"));
        user5.setProfileImg("https://d2pas86kykpvmq.cloudfront.net/images/humans-3.0/ava-4.png");
        Set<Role> userRoles5 = new HashSet<>();
        userRoles5.add(userRole);
        user5.setRoles(userRoles5);
        userDao.save(user5);

        Artiste artistUser3 = new Artiste();
        artistUser3.setUserName("jilani@gmail.com");
        artistUser3.setName("jilani");
        artistUser3.setLastName("mo7amed");
        artistUser3.setUserPassword(getEncodedPassword("jilanisecurity@123"));
        artistUser3.setSpecialite("securite");
        artistUser3.setDescription("niernifjrenifj");
        artistUser3.setCin(87654321);
        artistUser3.setId_abonnement("ekjnekrjfkj");
        artistUser3.setTelephone("78945623");
        artistUser3.setProfileImg("https://img.freepik.com/premium-psd/character-avatar-3d-illustration_460336-702.jpg?w=2000");
        Set<Role> ownerRoles3 = new HashSet<>();
        ownerRoles3.add(artistRole);
        artistUser3.setRoles(ownerRoles);
        /*artistUser3.getFollowing().add(user1);
        artistUser3.getFollowing().add(user2);
        artistUser3.getFollowing().add(user3);
        artistUser3.getFollowing().add(artistUser1);
        artistUser3.getFollowing().add(artistUser2);
        artistUser3.getFollower().add(user4);
        artistUser3.getFollower().add(user3);
        artistUser3.getFollower().add(user5);*/
        userDao.save(artistUser3);

        ContenuePub c1 = new ContenuePub(0,"POST 1",null,null,null);
        contenuePubRepo.save(c1);
        Publication p1 = new Publication(0,c1,new Date(),"raj123@gmail.com");
        publicationRepo.save(p1);
        user.getPublications().add(p1);
        userDao.save(user);

        ContenuePub c2 = new ContenuePub(0,"POST 2",null,null,null);
        contenuePubRepo.save(c2);
        Publication p2 = new Publication(0,c2,new Date(),"raj123@gmail.com");
        publicationRepo.save(p2);
        user.getPublications().add(p2);
        userDao.save(user);

        ContenuePub c3 = new ContenuePub(0,"POST 3",null,null,null);
        contenuePubRepo.save(c3);
        Publication p3 = new Publication(0,c3,new Date(),"soha@gmail.com");
        publicationRepo.save(p3);
        artistUser2.getPublications().add(p3);
        userDao.save(artistUser2);

        ContenuePub c4 = new ContenuePub(0,"POST 4",null,null,null);
        contenuePubRepo.save(c4);
        Publication p4 = new Publication(0,c4,new Date(),"soha@gmail.com");
        publicationRepo.save(p4);
        artistUser2.getPublications().add(p4);
        userDao.save(artistUser2);

        ContenuePub c5 = new ContenuePub(0,"POST 5",null,null,null);
        contenuePubRepo.save(c5);
        Publication p5 = new Publication(0,c5,new Date(),"moham@gmail.com");
        publicationRepo.save(p5);
        artistUser1.getPublications().add(p5);
        userDao.save(artistUser1);

        ContenuePub c6 = new ContenuePub(0,"POST 6",null,null,null);
        contenuePubRepo.save(c6);
        Publication p6 = new Publication(0,c6,new Date(),"jilani@gmail.com");
        publicationRepo.save(p6);
        artistUser3.getPublications().add(p6);
        userDao.save(artistUser3);

        ContenuePub c7 = new ContenuePub(0,"POST 7",null,null,null);
        contenuePubRepo.save(c7);
        Publication p7 = new Publication(0,c7,new Date(),"ami23@gmail.com");
        publicationRepo.save(p7);
        user1.getPublications().add(p7);
        userDao.save(user1);

        ContenuePub c8 = new ContenuePub(0,"POST 8",null,null,null);
        contenuePubRepo.save(c8);
        Publication p8 = new Publication(0,c8,new Date(),"noor55@gmail.com");
        publicationRepo.save(p8);
        user2.getPublications().add(p8);
        userDao.save(user2);

        ContenuePub c9 = new ContenuePub(0,"POST 9",null,null,null);
        contenuePubRepo.save(c9);
        Publication p9 = new Publication(0,c9,new Date(),"hooba@gmail.com");
        publicationRepo.save(p9);
        user3.getPublications().add(p9);
        userDao.save(user3);

        ContenuePub c10 = new ContenuePub(0,"POST 10",null,null,null);
        contenuePubRepo.save(c10);
        Publication p10 = new Publication(0,c10,new Date(),"oussa13@gmail.com");
        publicationRepo.save(p10);
        artistUser.getPublications().add(p10);
        userDao.save(artistUser);


    }



    public User fetchUserByProfileId(String profileId) {
        return userDao.findByProfileId(profileId);
    }

    public User fetchCurrentUser(String profileId) {
        return userDao.findByProfileId(profileId);
    }

    public void followUser(String profileIdToFollow) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findById(authentication.getName()).get();
        User userToFollow = userDao.findByProfileId(profileIdToFollow);
        System.out.println("userToFollow = " + userToFollow);
        System.out.println("user = " + user);
        user.getFollowing().add(userToFollow);
        userToFollow.getFollower().add(user);
        userDao.save(user);
        userDao.save(userToFollow);
        Notification notification = new Notification();
        notification.setDescription(user.getName() + " " + user.getLastName() + " started following you");
        notification.setImg(user.getProfileImg());
        notification.setDate(new Date());
        notificationRepo.save(notification);
        userToFollow.getNotifications().add(notification);
        userDao.save(userToFollow);
        System.out.println(notification+"\n");
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}


