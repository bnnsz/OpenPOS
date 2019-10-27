/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.models;

import com.bizstudio.security.entities.UserEntity;
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
import javafx.collections.ObservableList;

/**
 *
 * @author obinna.asuzu
 */
public class User{
    private final LongProperty idProperty;
    private final StringProperty usernameProperty;
    private final StringProperty firstnameProperty;
    private final StringProperty lastnameProperty;
    private final StringProperty othernamesProperty;
    private final StringProperty fullnameProperty;
    private final StringProperty emailProperty;
    private final StringProperty phoneProperty;
    private final BooleanProperty enabledProperty;
    private final BooleanProperty selectedProperty;
    private final ListProperty<Role> rolesProperty;

    public User(UserEntity user) {
        idProperty = new SimpleLongProperty(user.getId());
        usernameProperty = new SimpleStringProperty(user.getUsername());
        firstnameProperty = new SimpleStringProperty(user.getPrincipal("firstname"));
        lastnameProperty = new SimpleStringProperty(user.getPrincipal("lastname"));
        othernamesProperty = new SimpleStringProperty(user.getPrincipal("othernames"));
        fullnameProperty = new SimpleStringProperty((getFirstname() + " "+ getLastname()).trim());
        fullnameProperty.bind(Bindings.concat(firstnameProperty," ",lastnameProperty));
        emailProperty = new SimpleStringProperty(user.getPrincipal("email"));
        phoneProperty = new SimpleStringProperty(user.getPrincipal("phone"));
        enabledProperty = new SimpleBooleanProperty(user.isEnabled());
        selectedProperty = new SimpleBooleanProperty(false);
        rolesProperty = new SimpleListProperty<>(FXCollections
                .observableArrayList(user
                        .getRoles().stream()
                        .map(Role::new)
                        .collect(toList())));
    }

    /**
     * @return the usernameProperty
     */
    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    /**
     * @return the firstnameProperty
     */
    public StringProperty getFirstnameProperty() {
        return firstnameProperty;
    }

    /**
     * @return the lastnameProperty
     */
    public StringProperty getLastnameProperty() {
        return lastnameProperty;
    }

    /**
     * @return the othernamesProperty
     */
    public StringProperty getOthernamesProperty() {
        return othernamesProperty;
    }

    /**
     * @return the fullnameProperty
     */
    public StringProperty getFullnameProperty() {
        return fullnameProperty;
    }

    /**
     * @return the emailProperty
     */
    public StringProperty getEmailProperty() {
        return emailProperty;
    }

    /**
     * @return the phoneProperty
     */
    public StringProperty getPhoneProperty() {
        return phoneProperty;
    }

    /**
     * @return the enabledProperty
     */
    public BooleanProperty getEnabledProperty() {
        return enabledProperty;
    }

    /**
     * @return the selectedProperty
     */
    public BooleanProperty getSelectedProperty() {
        return selectedProperty;
    }

    /**
     * @return the rolesProperty
     */
    public ListProperty<Role> getRolesProperty() {
        return rolesProperty;
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

    public final String getUsername() {
        return getUsernameProperty().get();
    }

    public final void setUsername(String value) {
        getUsernameProperty().set(value);
    }

    public StringProperty usernameProperty() {
        return getUsernameProperty();
    }

    public final String getFirstname() {
        return getFirstnameProperty().get();
    }

    public final void setFirstname(String value) {
        getFirstnameProperty().set(value);
    }

    public StringProperty firstnameProperty() {
        return getFirstnameProperty();
    }

    public final String getLastname() {
        return getLastnameProperty().get();
    }

    public final void setLastname(String value) {
        getLastnameProperty().set(value);
    }

    public StringProperty lastnameProperty() {
        return getLastnameProperty();
    }

    public final String getOthernames() {
        return getOthernamesProperty().get();
    }

    public final void setOthernames(String value) {
        getOthernamesProperty().set(value);
    }

    public StringProperty othernamesProperty() {
        return getOthernamesProperty();
    }

    public final String getFullname() {
        return getFullnameProperty().get();
    }

    public final void setFullname(String value) {
        getFullnameProperty().set(value);
    }

    public StringProperty fullnameProperty() {
        return getFullnameProperty();
    }

    public final String getEmail() {
        return getEmailProperty().get();
    }

    public final void setEmail(String value) {
        getEmailProperty().set(value);
    }

    public StringProperty emailProperty() {
        return getEmailProperty();
    }

    public final String getPhone() {
        return getPhoneProperty().get();
    }

    public final void setPhone(String value) {
        getPhoneProperty().set(value);
    }

    public StringProperty phoneProperty() {
        return getPhoneProperty();
    }

    public final boolean isEnabled() {
        return getEnabledProperty().get();
    }

    public final void setEnabled(boolean value) {
        getEnabledProperty().set(value);
    }

    public BooleanProperty enabledProperty() {
        return getEnabledProperty();
    }

    public final boolean isSelected() {
        return getSelectedProperty().get();
    }

    public final void setSelected(boolean value) {
        getSelectedProperty().set(value);
    }

    public BooleanProperty selectedProperty() {
        return getSelectedProperty();
    }

    public final ObservableList<Role> getRoles() {
        return getRolesProperty().get();
    }

    public final void setRoles(ObservableList<Role> value) {
        getRolesProperty().set(value);
    }

    public ListProperty<Role> rolesProperty() {
        return getRolesProperty();
    }

    

    
    
}




