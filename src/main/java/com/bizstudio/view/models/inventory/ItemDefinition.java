/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.models.inventory;

import com.bizstudio.inventory.entities.ItemDefinitionEntity;
import com.bizstudio.view.models.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author obinna.asuzu
 */
public class ItemDefinition implements ViewModel<ItemDefinitionEntity> {

    private LongProperty id;
    private StringProperty code;
    private StringProperty name;
    private ItemCategory category;
    private BooleanProperty stockable;
    private StringProperty description;

    public ItemDefinition() {
        this.description = new SimpleStringProperty();
        this.id = new SimpleLongProperty();
        this.code = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.stockable = new SimpleBooleanProperty();
    }

    public ItemDefinition(ItemDefinitionEntity entity) {
        if (entity == null) {
            this.description = new SimpleStringProperty();
            this.id = new SimpleLongProperty();
            this.code = new SimpleStringProperty();
            this.name = new SimpleStringProperty();
            this.stockable = new SimpleBooleanProperty();
            return;
        }
        this.description = new SimpleStringProperty(entity.getDescription());
        this.id = new SimpleLongProperty(entity.getId());
        this.code = new SimpleStringProperty(entity.getCode());
        this.name = new SimpleStringProperty(entity.getName());
        this.stockable = new SimpleBooleanProperty(entity.isStockable());
        this.category = new ItemCategory(entity.getCategory());
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

    public final String getCode() {
        return code.get();
    }

    public final void setCode(String value) {
        code.set(value);
    }

    public StringProperty codeProperty() {
        return code;
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

    public final boolean isStockable() {
        return stockable.get();
    }

    public final void setStockable(boolean value) {
        stockable.set(value);
    }

    public BooleanProperty stockableProperty() {
        return stockable;
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
    public ItemDefinitionEntity toEntity() {
        if(code.get().isBlank()  || name.get().isBlank()){
            return null;
        }
        ItemDefinitionEntity entity = new ItemDefinitionEntity();
        entity.setId(id.get());
        entity.setCode(code.get());
        entity.setName(name.get());
        entity.setCategory(category.toEntity());
        entity.setStockable(stockable.get());
        entity.setDescription(description.get());
        return entity;
    }

}



