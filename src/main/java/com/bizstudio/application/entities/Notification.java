/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.application.entities;

import com.bizstudio.application.enums.NavigationRoute;
import com.bizstudio.application.enums.NotificationState;
import com.bizstudio.ui.pages.application.ApplicationPage;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author ObinnaAsuzu
 */
public interface Notification extends Serializable{
    public String getId();
    public void setId(String id);

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

    /**
     * @return the route
     */
    public NavigationRoute getRoute();

    /**
     * @param route the page to set
     */
    public void setRoute(NavigationRoute route);

    /**
     * @return the pageParameters
     */
    public Map<String, String> getPageParameters();

    /**
     * @param pageParameters the pageParameters to set
     */
    public void setPageParameters(Map<String, String> pageParameters);
}
