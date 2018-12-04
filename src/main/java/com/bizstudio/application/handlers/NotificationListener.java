/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.application.handlers;

import com.bizstudio.application.entities.Notification;

/**
 *
 * @author ObinnaAsuzu
 */
@FunctionalInterface
public interface NotificationListener {
    public void handle(Notification notification);
}
