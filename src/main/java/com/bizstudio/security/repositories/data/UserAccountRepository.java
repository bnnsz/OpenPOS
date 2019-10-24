/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.repositories.data;

import com.bizstudio.security.entities.data.UserAccountEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ObinnaAsuzu
 */
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

    

    public Optional<UserAccountEntity> findByUsername(String username);

    public Optional<UserAccountEntity> findByPin(String pin);

    @Query("SELECT DISTINCT a FROM UserAccountEntity a JOIN a.principals p WHERE (a.username = :username OR p.value = :email)")
    public List<UserAccountEntity> findByUsernameOrEmail(String username, String email);

    @Query("SELECT DISTINCT a FROM UserAccountEntity a JOIN a.principals p "
                + "WHERE (a.username LIKE %:criteria% "
                + "OR p.value LIKE %:criteria%)")
    public Page<UserAccountEntity> searchByCriteria(
            @Param("criteria") String criteria,
            Pageable pageable);

    @Query("SELECT DISTINCT a FROM UserAccountEntity a JOIN a.principals p "
            + "WHERE (p.value = :email)")
    public List<UserAccountEntity> findByEmail(@Param("email") String email);

}





