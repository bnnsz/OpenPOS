/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.repositories;

import com.bizstudio.security.entities.SessionEntity;
import com.bizstudio.utils.JpaRepository;
import java.io.Serializable;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ObinnaAsuzu
 */
public class SessionRepository extends JpaRepository<SessionEntity, Long> implements Serializable {

    public SessionRepository(EntityManagerFactory emf) {
        super(emf, SessionEntity.class);
    }

}
