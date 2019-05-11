/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.repositories;

import com.bizstudio.security.entities.CredentialEntity;
import com.bizstudio.security.entities.PrincipalEntity;
import com.bizstudio.security.entities.UserAccountEntity;
import com.bizstudio.exceptions.NonexistentEntityException;
import com.bizstudio.utils.JpaRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author ObinnaAsuzu
 */
public class UserAccountRepository extends JpaRepository<UserAccountEntity, Long> implements Serializable {

    
    public UserAccountRepository(EntityManagerFactory emf) {
        super(emf, UserAccountEntity.class);
    }

    

    @Override
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

    @Override
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
                if (findById(id) == null) {
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

    @Override
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
