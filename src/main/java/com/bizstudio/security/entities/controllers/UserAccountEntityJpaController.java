/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.entities.controllers;

import com.bizstudio.security.entities.UserAccountEntity;
import com.bizstudio.security.entities.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityGraph;
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
public class UserAccountEntityJpaController implements Serializable {

    public UserAccountEntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserAccountEntity userAccountEntity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(userAccountEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserAccountEntity userAccountEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            userAccountEntity = em.merge(userAccountEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = userAccountEntity.getId();
                if (findUserAccountEntity(id) == null) {
                    throw new NonexistentEntityException("The userAccountEntity with id " + id + " no longer exists.");
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
            UserAccountEntity userAccountEntity;
            try {
                userAccountEntity = em.getReference(UserAccountEntity.class, id);
                userAccountEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userAccountEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(userAccountEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserAccountEntity> findUserAccountEntities() {
        return findUserAccountEntities(true, -1, -1);
    }

    public List<UserAccountEntity> findUserAccountEntities(int maxResults, int firstResult) {
        return findUserAccountEntities(false, maxResults, firstResult);
    }

    private List<UserAccountEntity> findUserAccountEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserAccountEntity.class));
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

    public UserAccountEntity findUserAccountEntity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserAccountEntity.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserAccountEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserAccountEntity> rt = cq.from(UserAccountEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<UserAccountEntity> findByUsernameWithRoles(String username) {
        EntityManager em = getEntityManager();
        
        List<UserAccountEntity> result = em.createQuery("SELECT c FROM UserAccountEntity AS c WHERE c.username = :username")
                .setParameter("username", username)
                .setHint("javax.persistence.fetchgraph", em.getEntityGraph("roles"))
                .getResultList();
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    public List<UserAccountEntity> findByUsername(String username) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT c FROM UserAccountEntity AS c WHERE c.username = :username");
        query.setParameter("username", username);
        List<UserAccountEntity> result = query.getResultList();
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

}
