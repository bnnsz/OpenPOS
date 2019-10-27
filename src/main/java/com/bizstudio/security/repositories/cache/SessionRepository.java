/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.repositories.cache;


import com.bizstudio.security.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ObinnaAsuzu
 */
public interface SessionRepository extends JpaRepository<SessionEntity, Long>{


}




