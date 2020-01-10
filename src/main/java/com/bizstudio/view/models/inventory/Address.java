/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.models.inventory;

import com.bizstudio.inventory.entities.AddressEntity;
import com.bizstudio.view.models.ViewModel;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author obinna.asuzu
 */
public class Address implements ViewModel<AddressEntity>{
    private final LongProperty id;
    private final StringProperty line1;
    private final StringProperty line2;
    private final StringProperty countryCode;
    private final StringProperty stateCode;
    private final StringProperty city;

    public Address() {
        id = new SimpleLongProperty();
        line1 = new SimpleStringProperty();
        line2 = new SimpleStringProperty();
        countryCode = new SimpleStringProperty();
        stateCode = new SimpleStringProperty();
        city = new SimpleStringProperty();
    }
    
    

    public Address(AddressEntity entity) {
        id = new SimpleLongProperty(entity.getId());
        line1 = new SimpleStringProperty(entity.getLine1());
        line2 = new SimpleStringProperty(entity.getLine2());
        countryCode = new SimpleStringProperty(entity.getCountryCode());
        stateCode = new SimpleStringProperty(entity.getStateCode());
        city = new SimpleStringProperty(entity.getCity());
    }

    public final long getId() {
        return id.get();
    }

    public final void setId(long value) {
        id.set(value);
    }

    public LongProperty idProperty() {
        return id;
    }

    public final String getLine1() {
        return line1.get();
    }

    public final void setLine1(String value) {
        line1.set(value);
    }

    public StringProperty line1Property() {
        return line1;
    }

    public final String getLine2() {
        return line2.get();
    }

    public final void setLine2(String value) {
        line2.set(value);
    }

    public StringProperty line2Property() {
        return line2;
    }

    public final String getCountryCode() {
        return countryCode.get();
    }

    public final void setCountryCode(String value) {
        countryCode.set(value);
    }

    public StringProperty countryCodeProperty() {
        return countryCode;
    }

    public final String getStateCode() {
        return stateCode.get();
    }

    public final void setStateCode(String value) {
        stateCode.set(value);
    }

    public StringProperty stateCodeProperty() {
        return stateCode;
    }

    public final String getCity() {
        return city.get();
    }

    public final void setCity(String value) {
        city.set(value);
    }

    public StringProperty cityProperty() {
        return city;
    }
    
    

    @Override
    public AddressEntity toEntity() {
        if(line1.get().isBlank()){
            return null;
        }
        
        AddressEntity entity = new AddressEntity();
        entity.setId(id.get());
        entity.setLine1(line1.get());
        entity.setLine2(line2.get());
        entity.setCountryCode(countryCode.get());
        entity.setStateCode(stateCode.get());
        entity.setCity(city.get());
        return entity;
    }
    
}









