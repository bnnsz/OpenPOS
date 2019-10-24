/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.inventory.entities.data;

import com.bizstudio.inventory.listeners.EntityListener;
import com.bizstudio.security.entities.data.AbstractEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author ObinnaAsuzu
 */
@Entity
@EntityListeners(EntityListener.class)
public class ItemEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    private String name;
    
    @ManyToOne
    private ItemCategoryEntity category;
    
    @Column
    private boolean stockable;
    
    @OneToMany
    private List<ItemPropEntity> properties = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<ItemVariantEntity> variants = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the category
     */
    public ItemCategoryEntity getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(ItemCategoryEntity category) {
        this.category = category;
    }

    /**
     * @return the stockable
     */
    public boolean isStockable() {
        return stockable;
    }

    /**
     * @param stockable the stockable to set
     */
    public void setStockable(boolean stockable) {
        this.stockable = stockable;
    }

    /**
     * @return the properties
     */
    public List<ItemPropEntity> getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(List<ItemPropEntity> properties) {
        this.properties = properties;
    }

    /**
     * @return the variants
     */
    public List<ItemVariantEntity> getVariants() {
        return variants;
    }

    /**
     * @param variants the variants to set
     */
    public void setVariants(List<ItemVariantEntity> variants) {
        this.variants = variants;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemEntity)) {
            return false;
        }
        ItemEntity other = (ItemEntity) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.bizstudio.inventory.entities.ItemEntity[ id=" + id + " ]";
    }

    

}
