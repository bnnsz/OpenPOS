/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.inventory.entities;

import com.bizstudio.inventory.entities.listeners.EntityListener;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author ObinnaAsuzu
 */
@Entity
@EntityListeners(EntityListener.class)
public class ItemAttributeEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    private ItemPropertyEntity property;
    
    @Column(name="ATTRIBUTE_VALUE")
    private String value;
    
    @ManyToOne
    private ItemEntity item;
    
    @ManyToOne
    private ItemVariantEntity itemVariant;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the property
     */
    public ItemPropertyEntity getProperty() {
        return property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(ItemPropertyEntity property) {
        this.property = property;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the item
     */
    public ItemEntity getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(ItemEntity item) {
        this.item = item;
    }

    /**
     * @return the itemVariant
     */
    public ItemVariantEntity getItemVariant() {
        return itemVariant;
    }

    /**
     * @param itemVariant the itemVariant to set
     */
    public void setItemVariant(ItemVariantEntity itemVariant) {
        this.itemVariant = itemVariant;
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
        if (!(object instanceof ItemAttributeEntity)) {
            return false;
        }
        ItemAttributeEntity other = (ItemAttributeEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bizstudio.inventory.entities.ItemAttributeEntity[ id=" + id + " ]";
    }
    
}
