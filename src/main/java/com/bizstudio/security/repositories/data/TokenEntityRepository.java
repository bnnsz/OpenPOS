/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.repositories.data;

import com.bizstudio.security.entities.TokenEntity;
import com.bizstudio.security.entities.UserEntity;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author obinna.asuzu
 */
public interface TokenEntityRepository extends JpaRepository<TokenEntity, Long> {

    public Optional<TokenEntity> findByValue(String value);

    public Stream<TokenEntity> findByUser_Username(String username);

    public Stream<TokenEntity> findByUser_Id(Long id);

    public Stream<TokenEntity> findByUser(UserEntity user);

    public Stream<TokenEntity> findByUserAndExpiredAndChannel(UserEntity user, boolean expired, String channel);
    
    public Stream<TokenEntity> findByUserAndExpired(UserEntity user, boolean expired);
}




