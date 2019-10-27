/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.core.repositories;

import com.bizstudio.security.entities.UserEntity;
import com.bizstudio.core.entities.ImageEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author obinna.asuzu
 */
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    public List<ImageEntity> findByOwner(UserEntity owner);

    public Page<ImageEntity> findByOwner(UserEntity owner, Pageable page);

    public Optional<ImageEntity> findByFilePath(String path);
}

