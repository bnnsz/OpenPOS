/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.inventory.entities.data;

import com.bizstudio.security.entities.data.AbstractEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author ObinnaAsuzu
 */
@Entity
public class ItemStockEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private LocationEntity location;
    
    @ManyToOne
    private ItemVariantEntity variant;
    
    @Column
    private int quantity;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the location
     */
    public LocationEntity getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    /**
     * @return the variant
     */
    public ItemVariantEntity getVariant() {
        return variant;
    }

    /**
     * @param variant the variant to set
     */
    public void setVariant(ItemVariantEntity variant) {
        this.variant = variant;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
        if (!(object instanceof ItemStockEntity)) {
            return false;
        }
        ItemStockEntity other = (ItemStockEntity) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.bizstudio.inventory.entities.StockEntity[ id=" + id + " ]";
    }
    
}
