/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.entities.controllers;

import com.bizstudio.security.entities.CredentialEntity;
import com.bizstudio.security.entities.PrincipalEntity;
import com.bizstudio.security.entities.UserAccountEntity;
import com.bizstudio.security.entities.controllers.exceptions.NonexistentEntityException;
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
public class UserAccountEntityJpaController implements Serializable {

    public UserAccountEntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserAccountEntity userAccountEntity) {
        if (userAccountEntity.getCredentials() == null) {
            userAccountEntity.setCredentials(new ArrayList<>());
        }
        if (userAccountEntity.getPrincipals() == null) {
            userAccountEntity.setPrincipals(new ArrayList<>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(userAccountEntity);
            List<CredentialEntity> attachedCredentials = new ArrayList<>();
            attachedCredentials.add(new CredentialEntity("pin", userAccountEntity.getPin()));
            for (CredentialEntity credentialToAttach : userAccountEntity.getCredentials()) {
                credentialToAttach.setUserAccount(userAccountEntity);
                em.persist(credentialToAttach);
            }
            userAccountEntity.setCredentials(attachedCredentials);
            
            List<PrincipalEntity> attachedPrincipals = new ArrayList<>();
            attachedPrincipals.add(new PrincipalEntity("username", userAccountEntity.getUsername()));
            for (PrincipalEntity principalToAttach : userAccountEntity.getPrincipals()) {
                principalToAttach.setUserAccount(userAccountEntity);
                em.persist(principalToAttach);
            }
            userAccountEntity.setPrincipals(attachedPrincipals);
            
            
            
            for (CredentialEntity credential : userAccountEntity.getCredentials()) {
                UserAccountEntity oldUserAccountOfCredential = credential.getUserAccount();
                credential.setUserAccount(userAccountEntity);
                credential = em.merge(credential);
                if (oldUserAccountOfCredential != null) {
                    oldUserAccountOfCredential.getCredentials().remove(credential);
                    oldUserAccountOfCredential = em.merge(oldUserAccountOfCredential);
                }
            }
            
            
            for (PrincipalEntity principalsPrincipalEntity : userAccountEntity.getPrincipals()) {
                UserAccountEntity oldUserAccountOfPrincipalsPrincipalEntity = principalsPrincipalEntity.getUserAccount();
                principalsPrincipalEntity.setUserAccount(userAccountEntity);
                principalsPrincipalEntity = em.merge(principalsPrincipalEntity);
                if (oldUserAccountOfPrincipalsPrincipalEntity != null) {
                    oldUserAccountOfPrincipalsPrincipalEntity.getPrincipals().remove(principalsPrincipalEntity);
                    oldUserAccountOfPrincipalsPrincipalEntity = em.merge(oldUserAccountOfPrincipalsPrincipalEntity);
                }
            }
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
            UserAccountEntity persistentUserAccountEntity = em.find(UserAccountEntity.class, userAccountEntity.getId());
            List<CredentialEntity> credentialsOld = persistentUserAccountEntity.getCredentials();
            List<CredentialEntity> credentialsNew = userAccountEntity.getCredentials();
            List<PrincipalEntity> principalsOld = persistentUserAccountEntity.getPrincipals();
            List<PrincipalEntity> principalsNew = userAccountEntity.getPrincipals();
            List<CredentialEntity> attachedCredentialsNew = new ArrayList<>();
            for (CredentialEntity credentialsNewCredentialEntityToAttach : credentialsNew) {
                credentialsNewCredentialEntityToAttach = em.getReference(credentialsNewCredentialEntityToAttach.getClass(), credentialsNewCredentialEntityToAttach.getId());
                attachedCredentialsNew.add(credentialsNewCredentialEntityToAttach);
            }
            credentialsNew = attachedCredentialsNew;
            userAccountEntity.setCredentials(credentialsNew);
            List<PrincipalEntity> attachedPrincipalsNew = new ArrayList<>();
            for (PrincipalEntity principalsNewPrincipalEntityToAttach : principalsNew) {
                principalsNewPrincipalEntityToAttach = em.getReference(principalsNewPrincipalEntityToAttach.getClass(), principalsNewPrincipalEntityToAttach.getId());
                attachedPrincipalsNew.add(principalsNewPrincipalEntityToAttach);
            }
            principalsNew = attachedPrincipalsNew;
            userAccountEntity.setPrincipals(principalsNew);
            userAccountEntity = em.merge(userAccountEntity);
            for (CredentialEntity credentialsOldCredentialEntity : credentialsOld) {
                if (!credentialsNew.contains(credentialsOldCredentialEntity)) {
                    credentialsOldCredentialEntity.setUserAccount(null);
                    credentialsOldCredentialEntity = em.merge(credentialsOldCredentialEntity);
                }
            }
            for (CredentialEntity credentialsNewCredentialEntity : credentialsNew) {
                if (!credentialsOld.contains(credentialsNewCredentialEntity)) {
                    UserAccountEntity oldUserAccountOfCredentialsNewCredentialEntity = credentialsNewCredentialEntity.getUserAccount();
                    credentialsNewCredentialEntity.setUserAccount(userAccountEntity);
                    credentialsNewCredentialEntity = em.merge(credentialsNewCredentialEntity);
                    if (oldUserAccountOfCredentialsNewCredentialEntity != null && !oldUserAccountOfCredentialsNewCredentialEntity.equals(userAccountEntity)) {
                        oldUserAccountOfCredentialsNewCredentialEntity.getCredentials().remove(credentialsNewCredentialEntity);
                        oldUserAccountOfCredentialsNewCredentialEntity = em.merge(oldUserAccountOfCredentialsNewCredentialEntity);
                    }
                }
            }
            for (PrincipalEntity principalsOldPrincipalEntity : principalsOld) {
                if (!principalsNew.contains(principalsOldPrincipalEntity)) {
                    principalsOldPrincipalEntity.setUserAccount(null);
                    principalsOldPrincipalEntity = em.merge(principalsOldPrincipalEntity);
                }
            }
            for (PrincipalEntity principalsNewPrincipalEntity : principalsNew) {
                if (!principalsOld.contains(principalsNewPrincipalEntity)) {
                    UserAccountEntity oldUserAccountOfPrincipalsNewPrincipalEntity = principalsNewPrincipalEntity.getUserAccount();
                    principalsNewPrincipalEntity.setUserAccount(userAccountEntity);
                    principalsNewPrincipalEntity = em.merge(principalsNewPrincipalEntity);
                    if (oldUserAccountOfPrincipalsNewPrincipalEntity != null && !oldUserAccountOfPrincipalsNewPrincipalEntity.equals(userAccountEntity)) {
                        oldUserAccountOfPrincipalsNewPrincipalEntity.getPrincipals().remove(principalsNewPrincipalEntity);
                        oldUserAccountOfPrincipalsNewPrincipalEntity = em.merge(oldUserAccountOfPrincipalsNewPrincipalEntity);
                    }
                }
            }
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
            List<CredentialEntity> credentials = userAccountEntity.getCredentials();
            for (CredentialEntity credentialsCredentialEntity : credentials) {
                credentialsCredentialEntity.setUserAccount(null);
                credentialsCredentialEntity = em.merge(credentialsCredentialEntity);
            }
            List<PrincipalEntity> principals = userAccountEntity.getPrincipals();
            for (PrincipalEntity principalsPrincipalEntity : principals) {
                principalsPrincipalEntity.setUserAccount(null);
                principalsPrincipalEntity = em.merge(principalsPrincipalEntity);
            }
            em.remove(userAccountEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserAccountEntity> findUserAccountEntityEntities() {
        return findUserAccountEntityEntities(true, -1, -1);
    }

    public List<UserAccountEntity> findUserAccountEntityEntities(int maxResults, int firstResult) {
        return findUserAccountEntityEntities(false, maxResults, firstResult);
    }

    private List<UserAccountEntity> findUserAccountEntityEntities(boolean all, int maxResults, int firstResult) {
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
    
    
    public List<UserAccountEntity> findByPin(String pin) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT c FROM UserAccountEntity AS c WHERE c.pin = :pin");
        query.setParameter("pin", pin);
        List<UserAccountEntity> result = query.getResultList();
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

}
