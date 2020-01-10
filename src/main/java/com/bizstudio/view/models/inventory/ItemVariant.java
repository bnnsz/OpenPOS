/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.models.inventory;

import com.bizstudio.inventory.entities.ItemVariantEntity;
import com.bizstudio.view.models.ViewModel;
import java.util.ArrayList;
import java.util.Collection;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author obinna.asuzu
 */
public class ItemVariant implements ViewModel<ItemVariantEntity> {

    private final LongProperty id;
    private ItemDefinition itemDefinition;

    private ObservableList<ItemAttribute> attributes = FXCollections.observableList(new ArrayList<>());
    private ObservableList<ItemStock> stocks = FXCollections.observableList(new ArrayList<>());

    public ItemVariant() {
        this.id = new SimpleLongProperty(0);
        this.itemDefinition = null;
    }

    public ItemVariant(ItemVariantEntity entity) {
        if (entity == null) {
            this.id = new SimpleLongProperty(0);
            this.itemDefinition = null;
            return;
        }
        this.id = new SimpleLongProperty(entity.getId());
        this.itemDefinition = new ItemDefinition(entity.getItemDefinition());
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
     * @return the itemDefinition
     */
    public ItemDefinition getItemDefinition() {
        return itemDefinition;
    }

    /**
     * @param itemDefinition the itemDefinition to set
     */
    public void setItemDefinition(ItemDefinition itemDefinition) {
        this.itemDefinition = itemDefinition;
    }

    public void addAttributeListener(ListChangeListener<? super ItemAttribute> ll) {
        attributes.addListener(ll);
    }

    public void removeAttributeListener(ListChangeListener<? super ItemAttribute> ll) {
        attributes.removeListener(ll);
    }

    public boolean addAllAttributes(ItemAttribute... es) {
        return attributes.addAll(es);
    }

    public boolean setAllAttributes(ItemAttribute... es) {
        return attributes.setAll(es);
    }

    public boolean setAllAttributes(Collection<? extends ItemAttribute> clctn) {
        return attributes.setAll(clctn);
    }

    public boolean removeAllAttributes(ItemAttribute... es) {
        return attributes.removeAll(es);
    }

    public void removeAttribute(int i, int i1) {
        attributes.remove(i, i1);
    }

    public boolean addAttribute(ItemAttribute e) {
        return attributes.add(e);
    }

    public void addAttribute(int index, ItemAttribute element) {
        attributes.add(index, element);
    }

    public void addStockListener(ListChangeListener<? super ItemStock> ll) {
        stocks.addListener(ll);
    }

    public void removeStockListener(ListChangeListener<? super ItemStock> ll) {
        stocks.removeListener(ll);
    }

    public boolean addAllStocks(ItemStock... es) {
        return stocks.addAll(es);
    }

    public boolean setAllStocks(ItemStock... es) {
        return stocks.setAll(es);
    }

    public boolean setAllStocks(Collection<? extends ItemStock> clctn) {
        return stocks.setAll(clctn);
    }

    public boolean removeAllStocks(ItemStock... es) {
        return stocks.removeAll(es);
    }

    public void removeStock(int i, int i1) {
        stocks.remove(i, i1);
    }

    public boolean addStock(ItemStock e) {
        return stocks.add(e);
    }

    public void addStock(int index, ItemStock element) {
        stocks.add(index, element);
    }

    /**
     * @return the attributes
     */
    public ObservableList<ItemAttribute> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(ObservableList<ItemAttribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return the stocks
     */
    public ObservableList<ItemStock> getStocks() {
        return stocks;
    }

    /**
     * @param stocks the stocks to set
     */
    public void setStocks(ObservableList<ItemStock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public ItemVariantEntity toEntity() {
        if (this.itemDefinition == null) {
            return null;
        }

        ItemVariantEntity entity = new ItemVariantEntity();
        entity.setId(id.get());
        return entity;
    }

}

