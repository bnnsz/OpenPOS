/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.application.managers;

/**
 *
 * @author ObinnaAsuzu
 */
public class NotificationManager {
    
    private NotificationManager() {
    }
    
    public static NotificationManager getInstance() {
        return NotificationManagerHolder.INSTANCE;
    }
    
    private static class NotificationManagerHolder {

        private static final NotificationManager INSTANCE = new NotificationManager();
    }
}
