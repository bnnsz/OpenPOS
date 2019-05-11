/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.utils;

import com.bizstudio.exceptions.NonexistentEntityException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author obinna.asuzu
 */
public class JpaRepository<Entity, Id> {

    final Class<Entity> c;
    final Field idField;
    final EntityManagerFactory emf;

    public JpaRepository(EntityManagerFactory emf, Class entityClass) {
        this.emf = emf;
        this.c = entityClass;
        this.idField = Arrays.asList(entityClass.getDeclaredFields())
                .stream()
                .filter(f -> f.isAnnotationPresent(javax.persistence.Id.class))
                .findFirst()
                .orElse(null);
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void save(Entity entity) {
        try {
            edit(entity);
        } catch (NonexistentEntityException ex) {
            create(entity);
        } catch (Exception ex) {
            Logger.getLogger(JpaRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void create(Entity entity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Entity entity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            entity = em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Id id = (Id) idField.get(entity);
                if (findById(id) == null) {
                    throw new NonexistentEntityException("The " + c.getSimpleName() + " with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Id id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entity entity;
            try {
                entity = em.getReference(c, id);
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The " + c.getSimpleName() + " with id " + id + " no longer exists.", enfe);
            }
            em.remove(entity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Entity> findAll() {
        return findAll(null, null);
    }
    
    public List<Entity> findAll(Sorting sorting) {
        return findAll(null, sorting);
    }
    
    public List<Entity> findAll(Page page) {
        return findAll(page, null);
    }

    public List<Entity> findAll(Page page, Sorting sorting) {
        EntityManager em = getEntityManager();
        try {
            String queryString = "SELECT a FROM " + c.getSimpleName() + " a";
            if (sorting != null) {
                queryString = queryString + " ORDER BY a." + sorting.getField() + " " + sorting.getOrder().getValue();
            }
            Query query = em.createQuery(queryString, c);
            if (page != null) {
                query.setMaxResults(page.getPageSize());
                query.setFirstResult(page.getPageSize() * page.getPageNo());
            }
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    

    public Entity findById(Id id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(c, id);
        } finally {
            em.close();
        }
    }

    public int getCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entity> rt = cq.from(c);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
