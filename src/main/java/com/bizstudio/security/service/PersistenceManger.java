/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ObinnaAsuzu
 */
public class PersistenceManger {

    

    private final EntityManagerFactory dataEMF = Persistence.createEntityManagerFactory("dataPU");
    private final EntityManagerFactory cacheEMF = Persistence.createEntityManagerFactory("cachePU");

    private PersistenceManger() {
    }

    public static PersistenceManger getInstance() {
        return PersistenceMangerHolder.INSTANCE;
    }
    
    /**
     * @return the dataEMF
     */
    public EntityManagerFactory getDataEMF() {
        return dataEMF;
    }

    /**
     * @return the cacheEMF
     */
    public EntityManagerFactory getCacheEMF() {
        return cacheEMF;
    }

    private static class PersistenceMangerHolder {

        private static final PersistenceManger INSTANCE = new PersistenceManger();
    }
}
