/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.inventory.listeners;

import com.bizstudio.security.entities.data.AuditLogEntity;
import com.bizstudio.security.entities.data.AbstractEntity;
import com.bizstudio.security.repositories.data.AuditLogRepository;
import com.bizstudio.security.enums.AuditLogAction;
import static com.bizstudio.security.enums.AuditLogAction.*;
import com.bizstudio.security.services.PersistenceManger;
import com.google.gson.Gson;
import java.time.LocalDateTime;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ObinnaAsuzu
 */
public class EntityListener {

    @Autowired
    private AuditLogRepository controller;

    

    @PrePersist
    public void prePersist(AbstractEntity item) {
        item.setCreatedTimestamp(LocalDateTime.now());
        item.setUpdatedTimestamp(LocalDateTime.now());
        audit(item, CREATED);
    }

    @PreUpdate
    public void preUpdate(AbstractEntity item) {
        item.setUpdatedTimestamp(LocalDateTime.now());
        audit(item, UPDATED);
    }

    @PreRemove
    public void preRemove(AbstractEntity item) {
        audit(item, REMOVED);
    }

    public void audit(AbstractEntity item, AuditLogAction action) {
        AuditLogEntity log = new AuditLogEntity();
        Gson gson = new Gson();
        log.setEntityState(gson.toJson(item));
        log.setTimestamp(LocalDateTime.now());
        log.setEntityRef(String.valueOf(item.getId()));
        log.setEntityName(item.getClass().getSimpleName());
        log.setAction(action);
        log.setUsername(SecurityUtils.getSubject().getPrincipal().toString());
        controller.save(log);
    }
}


