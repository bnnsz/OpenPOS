/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.repositories.data;

import com.bizstudio.security.entities.UserEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author obinna.asuzu
 */
public interface UserEntityRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    public Optional<UserEntity> findByUsername(String username);

    public Optional<UserEntity> findByPin(String pin);

    @Query("SELECT DISTINCT a FROM UserEntity a JOIN a.principals p WHERE (a.username = :value) OR (p.key = 'email' AND p.value = :value) OR (p.key = 'phone' AND p.value = :value)")
    public Optional<UserEntity> findByUsernameOrEmailOrPhone(@Param("value") String value);

    @Query("SELECT DISTINCT a FROM UserEntity a JOIN a.principals p WHERE (a.username = :username) OR (p.key = 'email' AND p.value = :email) OR (p.key = 'phone' AND p.value = :phone)")
    public Optional<UserEntity> findByUsernameOrEmailOrPhone(
            @Param("username") String username,
            @Param("email") String email,
            @Param("phone") String phone);

    @Query("SELECT DISTINCT a FROM UserEntity a JOIN a.roles r "
            + "WHERE a.username = :username AND r.name IN (:roles)")
    public Optional<UserEntity> findByUsernameAndRolesIn(
            @Param("username") String criteria,
            @Param("roles") List<String> roles);

    @Query("SELECT DISTINCT a FROM UserEntity a JOIN a.roles r "
            + "WHERE r.name IN (:roles)")
    public List<UserEntity> findByRolesIn(
            @Param("roles") List<String> roles);

    @Query("SELECT DISTINCT a FROM UserEntity a JOIN a.principals p "
            + "WHERE (a.username LIKE %:criteria% "
            + "OR p.value LIKE %:criteria%) "
            + "AND (a.createdTimestamp BETWEEN :fromDate AND :toDate)")
    public Page<UserEntity> searchByCriteria(
            @Param("criteria") String criteria,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable);

    @Query("SELECT DISTINCT a FROM UserEntity a JOIN a.principals p "
            + "WHERE (p.key = :key AND LOWER(p.value) = LOWER(:value))")
    public Page<UserEntity> findByPrinciple(
            @Param("key") String key,
            @Param("value") String value, Pageable pageable);

    @Query("SELECT DISTINCT a FROM UserEntity a JOIN a.principals p "
            + "WHERE (p.key = :key AND LOWER(p.value) = LOWER(:value))")
    public List<UserEntity> findByPrinciple(
            @Param("key") String key,
            @Param("value") String value);

    public default Optional<UserEntity> findFirstByPrinciple(String key, String value) {
        Page<UserEntity> users = findByPrinciple(key, value, PageRequest.of(0, 1));
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.getContent().get(0));
    }

    @Query("SELECT DISTINCT a FROM UserEntity a JOIN a.principals p "
            + "WHERE (a.username LIKE %:criteria% "
            + "OR p.value LIKE %:criteria%)")
    public Page<UserEntity> searchByCriteria(
            @Param("criteria") String search,
            Pageable pageable);

}



