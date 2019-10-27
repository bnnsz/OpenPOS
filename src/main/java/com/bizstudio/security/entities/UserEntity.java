/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.entities;

import com.bizstudio.security.util.CredentialConverter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author ObinnaAsuzu
 */
@Entity
public class UserEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    @Convert(converter = CredentialConverter.class)
    private String pin;

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private List<CredentialEntity> credentials = new ArrayList<>();

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private List<PrincipalEntity> principals = new ArrayList<>();

    @OneToMany
    private List<RoleEntity> roles = new ArrayList<>();
    @Column
    private Boolean enabled;
    @Column
    private Boolean system;

    private transient Map<String, String> principalsMap;

    private transient boolean selected;

    public UserEntity() {
        
    }

    
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
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
     * @return the pin
     */
    public String getPin() {
        return pin;
    }

    /**
     * @param pin the pin to set
     */
    public void setPin(String pin) {
        this.pin = pin;
    }

    /**
     * @return the credentials
     */
    public List<CredentialEntity> getCredentials() {
        return credentials;
    }

    /**
     * @param credentials the credentials to set
     */
    public void setCredentials(List<CredentialEntity> credentials) {
        this.credentials = credentials;
    }

    /**
     * @return the principals
     */
    public List<PrincipalEntity> getPrincipals() {
        return principals;
    }

    /**
     * @return the principals
     */
    public Map<String, String> getPrincipalsAsMap() {
        if (principalsMap == null) {
            principalsMap = new HashMap<>();
            for (PrincipalEntity p : principals) {
                principalsMap.put(p.getKey(), p.getValue());
            }
        }
        return principalsMap;
    }

    public String getPrincipal(String name) {
        String p = getPrincipalsAsMap().get(name);
        return p == null ? "" : p;
    }

    /**
     * @param principals the principals to set
     */
    public void setPrincipals(List<PrincipalEntity> principals) {
        this.principals = principals;
    }

    /**
     * @param key
     * @param value
     */
    public void setPrincipal(String key, String value) {
        boolean[] found = {false};
        for (PrincipalEntity p : this.principals) {
            if (p.getKey() != null && p.getKey().equals(key)) {
                p.setValue(value);
                principals.add(new PrincipalEntity(this, null, key, value));
                break;
            }
        }
    }

    /**
     * @return the roles
     */
    public List<RoleEntity> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled == null ? false : enabled;
    }

    /**
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the system
     */
    public boolean isSystem() {
        return system;
    }

    /**
     * @return the system
     */
    public Boolean getSystem() {
        return system;
    }

    /**
     * @param system the system to set
     */
    public void setSystem(Boolean system) {
        this.system = system;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return (username + " => " + credentials.toString());
    }
}





