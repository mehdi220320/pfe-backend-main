package com.fsb.PFE.shared.contenue_publication.likes;

import com.fsb.PFE.authentication.dao.UserDao;
import com.fsb.PFE.authentication.entity.User;
import com.fsb.PFE.authentication.entity.UserDTO;
import com.fsb.PFE.authentication.entity.UserDTOLikes;
import com.fsb.PFE.shared.contenue_publication.ContenuePub;
import com.fsb.PFE.shared.contenue_publication.ContenuePubRepo;
import com.fsb.PFE.shared.notification.Notification;
import com.fsb.PFE.shared.notification.NotificationRepo;
import com.fsb.PFE.shared.publication.PublicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LikeServiceImpl implements LikeService{
    @Autowired private ContenuePubRepo contenuePubRepo;
    @Autowired private LikeRepo likeRepo;
    @Autowired private UserDao userDao;
    @Autowired private NotificationRepo notificationRepo;
    @Autowired private PublicationRepo publicationRepo;


    @Override
    public void addLike(int idContenuePub) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Like like = new Like();
        like.setIdOwner(auth.getName());
        ContenuePub contenuePub = contenuePubRepo.findById(idContenuePub).get();
        ArrayList<String> list = contenuePub.getLikes().stream().map(Like::getIdOwner).
                collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println(list);
        if(!list.contains(auth.getName())){
            contenuePub.getLikes().add(like);
            likeRepo.save(like);
            contenuePubRepo.save(contenuePub);

            User user = userDao.findById(like.getIdOwner()).get();
            Notification notification = new Notification();
            notification.setDescription(user.getName() + " " + user.getLastName() + " liked your post");
            notification.setImg(user.getProfileImg());
            notification.setDate(new Date());
            notificationRepo.save(notification);
            String owner = publicationRepo.findByContenuId(contenuePub.getId()).getOwner();
            userDao.findById(owner).get().getNotifications().add(notification);
            userDao.save(user);
        }

    }

    @Override
    public void deleteLike(int idContenuePub) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Like> likes = likeRepo.findAllByIdOwner(auth.getName());
        ContenuePub contenuePub = contenuePubRepo.findById(idContenuePub).get();
        for (Like like : likes) {
            if (contenuePub.getLikes().contains(like)) {
                contenuePub.getLikes().remove(like);
                contenuePubRepo.save(contenuePub);
                likeRepo.delete(like);
                break;
            }
        }

    }

    @Override
    public List<Like> getAllLikes(int idContenuPub) {
        ContenuePub contenuePub = contenuePubRepo.findById(idContenuPub).get();
        return contenuePub.getLikes();
    }

    @Override
    public List<UserDTOLikes> getAllLikesNames(int idContenuPub) {
        List<String> list = getAllLikes(idContenuPub).stream().map(Like::getIdOwner).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println(list);
        List<UserDTOLikes> list1 = new ArrayList<>();
        for (String s : list) {
            User user = userDao.findById(s).get();
            list1.add(mapToUserLikes(user));
        }
        return list1;
    }


        UserDTOLikes mapToUserLikes(User user){
        UserDTOLikes userDTOLikes = new UserDTOLikes();
        userDTOLikes.setName(user.getName());
        userDTOLikes.setLastName(user.getLastName());
        userDTOLikes.setProfileId(user.getProfileId());
        userDTOLikes.setProfileImg(user.getProfileImg());
        return userDTOLikes;
    }

}
