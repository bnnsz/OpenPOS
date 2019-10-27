/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.inventory.repositories;

import com.bizstudio.inventory.entities.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author obinna.asuzu
 */
public interface LocationRepository extends JpaRepository<LocationEntity, Long>{
}

