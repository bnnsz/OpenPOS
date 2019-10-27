/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.repositories.data;

import com.bizstudio.security.entities.UserEntity;
import com.bizstudio.security.entities.UserPrincipalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author obinna.asuzu
 */
public interface UserPrincipleEntityRepository  extends JpaRepository<UserPrincipalEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    
}
