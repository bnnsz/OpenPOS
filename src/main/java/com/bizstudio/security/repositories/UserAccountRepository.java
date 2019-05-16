/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.repositories;

import com.bizstudio.security.entities.UserAccountEntity;
import com.bizstudio.utils.JpaRepository;
import com.bizstudio.utils.Page;
import com.bizstudio.utils.Page.PageRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author ObinnaAsuzu
 */
public class UserAccountRepository extends JpaRepository<UserAccountEntity, Long> implements Serializable {

    public UserAccountRepository(EntityManagerFactory emf) {
        super(emf, UserAccountEntity.class);
    }

    public Optional<UserAccountEntity> findByUsername(String username) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT c FROM UserAccountEntity AS c WHERE c.username = :username");
        query.setParameter("username", username);
        Stream<UserAccountEntity> result = query.getResultStream();
        return result.findFirst();
    }

    public Optional<UserAccountEntity> findByPin(String pin) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT c FROM UserAccountEntity AS c WHERE c.pin = :pin");
        query.setParameter("pin", pin);
        Stream<UserAccountEntity> result = query.getResultStream();
        return result.findFirst();
    }

    public List<UserAccountEntity> findByUsernameOrEmail(String username, String email) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT DISTINCT a FROM UserAccountEntity a JOIN a.principals p "
                + "WHERE (a.username = :username OR p.value = :email)");
        query.setParameter("username", username);
        query.setParameter("email", email);
        List<UserAccountEntity> result = query.getResultList();
        return result == null ? new ArrayList<>() : result;
    }

    public Page<UserAccountEntity> searchByCriteria(
            String criteria,
            PageRequest pageRequest) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT DISTINCT a FROM UserAccountEntity a JOIN a.principals p "
                + "WHERE (a.username LIKE %:criteria% "
                + "OR p.value LIKE %:criteria%)");
        query.setParameter("criteria", criteria);
        return getResultPage(query, pageRequest);
    }

    public List<UserAccountEntity> findByEmail(String email) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT DISTINCT a FROM UserAccountEntity a JOIN a.principals p "
            + "WHERE (p.value = :email)");
        query.setParameter("email", email);
        List<UserAccountEntity> result = query.getResultList();
        return result == null ? new ArrayList<>() : result;
    }

}
