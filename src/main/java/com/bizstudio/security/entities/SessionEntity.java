/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.entities;

import com.bizstudio.security.util.SessionAttributesConverter;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.apache.shiro.session.mgt.SimpleSession;

/**
 *
 * @author ObinnaAsuzu
 */
@Entity
@Table(name = "USER_SESSIONS")
public class SessionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String username;
    @Column
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date startTimestamp;
    @Column
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date stopTimestamp;
    @Column
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lastAccessTime;
    @Column
    private long timeout;
    @Column
    private boolean expired;
    @Column(name = "SESSION_HOST")
    private String host;
    @Column
    @Convert(converter = SessionAttributesConverter.class)
    private Map<Object, Object> attributes;

    public SessionEntity() {
    }

    private SessionEntity(SimpleSession session, boolean setId) {
        if (setId) {
            this.id = (Long) session.getId();
        }
        this.startTimestamp = session.getStartTimestamp();
        this.stopTimestamp = session.getStopTimestamp();
        this.lastAccessTime = session.getLastAccessTime();
        this.timeout = session.getTimeout();
        this.expired = session.isExpired();
        this.host = session.getHost();
        this.attributes = session.getAttributes();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the startTimestamp
     */
    public Date getStartTimestamp() {
        return startTimestamp;
    }

    /**
     * @param startTimestamp the startTimestamp to set
     */
    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    /**
     * @return the stopTimestamp
     */
    public Date getStopTimestamp() {
        return stopTimestamp;
    }

    /**
     * @param stopTimestamp the stopTimestamp to set
     */
    public void setStopTimestamp(Date stopTimestamp) {
        this.stopTimestamp = stopTimestamp;
    }

    /**
     * @return the lastAccessTime
     */
    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    /**
     * @param lastAccessTime the lastAccessTime to set
     */
    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    /**
     * @return the timeout
     */
    public long getTimeout() {
        return timeout;
    }

    /**
     * @param timeout the timeout to set
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * @return the expired
     */
    public boolean isExpired() {
        return expired;
    }

    /**
     * @param expired the expired to set
     */
    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SessionEntity)) {
            return false;
        }
        SessionEntity other = (SessionEntity) object;
        return !((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.bizstudio.BizPortal.security.entities.SessionEntity[ id=" + getId() + " ]";
    }

    public void updateSession(SimpleSession session) {
        this.startTimestamp = session.getStartTimestamp();
        this.stopTimestamp = session.getStopTimestamp();
        this.lastAccessTime = session.getLastAccessTime();
        this.timeout = session.getTimeout();
        this.expired = session.isExpired();
        this.host = session.getHost();
        this.attributes = session.getAttributes();
        if (session.getAttribute("user") != null) {
            this.username = session.getAttribute("user").toString();
        }
    }

    public static SessionEntity create(SimpleSession session) {
        return new SessionEntity(session, false);
    }

    public static SessionEntity update(SimpleSession session) {
        return new SessionEntity(session, true);
    }

    public SimpleSession toSession() {
        SimpleSession session = new SimpleSession();
        session.setId(this.id);
        session.setStartTimestamp(this.startTimestamp);
        session.setStopTimestamp(this.stopTimestamp);
        session.setLastAccessTime(this.lastAccessTime);
        session.setTimeout(this.timeout);
        session.setExpired(this.expired);
        session.setHost(this.host);
        session.setAttributes(this.attributes);
        return session;
    }
}
