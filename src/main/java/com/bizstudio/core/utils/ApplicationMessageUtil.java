/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.core.utils;

import com.bizstudio.ui.components.util.Alert;
import com.bizstudio.ui.pages.application.ApplicationContainer;

/**
 *
 * @author ObinnaAsuzu
 */
public class ApplicationMessageUtil {

    /**
     * Instantiates the message util, must be called before calling the getInstance().<br>
     * Make sure to add this immediately after the application container is created
     * @param applicationContainer
     * @return 
     */
    public static ApplicationMessageUtil instantiate(ApplicationContainer applicationContainer){
        AlertMessageUtilHolder.INSTANCE = new ApplicationMessageUtil(applicationContainer);
        return AlertMessageUtilHolder.INSTANCE;
    }

    /**
     * Returns message util instance
     * @return 
     */
    public static ApplicationMessageUtil getInstance() {
        return AlertMessageUtilHolder.INSTANCE;
    }
    ApplicationContainer applicationContainer;
    private ApplicationMessageUtil(ApplicationContainer applicationContainer) {
        this.applicationContainer =  applicationContainer;
    }

    public void addMessage(String message) {
        addMessage(MessageType.INFO, "", message);
    }

    public void addMessage(String title, String message) {
        addMessage(MessageType.INFO, title, message);
    }
    
    public void addMessage(MessageType type, String message) {
        addMessage(type, "", message);
    }

    public void addMessage(MessageType type, String title, String message) {
        Alert alert = Alert.create(title, message);
        alert.getStyleClass().add(type.getColorClass());
        applicationContainer.addAlert(alert);
    }

    private static class AlertMessageUtilHolder {
        private static ApplicationMessageUtil INSTANCE;
    }
}
