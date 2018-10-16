/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.application.managers;

import com.bizstudio.application.entities.Notification;
import com.bizstudio.application.enums.NavigationRoute;
import com.bizstudio.application.handlers.NotificationListener;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 *
 * @author ObinnaAsuzu
 */
public class NotificationManager {

    List<NotificationListener> onCreateListeners = new ArrayList<>();
    List<NotificationListener> onHandleListeners = new ArrayList<>();

    List<Notification> notifications = new ArrayList<>();

    NavigationManger navigationManger = NavigationManger.getInstance();

    public void addOnCreateListener(NotificationListener listener) {
        if (listener != null) {
            onCreateListeners.add(listener);
        }
    }

    public void addOnHandleListener(NotificationListener listener) {
        if (listener != null) {
            onHandleListeners.add(listener);
        }
    }

    public void removeOnCreateListener(NotificationListener listener) {
        if (listener != null) {
            onCreateListeners.remove(listener);
        }
    }

    public void removeOnHandleListener(NotificationListener listener) {
        if (listener != null) {
            onHandleListeners.remove(listener);
        }
    }

    public void create(Notification notification) {
        onCreateListeners.forEach(action -> action.handle());
    }

    public void handle(Notification notification) {
        if (notification != null) {
            notifications.remove(notification);
            onHandleListeners.forEach(action -> action.handle());
            Map<String, Object> parameters = notification.getPageParameters().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                    e -> e.getValue()));
            navigationManger.navigate(NavigationRoute.HOME, parameters);
        }
    }

    private NotificationManager() {
    }

    public static NotificationManager getInstance() {
        return NotificationManagerHolder.INSTANCE;
    }

    private static class NotificationManagerHolder {

        private static final NotificationManager INSTANCE = new NotificationManager();
    }
}
