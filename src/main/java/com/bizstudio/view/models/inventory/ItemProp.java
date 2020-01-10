/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.models.inventory;

import com.bizstudio.inventory.entities.ItemPropEntity;
import com.bizstudio.view.models.ViewModel;
import java.util.ArrayList;
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
public class ItemProp implements ViewModel<ItemPropEntity> {

    private final LongProperty id;
    private final StringProperty name;
    private final ListProperty<String> values;

    public ItemProp(ItemPropEntity entity) {
        if (entity == null) {
            id = new SimpleLongProperty(0);
            name = new SimpleStringProperty("");
            values = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
            return;
        }
        id = new SimpleLongProperty(entity.getId());
        name = new SimpleStringProperty(entity.getName());
        values = new SimpleListProperty<>(FXCollections.observableList(entity.getValues()));
    }

    public ItemProp() {
        id = new SimpleLongProperty(0);
        name = new SimpleStringProperty("");
        values = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
    }

    @Override
    public ItemPropEntity toEntity() {
        if (name.get().isBlank()) {
            return null;
        }
        ItemPropEntity entity = new ItemPropEntity();
        entity.setId(id.get());
        entity.setName(name.get());
        entity.setValues(values.get());
        return entity;
    }

}

