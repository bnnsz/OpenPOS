/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.entities;

import com.bizstudio.security.util.CredentialConverter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class UserAccountEntity implements Serializable {

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
    private List<UserRoleEntity> roles = new ArrayList<>();

    public UserAccountEntity() {
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
     * @param principals the principals to set
     */
    public void setPrincipals(List<PrincipalEntity> principals) {
        this.principals = principals;
    }

    /**
     * @return the roles
     */
    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return (username + " => " + credentials.toString());
    }

}
