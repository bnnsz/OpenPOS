/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.repositories;

import com.bizstudio.security.entities.AuditLogEntity;
import com.bizstudio.exceptions.NonexistentEntityException;
import com.bizstudio.utils.JpaRepository;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author ObinnaAsuzu
 */
public class AuditLogRepository extends JpaRepository<AuditLogEntity, Long> implements Serializable  {
    public AuditLogRepository(EntityManagerFactory emf, Class entityClass) {
        super(emf, entityClass);
    }
}