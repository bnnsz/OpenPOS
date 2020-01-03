/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.inventory.entities;

import com.bizstudio.core.components.EntityListener;
import com.bizstudio.security.entities.AbstractEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
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
public class ItemCategoryEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @ManyToOne
    ItemCategoryEntity parent;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<ItemCategoryEntity> children;
    @Column(columnDefinition = "TEXT")
    private String description;

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
     * @return the children
     */
    public List<ItemCategoryEntity> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<ItemCategoryEntity> children) {
        this.children = children;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof ItemCategoryEntity)) {
            return false;
        }
        ItemCategoryEntity other = (ItemCategoryEntity) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.bizstudio.inventory.entities.ItemCategoryEntity[ id=" + id + " ]";
    }

}





