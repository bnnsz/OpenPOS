/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.repositories;

import com.bizstudio.security.entities.UserRoleEntity;
import com.bizstudio.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.ArrayList;
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
public class UserRoleRepository implements Serializable {

    public UserRoleRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserRoleEntity userRoleEntity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(userRoleEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserRoleEntity userRoleEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            userRoleEntity = em.merge(userRoleEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = userRoleEntity.getId();
                if (findUserRoleEntity(id) == null) {
                    throw new NonexistentEntityException("The userRoleEntity with id " + id + " no longer exists.");
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
            UserRoleEntity userRoleEntity;
            try {
                userRoleEntity = em.getReference(UserRoleEntity.class, id);
                userRoleEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userRoleEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(userRoleEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserRoleEntity> findUserRoleEntities() {
        return findUserRoleEntities(true, -1, -1);
    }

    public List<UserRoleEntity> findUserRoleEntities(int maxResults, int firstResult) {
        return findUserRoleEntities(false, maxResults, firstResult);
    }

    private List<UserRoleEntity> findUserRoleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserRoleEntity.class));
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

    public UserRoleEntity findUserRoleEntity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserRoleEntity.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserRoleEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserRoleEntity> rt = cq.from(UserRoleEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public UserRoleEntity findFirstByName(String roleString) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT c FROM UserRoleEntity AS c WHERE c.name = :role");
        query.setParameter("role", roleString);
        List<UserRoleEntity> result = query.setMaxResults(1).getResultList();
        if (result == null || result.isEmpty()){
            return null;
        }
        return result.get(0);
    }

    public List<UserRoleEntity> findByNames(List<String> roles) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT a FROM RoleEntity a WHERE a.name IN (:names)");
        query.setParameter("names", roles);
        List<UserRoleEntity> result = query.getResultList();
        return result == null ? new ArrayList<>() : result;
    }
    
}
