/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.inventory.repositories;

import com.bizstudio.inventory.entities.ItemEntity;
import com.bizstudio.utils.JpaRepository;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author obinna.asuzu
 */
public class ItemRepository extends JpaRepository<ItemEntity, Long>{
    public ItemRepository(EntityManagerFactory emf) {
        super(emf, ItemEntity.class);
    }
}
