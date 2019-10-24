/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.ui.models;

import com.bizstudio.security.entities.data.UserRoleEntity;
import com.bizstudio.security.enums.UserPermissions;
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
    ListProperty<UserPermissions> permissions;

    public Role(UserRoleEntity role) {
        id = new SimpleLongProperty(role.getId());
        name = new SimpleStringProperty(role.getName());
        permissions = new SimpleListProperty<>(FXCollections.observableArrayList(role.getPermissions()));
    }
    
    
}
