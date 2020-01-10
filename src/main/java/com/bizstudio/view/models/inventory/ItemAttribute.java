/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.models.inventory;

import com.bizstudio.inventory.entities.ItemAttrModifierType;
import com.bizstudio.inventory.entities.ItemAttributeEntity;
import com.bizstudio.inventory.entities.ItemVariantEntity;
import com.bizstudio.view.models.ViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author obinna.asuzu
 */
public class ItemAttribute implements ViewModel<ItemAttributeEntity> {

    private final LongProperty id;
    private ItemProp property;
    private final StringProperty value;
    private final ItemAttrModifierType modifierType;
    private final DoubleProperty modifierAmount;
    private ItemVariant variant;

    public ItemAttribute() {
        this.id = new SimpleLongProperty(0);
        this.value = new SimpleStringProperty("");
        this.modifierType = ItemAttrModifierType.NONE;
        this.modifierAmount = new SimpleDoubleProperty(0.00);
    }

    public ItemAttribute(ItemAttributeEntity entity) {
        if (entity == null) {
            this.id = new SimpleLongProperty(0);
            this.value = new SimpleStringProperty("");
            this.modifierType = ItemAttrModifierType.NONE;
            this.modifierAmount = new SimpleDoubleProperty(0.00);
            return;
        }
        this.id = new SimpleLongProperty(entity.getId());
        this.property = new ItemProp(entity.getProperty());
        this.value = new SimpleStringProperty(entity.getValue());
        this.modifierType = entity.getModifierType();
        this.modifierAmount = new SimpleDoubleProperty(entity.getModifierAmount());
        this.variant = new ItemVariant(entity.getVariant());
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
     * @return the property
     */
    public ItemProp getProperty() {
        return property;
    }

    /**
     * @return the modifierType
     */
    public ItemAttrModifierType getModifierType() {
        return modifierType;
    }

    /**
     * @return the variant
     */
    public ItemVariant getVariant() {
        return variant;
    }

    public final String getValue() {
        return value.get();
    }

    public final void setValue(String value) {
        this.value.set(value);
    }

    public StringProperty valueProperty() {
        return value;
    }

    public final double getModifierAmount() {
        return modifierAmount.get();
    }

    public final void setModifierAmount(double value) {
        modifierAmount.set(value);
    }

    public DoubleProperty modifierAmountProperty() {
        return modifierAmount;
    }

    @Override
    public ItemAttributeEntity toEntity() {
        if (property == null || variant == null || value.get().isBlank()) {
            return null;
        }
        ItemAttributeEntity entity = new ItemAttributeEntity();
        entity.setId(id.get());
        entity.setModifierAmount(modifierAmount.get());
        entity.setValue(value.get());
        entity.setModifierType(modifierType);
        
        return entity;
    }

}






