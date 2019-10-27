/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.models;

import com.bizstudio.security.entities.PrivilegeEntity;
import com.bizstudio.security.entities.RoleEntity;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 *
 * @author obinna.asuzu
 */
public class Role {
    LongProperty id;
    StringProperty name;
    ListProperty<PrivilegeEntity> permissions;

    public Role(RoleEntity role) {
        id = new SimpleLongProperty(role.getId());
        name = new SimpleStringProperty(role.getName());
        permissions = new SimpleListProperty<>(FXCollections.observableArrayList(role.getPrivileges()));
    }
    
    
}


