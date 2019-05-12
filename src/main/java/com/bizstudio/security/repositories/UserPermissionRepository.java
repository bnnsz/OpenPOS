/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.repositories;

import com.bizstudio.security.entities.UserPermissionEntity;
import com.bizstudio.utils.JpaRepository;
import java.io.Serializable;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ObinnaAsuzu
 */
public class UserPermissionRepository extends JpaRepository<UserPermissionEntity, Long> implements Serializable {

    public UserPermissionRepository(EntityManagerFactory emf) {
        super(emf, UserPermissionEntity.class);
    }

   
}
