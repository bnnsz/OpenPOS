/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.inventory.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class ItemAttributeEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    private ItemPropEntity property;
    
    @Column(name="attribute_value")
    private String value;
    
    @Enumerated(EnumType.ORDINAL)
    private ItemAttrModifierType modifierType;
    
    @Column
    private double modifierAmount;
    
    @ManyToOne
    private ItemVariantEntity variant;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the property
     */
    public ItemPropEntity getProperty() {
        return property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(ItemPropEntity property) {
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
     * @return the modifierType
     */
    public ItemAttrModifierType getModifierType() {
        return modifierType;
    }

    /**
     * @param modifierType the modifierType to set
     */
    public void setModifierType(ItemAttrModifierType modifierType) {
        this.modifierType = modifierType;
    }

    /**
     * @return the modifierAmount
     */
    public double getModifierAmount() {
        return modifierAmount;
    }

    /**
     * @param modifierAmount the modifierAmount to set
     */
    public void setModifierAmount(double modifierAmount) {
        this.modifierAmount = modifierAmount;
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
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.bizstudio.inventory.entities.ItemAttributeEntity[ id=" + id + " ]";
    }
    
}
