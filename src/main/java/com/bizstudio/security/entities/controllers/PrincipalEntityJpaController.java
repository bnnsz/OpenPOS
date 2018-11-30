/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.entities.controllers;

import com.bizstudio.security.entities.PrincipalEntity;
import com.bizstudio.security.entities.UserAccountEntity;
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
            UserAccountEntity userAccount = principalEntity.getUserAccount();
            if (userAccount != null) {
                userAccount = em.getReference(userAccount.getClass(), userAccount.getId());
                principalEntity.setUserAccount(userAccount);
            }
            em.persist(principalEntity);
            if (userAccount != null) {
                userAccount.getPrincipals().add(principalEntity);
                userAccount = em.merge(userAccount);
            }
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
            PrincipalEntity persistentPrincipalEntity = em.find(PrincipalEntity.class, principalEntity.getId());
            UserAccountEntity userAccountOld = persistentPrincipalEntity.getUserAccount();
            UserAccountEntity userAccountNew = principalEntity.getUserAccount();
            if (userAccountNew != null) {
                userAccountNew = em.getReference(userAccountNew.getClass(), userAccountNew.getId());
                principalEntity.setUserAccount(userAccountNew);
            }
            principalEntity = em.merge(principalEntity);
            if (userAccountOld != null && !userAccountOld.equals(userAccountNew)) {
                userAccountOld.getPrincipals().remove(principalEntity);
                userAccountOld = em.merge(userAccountOld);
            }
            if (userAccountNew != null && !userAccountNew.equals(userAccountOld)) {
                userAccountNew.getPrincipals().add(principalEntity);
                userAccountNew = em.merge(userAccountNew);
            }
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
            UserAccountEntity userAccount = principalEntity.getUserAccount();
            if (userAccount != null) {
                userAccount.getPrincipals().remove(principalEntity);
                userAccount = em.merge(userAccount);
            }
            em.remove(principalEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PrincipalEntity> findPrincipalEntityEntities() {
        return findPrincipalEntityEntities(true, -1, -1);
    }

    public List<PrincipalEntity> findPrincipalEntityEntities(int maxResults, int firstResult) {
        return findPrincipalEntityEntities(false, maxResults, firstResult);
    }

    private List<PrincipalEntity> findPrincipalEntityEntities(boolean all, int maxResults, int firstResult) {
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
