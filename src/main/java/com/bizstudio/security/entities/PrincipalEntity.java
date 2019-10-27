/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.entities;

import java.io.Serializable;
import javax.persistence.Column;
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
public class PrincipalEntity implements Serializable, Principal {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String key;
    @Column
    private String value;
    @ManyToOne
    private UserEntity userAccount;

    public PrincipalEntity(String name, String value) {
        this.key = name;
        this.value = value;
    }
    
    public PrincipalEntity(UserEntity userAccount, Long id, String name, String value) {
        this.userAccount = userAccount;
        this.id = id;
        this.key = name;
        this.value = value;
    }
    
    public PrincipalEntity(String name, String value,UserEntity userAccount) {
        this.userAccount = userAccount;
        this.key = name;
        this.value = value;
    }

    public PrincipalEntity() {
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
        if (!(object instanceof PrincipalEntity)) {
            return false;
        }
        PrincipalEntity other = (PrincipalEntity) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.bizstudio.BizPortal.security.entities.PrincipalEntity[ id=" + id + " ]";
    }

    /**
     * @return the key
     */
    @Override
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    @Override
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
