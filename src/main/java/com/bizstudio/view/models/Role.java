/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.models;

import com.bizstudio.security.entities.PrivilegeEntity;
import com.bizstudio.security.entities.RoleEntity;
import java.util.ArrayList;
import java.util.Objects;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author obinna.asuzu
 */
public class Role {
    LongProperty idProperty;
    StringProperty nameProperty;
    BooleanProperty systemProperty;
    ListProperty<PrivilegeEntity> permissionsProperty;

    public Role(RoleEntity role) {
        idProperty = new SimpleLongProperty(role.getId());
        nameProperty = new SimpleStringProperty(role.getName());
        systemProperty = new SimpleBooleanProperty(role.isSystem());
        permissionsProperty = new SimpleListProperty<>(FXCollections.observableArrayList(role.getPrivileges()));
    }

    public Role() {
        idProperty = new SimpleLongProperty(0);
        nameProperty = new SimpleStringProperty("");
        systemProperty = new SimpleBooleanProperty(false);
        permissionsProperty = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    }
    
    

    public final long getId() {
        return idProperty.get();
    }

    public final void setId(long value) {
        idProperty.set(value);
    }

    public LongProperty idProperty() {
        return idProperty;
    }

    public final String getName() {
        return nameProperty.get();
    }

    public final void setName(String value) {
        nameProperty.set(value);
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public final ObservableList<PrivilegeEntity> getPermissions() {
        return permissionsProperty.get();
    }

    public final void setPermissions(ObservableList<PrivilegeEntity> value) {
        permissionsProperty.set(value);
    }

    public ListProperty<PrivilegeEntity> permissionsProperty() {
        return permissionsProperty;
    }

    public final boolean isSystem() {
        return systemProperty.get();
    }

    public final void setSystem(boolean value) {
        systemProperty.set(value);
    }

    public BooleanProperty systemProperty() {
        return systemProperty;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idProperty);
        hash = 97 * hash + Objects.hashCode(this.nameProperty);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;
        if (!Objects.equals(this.idProperty, other.idProperty)) {
            return false;
        }
        if (!Objects.equals(this.nameProperty, other.nameProperty)) {
            return false;
        }
        return true;
    }
    
    
    
    

    public RoleEntity toEntity() {
        RoleEntity entity = new RoleEntity();
        entity.setId(getId());
        entity.setName(getName());
        entity.setSystem(isSystem());
        entity.setPrivileges(getPermissions().stream().collect(toSet()));
        return entity;
    }

    @Override
    public String toString() {
        return "Role{" + "idProperty=" + idProperty + ", nameProperty=" + nameProperty + ", systemProperty=" + systemProperty + ", permissionsProperty=" + permissionsProperty + '}';
    }
    
    
    
    
}
















