/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.inventory.repositories;

import com.bizstudio.inventory.entities.LocationEntity;
import com.bizstudio.utils.JpaRepository;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author obinna.asuzu
 */
public class LocationRepository extends JpaRepository<LocationEntity, Long>{
    public LocationRepository(EntityManagerFactory emf) {
        super(emf, LocationEntity.class);
    }
}
