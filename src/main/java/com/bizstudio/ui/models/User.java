/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.ui.models;

import com.bizstudio.security.entities.UserAccountEntity;
import java.util.Objects;
import static java.util.stream.Collectors.toList;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 *
 * @author obinna.asuzu
 */
public class User {
    private final LongProperty id;
    private final StringProperty username;
    private final StringProperty firstname;
    private final StringProperty lastname;
    private final StringProperty othernames;
    private final StringProperty fullname;
    private final StringProperty email;
    private final StringProperty phone;
    private final BooleanProperty enabled;
    private final BooleanProperty selected;
    private final ListProperty<Role> roles;

    public User(UserAccountEntity user) {
        id = new SimpleLongProperty(user.getId());
        username = new SimpleStringProperty(user.getUsername());
        firstname = new SimpleStringProperty(user.getPrincipal("firstname"));
        lastname = new SimpleStringProperty(user.getPrincipal("lastname"));
        othernames = new SimpleStringProperty(user.getPrincipal("othernames"));
        fullname = new SimpleStringProperty((firstname.get() + " "+ lastname.get()).trim());
        fullname.bind(Bindings.concat(firstname," ",lastname));
        email = new SimpleStringProperty(user.getPrincipal("email"));
        phone = new SimpleStringProperty(user.getPrincipal("phone"));
        enabled = new SimpleBooleanProperty(user.isEnabled());
        selected = new SimpleBooleanProperty(false);
        roles = new SimpleListProperty<>(FXCollections
                .observableArrayList(user
                        .getRoles().stream()
                        .map(Role::new)
                        .collect(toList())));
    }

    /**
     * @return the id
     */
    public LongProperty getId() {
        return id;
    }

    /**
     * @return the username
     */
    public StringProperty getUsername() {
        return username;
    }

    /**
     * @return the firstname
     */
    public StringProperty getFirstname() {
        return firstname;
    }

    /**
     * @return the lastname
     */
    public StringProperty getLastname() {
        return lastname;
    }

    /**
     * @return the othernames
     */
    public StringProperty getOthernames() {
        return othernames;
    }

    /**
     * @return the fullname
     */
    public StringProperty getFullname() {
        return fullname;
    }

    /**
     * @return the email
     */
    public StringProperty getEmail() {
        return email;
    }

    /**
     * @return the phone
     */
    public StringProperty getPhone() {
        return phone;
    }

    /**
     * @return the enabled
     */
    public BooleanProperty getEnabled() {
        return enabled;
    }

    /**
     * @return the selected
     */
    public BooleanProperty getSelected() {
        return selected;
    }

    /**
     * @return the roles
     */
    public ListProperty<Role> getRoles() {
        return roles;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final User other = (User) obj;
        return Objects.equals(this.id, other.id);
    }

    
    
}
