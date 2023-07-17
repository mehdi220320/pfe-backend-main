package com.fsb.PFE.shared.notification;

import java.util.List;

public interface NotificationService {
    void addNotification(Notification notification, String username);
    void deleteNotification(int id);
    List<Notification> getAllNotification();
    public List<Notification> unreadNotification();
    public void setLastNotifDate();
}
