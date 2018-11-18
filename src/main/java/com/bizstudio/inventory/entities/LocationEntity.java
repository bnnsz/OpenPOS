/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.inventory.entities;

import com.bizstudio.security.entities.AbstractEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author ObinnaAsuzu
 */
@Entity
public class LocationEntity extends AbstractEntity implements Serializable {

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    private String name;
    
    @Column
    private String phoneNumber;
    
    @OneToOne
    private AddressEntity address;
    
    @OneToMany(mappedBy = "parentLocation")
    private List<LocationEntity> subLocations;
    
    @ManyToOne
    private LocationEntity parentLocation;
    
    @OneToMany(mappedBy = "location")
    private List<ItemStockEntity> stocks;
    
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
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the address
     */
    public AddressEntity getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    /**
     * @return the subLocations
     */
    public List<LocationEntity> getSubLocations() {
        return subLocations;
    }

    /**
     * @param subLocations the subLocations to set
     */
    public void setSubLocations(List<LocationEntity> subLocations) {
        this.subLocations = subLocations;
    }

    /**
     * @return the parentLocation
     */
    public LocationEntity getParentLocation() {
        return parentLocation;
    }

    /**
     * @param parentLocation the parentLocation to set
     */
    public void setParentLocation(LocationEntity parentLocation) {
        this.parentLocation = parentLocation;
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
        if (!(object instanceof LocationEntity)) {
            return false;
        }
        LocationEntity other = (LocationEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bizstudio.inventory.entities.LocationEntity[ id=" + id + " ]";
    }
    
}
