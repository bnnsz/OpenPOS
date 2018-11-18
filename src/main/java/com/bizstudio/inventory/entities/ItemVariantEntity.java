/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.inventory.entities;

import com.bizstudio.inventory.entities.listeners.EntityListener;
import com.bizstudio.security.entities.AbstractEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
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
public class ItemVariantEntity extends AbstractEntity implements Serializable {

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private ItemEntity item;
    
    @OneToMany(mappedBy = "variant")
    private List<ItemAttributeEntity> attributes = new ArrayList<>();
    
    @OneToMany(mappedBy = "variant")
    private List<ItemStockEntity> stocks = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
     * @return the attributes
     */
    public List<ItemAttributeEntity> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(List<ItemAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return the stocks
     */
    public List<ItemStockEntity> getStocks() {
        return stocks;
    }

    /**
     * @param stocks the stocks to set
     */
    public void setStocks(List<ItemStockEntity> stocks) {
        this.stocks = stocks;
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
        if (!(object instanceof ItemVariantEntity)) {
            return false;
        }
        ItemVariantEntity other = (ItemVariantEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bizstudio.inventory.entities.ItemVariantEntity[ id=" + id + " ]";
    }

}
