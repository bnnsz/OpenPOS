/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.entities;

import com.bizstudio.security.util.CredentialConverter;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author ObinnaAsuzu
 */
@Entity
public class CredentialEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "CREDENTIAL_NAME")
    private String name;
    @Column(name = "CREDENTIAL_VALUE")
    @Convert(converter = CredentialConverter.class)
    private String value;
    @Column(name = "CREDENTIAL_EXPIRED")
    private Boolean expired;
    @ManyToOne
    private UserEntity userAccount;

    public CredentialEntity() {
    }

    public CredentialEntity(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    public CredentialEntity(String name, String value,UserEntity userAccount) {
        this(name, value);
        this.userAccount = userAccount;
    }
    
    
    
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
        if (!(object instanceof CredentialEntity)) {
            return false;
        }
        CredentialEntity other = (CredentialEntity) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return name+ ": "+value;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * @return the expired
     */
    public Boolean getExpired() {
        return expired;
    }

    /**
     * @param expired the expired to set
     */
    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    /**
     * @return the userAccount
     */
    public UserEntity getUserAccount() {
        return userAccount;
    }

    /**
     * @param userAccount the userAccount to set
     */
    public void setUserAccount(UserEntity userAccount) {
        this.userAccount = userAccount;
    }
    
}
