package com.fsb.PFE.shared.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/notification")
public class NotificationController {
    @Autowired private NotificationService notificationService;

    @PostMapping(value = "/addNotifi/{username}")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public void addNotification(Notification notification,@PathVariable String username){
        notificationService.addNotification(notification,username);
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public void deleteNotification(int id){
        notificationService.deleteNotification(id);
    }

    @GetMapping(value = "/getAllNotification")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public List<Notification> getAllNotification(){
        return notificationService.getAllNotification();
    }


    @GetMapping(value = "/getUnreadNotification")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public List<Notification> unreadNotification(){
        return notificationService.unreadNotification();
    }

    @PostMapping(value = "/setLastNotifDate")
    @PreAuthorize("hasRole('Artiste') or hasRole('User')")
    public void setLastNotifDate(){
        notificationService.setLastNotifDate();
    }


}
