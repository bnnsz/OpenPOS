/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.application.entities;

import com.bizstudio.application.enums.NavigationRoute;
import com.bizstudio.application.enums.NotificationState;
import com.bizstudio.security.util.CredentialConverter;
import com.bizstudio.ui.pages.application.ApplicationPage;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ObinnaAsuzu
 */
@Entity
public class NotificationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date created;
    @Column
    private String title;
    @Column
    private String message;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "notification_state")
    private NotificationState state;
    @Column
    @Enumerated(EnumType.ORDINAL)
    private NavigationRoute route;
    @ElementCollection
    private Map<String, String> pageParameters;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificationEntity)) {
            return false;
        }
        NotificationEntity other = (NotificationEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bizstudio.application.entities.NotificationEntity[ id=" + id + " ]";
    }

    /**
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the state
     */
    public NotificationState getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(NotificationState state) {
        this.state = state;
    }

    /**
     * @return the page
     */
    public NavigationRoute getroute() {
        return route;
    }

    /**
     * @param route the route to set
     */
    public void setRoute(NavigationRoute route) {
        this.route = route;
    }

    /**
     * @return the pageParameters
     */
    public Map<String, String> getPageParameters() {
        return pageParameters;
    }

    /**
     * @param pageParameters the pageParameters to set
     */
    public void setPageParameters(Map<String, String> pageParameters) {
        this.pageParameters = pageParameters;
    }
    
}
