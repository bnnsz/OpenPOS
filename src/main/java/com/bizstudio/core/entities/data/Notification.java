/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.core.entities.data;

import com.bizstudio.core.enums.NotificationState;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ObinnaAsuzu
 */
public interface Notification extends Serializable{
    public String getId();
    public void setId(String id);
    public void setAction(NotificationAction action);

    /**
     * 
     * @return 
     */
    public NotificationAction getAction();
    /**
     * @return the created
     */
    public Date getCreated();

    /**
     * @param created the created to set
     */
    public void setCreated(Date created);

    /**
     * @return the title
     */
    public String getTitle();

    /**
     * @param title the title to set
     */
    public void setTitle(String title);

    /**
     * @return the message
     */
    public String getMessage();

    /**
     * @param message the message to set
     */
    public void setMessage(String message);

    /**
     * @return the state
     */
    public NotificationState getState();

    /**
     * @param state the state to set
     */
    public void setState(NotificationState state);
}
