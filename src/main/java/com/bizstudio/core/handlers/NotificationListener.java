/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.core.handlers;

import com.bizstudio.core.entities.data.Notification;

/**
 *
 * @author ObinnaAsuzu
 */
@FunctionalInterface
public interface NotificationListener {
    public void handle(Notification notification);
}
