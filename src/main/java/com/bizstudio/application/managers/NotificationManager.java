/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.application.managers;

import com.bizstudio.application.entities.Notification;
import com.bizstudio.application.handlers.NotificationListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ObinnaAsuzu
 */
public class NotificationManager {
    public static NotificationManager getInstance() {
        return NotificationManagerHolder.INSTANCE;
    }

    private List<NotificationListener> onCreateListeners = new ArrayList<>();

    private List<Notification> notifications = new ArrayList<>();
    private NotificationManager() {
    }

    public void addOnCreateListener(NotificationListener listener) {
        if (listener != null) {
            onCreateListeners.add(listener);
        }
    }

    public void removeOnCreateListener(NotificationListener listener) {
        if (listener != null) {
            onCreateListeners.remove(listener);
        }
    }

    public void create(Notification notification) {
        notifications.add(notification);
        onCreateListeners.forEach(action -> action.handle(notification));
    }


    private static class NotificationManagerHolder {

        private static final NotificationManager INSTANCE = new NotificationManager();
    }
}
