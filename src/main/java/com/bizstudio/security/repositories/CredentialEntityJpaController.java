/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.repositories;

import com.bizstudio.security.entities.CredentialEntity;
import com.bizstudio.security.entities.UserAccountEntity;
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
public class CredentialEntityJpaController implements Serializable {

    public CredentialEntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CredentialEntity credentialEntity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserAccountEntity userAccount = credentialEntity.getUserAccount();
            if (userAccount != null) {
                userAccount = em.getReference(userAccount.getClass(), userAccount.getId());
                credentialEntity.setUserAccount(userAccount);
            }
            em.persist(credentialEntity);
            if (userAccount != null) {
                userAccount.getCredentials().add(credentialEntity);
                userAccount = em.merge(userAccount);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CredentialEntity credentialEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CredentialEntity persistentCredentialEntity = em.find(CredentialEntity.class, credentialEntity.getId());
            UserAccountEntity userAccountOld = persistentCredentialEntity.getUserAccount();
            UserAccountEntity userAccountNew = credentialEntity.getUserAccount();
            if (userAccountNew != null) {
                userAccountNew = em.getReference(userAccountNew.getClass(), userAccountNew.getId());
                credentialEntity.setUserAccount(userAccountNew);
            }
            credentialEntity = em.merge(credentialEntity);
            if (userAccountOld != null && !userAccountOld.equals(userAccountNew)) {
                userAccountOld.getCredentials().remove(credentialEntity);
                userAccountOld = em.merge(userAccountOld);
            }
            if (userAccountNew != null && !userAccountNew.equals(userAccountOld)) {
                userAccountNew.getCredentials().add(credentialEntity);
                userAccountNew = em.merge(userAccountNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = credentialEntity.getId();
                if (findCredentialEntity(id) == null) {
                    throw new NonexistentEntityException("The credentialEntity with id " + id + " no longer exists.");
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
            CredentialEntity credentialEntity;
            try {
                credentialEntity = em.getReference(CredentialEntity.class, id);
                credentialEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The credentialEntity with id " + id + " no longer exists.", enfe);
            }
            UserAccountEntity userAccount = credentialEntity.getUserAccount();
            if (userAccount != null) {
                userAccount.getCredentials().remove(credentialEntity);
                userAccount = em.merge(userAccount);
            }
            em.remove(credentialEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CredentialEntity> findCredentialEntityEntities() {
        return findCredentialEntityEntities(true, -1, -1);
    }

    public List<CredentialEntity> findCredentialEntityEntities(int maxResults, int firstResult) {
        return findCredentialEntityEntities(false, maxResults, firstResult);
    }

    private List<CredentialEntity> findCredentialEntityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CredentialEntity.class));
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

    public CredentialEntity findCredentialEntity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CredentialEntity.class, id);
        } finally {
            em.close();
        }
    }

    public int getCredentialEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CredentialEntity> rt = cq.from(CredentialEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
