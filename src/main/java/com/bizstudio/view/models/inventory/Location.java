/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.models.inventory;

import com.bizstudio.inventory.entities.LocationEntity;
import com.bizstudio.view.models.ViewModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author obinna.asuzu
 */
public class Location implements ViewModel<LocationEntity> {

    private final LongProperty id;
    private final StringProperty name;
    private final StringProperty phoneNumber;
    private Address address;
    private final ObservableList<Location> subLocations;
    private Location parentLocation;
    private final ObservableList<ItemStock> stocks;

    public Location() {
        id = new SimpleLongProperty(0);
        name = new SimpleStringProperty("");
        phoneNumber = new SimpleStringProperty("");
        subLocations = FXCollections.observableList(new ArrayList<>());
        stocks = FXCollections.observableList(new ArrayList<>());
    }

    public Location(LocationEntity entity) {
        if (entity == null) {
            id = new SimpleLongProperty(0);
            name = new SimpleStringProperty("");
            phoneNumber = new SimpleStringProperty("");
            subLocations = FXCollections.observableList(new ArrayList<>());
            stocks = FXCollections.observableList(new ArrayList<>());
            return;
        }
        id = new SimpleLongProperty(entity.getId());
        name = new SimpleStringProperty(entity.getName());
        phoneNumber = new SimpleStringProperty(entity.getPhoneNumber());
        if (entity.getAddress() != null) {
            address = new Address(entity.getAddress());
        }
        subLocations = FXCollections.observableList(new ArrayList<>());
        if (entity.getParentLocation() != null) {
            parentLocation = new Location(entity.getParentLocation());
        }
        stocks = FXCollections.observableList(new ArrayList<>());
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

    public final String getName() {
        return name.get();
    }

    public final void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public final String getPhoneNumber() {
        return phoneNumber.get();
    }

    public final void setPhoneNumber(String value) {
        phoneNumber.set(value);
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    /**
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return the subLocations
     */
    public ObservableList<Location> getSubLocations() {
        return subLocations;
    }

    /**
     * @return the parentLocation
     */
    public Location getParentLocation() {
        return parentLocation;
    }

    /**
     * @param parentLocation the parentLocation to set
     */
    public void setParentLocation(Location parentLocation) {
        this.parentLocation = parentLocation;
    }

    /**
     * @return the stocks
     */
    public ObservableList<ItemStock> getStocks() {
        return stocks;
    }

    public void addStocksListener(ListChangeListener<? super ItemStock> ll) {
        stocks.addListener(ll);
    }

    public void removeStocksListener(ListChangeListener<? super ItemStock> ll) {
        stocks.removeListener(ll);
    }

    public boolean addStock(ItemStock e) {
        return stocks.add(e);
    }

    public boolean addAllStocks(Collection<? extends ItemStock> c) {
        return stocks.addAll(c);
    }

    public boolean addAllStocks(int index, Collection<? extends ItemStock> c) {
        return stocks.addAll(index, c);
    }

    public boolean removeAllStocks(Collection<?> c) {
        return stocks.removeAll(c);
    }

    public void addStock(int index, ItemStock element) {
        stocks.add(index, element);
    }

    public ItemStock removeStock(int index) {
        return stocks.remove(index);
    }

    public boolean removeStockIf(Predicate<? super ItemStock> filter) {
        return stocks.removeIf(filter);
    }
    
    public void addSubLocationsListener(ListChangeListener<? super Location> ll) {
        subLocations.addListener(ll);
    }

    public void removeSubLocationsListener(ListChangeListener<? super Location> ll) {
        subLocations.removeListener(ll);
    }

    public boolean addSubLocation(Location e) {
        return subLocations.add(e);
    }

    public boolean addAllSubLocations(Collection<? extends Location> c) {
        return subLocations.addAll(c);
    }

    public boolean addAllSubLocations(int index, Collection<? extends Location> c) {
        return subLocations.addAll(index, c);
    }

    public boolean removeAllSubLocations(Collection<? extends Location> c) {
        return subLocations.removeAll(c);
    }

    public void addSubLocation(int index, Location element) {
        subLocations.add(index, element);
    }

    public Location removeSubLocation(int index) {
        return subLocations.remove(index);
    }

    public boolean removeSubLocationIf(Predicate<? super Location> filter) {
        return subLocations.removeIf(filter);
    }
    
    
    
    

    @Override
    public LocationEntity toEntity() {
        if (name.get().isBlank() || address == null) {
            return null;
        }
        LocationEntity entity = new LocationEntity();
        entity.setId(id.get());
        entity.setName(name.get());
        entity.setPhoneNumber(phoneNumber.get());
        entity.setAddress(address.toEntity());
        if (parentLocation != null) {
            entity.setParentLocation(entity);
        }
        return entity;

    }

}



