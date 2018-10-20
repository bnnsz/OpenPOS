/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.inventory.entities;

import com.bizstudio.security.entities.Auditable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author ObinnaAsuzu
 */
@Entity
public class ItemEntity implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private LocalDateTime createdTimestamp;

    @Column
    private LocalDateTime updatedTimestamp;

    @OneToMany(mappedBy = "item")
    private List<ItemAttributeEntity> defaultAttributes = new ArrayList<>();

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
     * @return the createdTimestamp
     */
    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    /**
     * @param createdTimestamp the createdTimestamp to set
     */
    @Override
    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    /**
     * @return the updatedTimestamp
     */
    public LocalDateTime getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    /**
     * @param updatedTimestamp the updatedTimestamp to set
     */
    @Override
    public void setUpdatedTimestamp(LocalDateTime updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    /**
     * @return the defaultAttributes
     */
    public List<ItemAttributeEntity> getDefaultAttributes() {
        return defaultAttributes;
    }

    /**
     * @param defaultAttributes the defaultAttributes to set
     */
    public void setDefaultAttributes(List<ItemAttributeEntity> defaultAttributes) {
        this.defaultAttributes = defaultAttributes;
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
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bizstudio.inventory.entities.ItemEntity[ id=" + id + " ]";
    }

    

}
