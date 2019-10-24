/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.repositories.data;

import com.bizstudio.security.entities.data.UserRoleEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ObinnaAsuzu
 */
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    
    public UserRoleEntity findFirstByName(String roleString);

    public List<UserRoleEntity> findByNameIn(List<String> roles);
    
}


