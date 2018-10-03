/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.entities.controllers;

import com.bizstudio.security.entities.PrincipalEntity;
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
public class PrincipalEntityJpaController implements Serializable {

    public PrincipalEntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PrincipalEntity principalEntity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(principalEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PrincipalEntity principalEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            principalEntity = em.merge(principalEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = principalEntity.getId();
                if (findPrincipalEntity(id) == null) {
                    throw new NonexistentEntityException("The principalEntity with id " + id + " no longer exists.");
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
            PrincipalEntity principalEntity;
            try {
                principalEntity = em.getReference(PrincipalEntity.class, id);
                principalEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The principalEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(principalEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PrincipalEntity> findPrincipalEntities() {
        return findPrincipalEntities(true, -1, -1);
    }

    public List<PrincipalEntity> findPrincipalEntities(int maxResults, int firstResult) {
        return findPrincipalEntities(false, maxResults, firstResult);
    }

    private List<PrincipalEntity> findPrincipalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PrincipalEntity.class));
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

    public PrincipalEntity findPrincipalEntity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PrincipalEntity.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrincipalEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PrincipalEntity> rt = cq.from(PrincipalEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
