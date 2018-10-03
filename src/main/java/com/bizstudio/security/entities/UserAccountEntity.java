/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;

/**
 *
 * @author ObinnaAsuzu
 */
@Entity
@NamedEntityGraphs({
    @NamedEntityGraph(name = "credentials", attributeNodes = @NamedAttributeNode("credentials")),
    @NamedEntityGraph(name = "principals", attributeNodes = @NamedAttributeNode("principals")),
    @NamedEntityGraph(name = "roles", attributeNodes = @NamedAttributeNode("roles")),

    @NamedEntityGraph(attributeNodes = {
        @NamedAttributeNode("credentials"),
        @NamedAttributeNode("principals"),
        @NamedAttributeNode("roles")
    }),})

public class UserAccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    private String username;
    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY)
    private List<CredentialEntity> credentials;
    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY)
    private List<PrincipalEntity> principals;
    @OneToMany(fetch = FetchType.LAZY)
    private List<UserRoleEntity> roles;

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

}
