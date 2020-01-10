/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.models.inventory;

import com.bizstudio.inventory.entities.ItemCategoryEntity;
import com.bizstudio.view.models.ViewModel;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author obinna.asuzu
 */
public class ItemCategory implements ViewModel<ItemCategoryEntity> {

    private LongProperty id;
    private StringProperty name;
    private ItemCategory parent;
    private StringProperty description;

    public ItemCategory() {
        this.description = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.id = new SimpleLongProperty();
    }

    public ItemCategory(ItemCategoryEntity entity) {
        this.description = new SimpleStringProperty(entity.getDescription());
        this.name = new SimpleStringProperty(entity.getName());
        this.id = new SimpleLongProperty(entity.getId());
        if (entity.getParent() != null) {
            this.parent = new ItemCategory(entity.getParent());
        }
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

    /**
     * @return the parent
     */
    public ItemCategory getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(ItemCategory parent) {
        this.parent = parent;
    }

    public final String getDescription() {
        return description.get();
    }

    public final void setDescription(String value) {
        description.set(value);
    }

    public StringProperty descriptionProperty() {
        return description;
    }
    
    

    @Override
    public ItemCategoryEntity toEntity() {
        if(name.get().isBlank()){
            return null;
        }
        ItemCategoryEntity entity = new ItemCategoryEntity();
        entity.setId(id.get());
        entity.setName(name.get());
        entity.setDescription(description.get());
        if (parent != null) {
            entity.setParent(parent.toEntity());
        }
        return entity;
    }
}




