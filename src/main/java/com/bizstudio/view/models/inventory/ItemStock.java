/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.models.inventory;

import com.bizstudio.inventory.entities.ItemStockEntity;
import com.bizstudio.view.models.ViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

/**
 *
 * @author obinna.asuzu
 */
public class ItemStock implements ViewModel<ItemStockEntity> {

    private final LongProperty id;
    private Location location;
    private ItemVariant variant;
    private final IntegerProperty quantity;

    public ItemStock() {
        id = new SimpleLongProperty(0);
        location = null;
        variant = null;
        quantity = new SimpleIntegerProperty(0);
    }

    public ItemStock(ItemStockEntity entity) {
        if (entity == null) {
            id = new SimpleLongProperty(0);
            location = null;
            variant = null;
            quantity = new SimpleIntegerProperty(0);
            return;
        }
        id = new SimpleLongProperty(entity.getId());
       
        variant = new ItemVariant(entity.getVariant());
        if (entity.getLocation() != null) {
             location = new Location(entity.getLocation());
        }
        if (entity.getVariant() != null) {
             variant = new ItemVariant(entity.getVariant());
        }
        quantity = new SimpleIntegerProperty(entity.getQuantity());
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

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the variant
     */
    public ItemVariant getVariant() {
        return variant;
    }

    /**
     * @param variant the variant to set
     */
    public void setVariant(ItemVariant variant) {
        this.variant = variant;
    }

    public final int getQuantity() {
        return quantity.get();
    }

    public final void setQuantity(int value) {
        quantity.set(value);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    @Override
    public ItemStockEntity toEntity() {
        if (variant == null || location == null) {
            return null;
        }

        ItemStockEntity entity = new ItemStockEntity();
        entity.setId(id.get());
        entity.setLocation(location.toEntity());
        entity.setVariant(variant.toEntity());
        entity.setQuantity(quantity.get());
        return entity;
    }

}




