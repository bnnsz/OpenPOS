/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.repositories;

import com.bizstudio.security.entities.UserPermissionEntity;
import com.bizstudio.exceptions.NonexistentEntityException;
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
public class UserPermissionEntityJpaController implements Serializable {

    public UserPermissionEntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserPermissionEntity userPermissionEntity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(userPermissionEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserPermissionEntity userPermissionEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            userPermissionEntity = em.merge(userPermissionEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = userPermissionEntity.getId();
                if (findUserPermissionEntity(id) == null) {
                    throw new NonexistentEntityException("The userPermissionEntity with id " + id + " no longer exists.");
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
            UserPermissionEntity userPermissionEntity;
            try {
                userPermissionEntity = em.getReference(UserPermissionEntity.class, id);
                userPermissionEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userPermissionEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(userPermissionEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserPermissionEntity> findUserPermissionEntities() {
        return findUserPermissionEntities(true, -1, -1);
    }

    public List<UserPermissionEntity> findUserPermissionEntities(int maxResults, int firstResult) {
        return findUserPermissionEntities(false, maxResults, firstResult);
    }

    private List<UserPermissionEntity> findUserPermissionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserPermissionEntity.class));
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

    public UserPermissionEntity findUserPermissionEntity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserPermissionEntity.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserPermissionEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserPermissionEntity> rt = cq.from(UserPermissionEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
