package com.fsb.PFE.shared.notification;

import com.fsb.PFE.authentication.dao.UserDao;
import com.fsb.PFE.authentication.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{
    private static Date lastNotifDate = new Date();
    @Autowired private NotificationRepo notificationRepo;
    @Autowired private UserDao userDao;

    @Override
    public void addNotification(Notification notification, String username) {
        notification.setDate(new Date());
        userDao.findById(username).get().getNotifications().add(notification);
        userDao.save(userDao.findById(username).get());
        notificationRepo.save(notification);
    }

    @Override
    public void deleteNotification(int id) {
        notificationRepo.deleteById(id);
    }

    @Override
    public List<Notification> getAllNotification() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findById(authentication.getName()).get();
        return user.getNotifications();
    }

    @Override
    public List<Notification> unreadNotification(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Notification> notifications = new ArrayList<>();
        for (Notification notification : userDao.findById(authentication.getName()).get().getNotifications()) {
            if(notification.getDate().after(lastNotifDate)){
                notifications.add(notification);
            }
        }
        return notifications;
    }

    @Override
    public void setLastNotifDate(){
        lastNotifDate = new Date();
    }


}
