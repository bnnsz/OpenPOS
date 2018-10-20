/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.entities.controllers;

import com.bizstudio.security.entities.AuditLogEntity;
import com.bizstudio.security.entities.controllers.exceptions.NonexistentEntityException;
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
public class AuditLogEntityJpaController implements Serializable {

    public AuditLogEntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AuditLogEntity auditLogEntity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(auditLogEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AuditLogEntity auditLogEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            auditLogEntity = em.merge(auditLogEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = auditLogEntity.getId();
                if (findAuditLogEntity(id) == null) {
                    throw new NonexistentEntityException("The auditLogEntity with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AuditLogEntity auditLogEntity;
            try {
                auditLogEntity = em.getReference(AuditLogEntity.class, id);
                auditLogEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The auditLogEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(auditLogEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AuditLogEntity> findAuditLogEntityEntities() {
        return findAuditLogEntityEntities(true, -1, -1);
    }

    public List<AuditLogEntity> findAuditLogEntityEntities(int maxResults, int firstResult) {
        return findAuditLogEntityEntities(false, maxResults, firstResult);
    }

    private List<AuditLogEntity> findAuditLogEntityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AuditLogEntity.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AuditLogEntity findAuditLogEntity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AuditLogEntity.class, id);
        } finally {
            em.close();
        }
    }

    public int getAuditLogEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AuditLogEntity> rt = cq.from(AuditLogEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
