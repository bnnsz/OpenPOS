/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.repositories.data;

import com.bizstudio.security.entities.RoleEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author obinna.asuzu
 */
public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long>, JpaSpecificationExecutor<RoleEntity> {

    public Optional<RoleEntity> findByName(String name);
    
    public Optional<RoleEntity> findFirstByNameIn(List<String> names);
    
    public Optional<RoleEntity> findFirstByName(String roleString);

    public List<RoleEntity> findByNameIn(List<String> roles);
    
    @Query("SELECT DISTINCT a FROM RoleEntity a JOIN a.privileges p "
            + "WHERE p.value IN (:values)")
    public List<RoleEntity> findByPrivilegeValues(@Param("values") List<String> values);
    
    @Query("SELECT DISTINCT a FROM RoleEntity a "
            + "WHERE a.name IN (:names)")
    public List<RoleEntity> findAllByNames(@Param("names") List<String> names);
    
    @Query("SELECT a FROM RoleEntity a "
            + "WHERE a.name LIKE %:criteria%")
    public Page<RoleEntity> searchByCriteria(
            @Param("criteria") String criteria,
            Pageable pageable);
    
    

    public List<RoleEntity> deleteByName(String name);
    
    
}



