/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.entities;

import com.bizstudio.security.enums.TokenType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author obinna.asuzu
 */
@Entity
public class TokenEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "token_value")
    private String value;
    
    private String channel;

    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private TokenType type;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    @Column
    private boolean expired;

    public TokenEntity() {
    }

    public TokenEntity(String value, UserEntity user, TokenType type, String channel) {
        this.value = value;
        this.user = user;
        this.type = type;
        this.channel = channel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * @return the type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(TokenType type) {
        this.type = type;
    }

    /**
     * @return the user
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserEntity user) {
        this.user = user;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TokenEntity)) {
            return false;
        }
        TokenEntity other = (TokenEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.encooked.entities.TokenEntity[ id=" + id + " ]";
    }

}






