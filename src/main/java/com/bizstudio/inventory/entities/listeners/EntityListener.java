/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.inventory.entities.listeners;

import com.bizstudio.security.entities.AuditLogEntity;
import com.bizstudio.security.entities.Auditable;
import com.bizstudio.security.entities.controllers.AuditLogEntityJpaController;
import com.bizstudio.security.entities.enums.AuditLogAction;
import static com.bizstudio.security.entities.enums.AuditLogAction.*;
import com.bizstudio.security.service.PersistenceManger;
import com.google.gson.Gson;
import java.time.LocalDateTime;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import org.apache.shiro.SecurityUtils;

/**
 *
 * @author ObinnaAsuzu
 */
public class EntityListener {

    AuditLogEntityJpaController controller;

    public EntityListener() {
        this.controller = new AuditLogEntityJpaController(PersistenceManger.getInstance().getDataEMF());
    }

    @PrePersist
    public void prePersist(Auditable item) {
        item.setCreatedTimestamp(LocalDateTime.now());
        item.setUpdatedTimestamp(LocalDateTime.now());
        audit(item, CREATED);
    }

    @PreUpdate
    public void preUpdate(Auditable item) {
        item.setUpdatedTimestamp(LocalDateTime.now());
        audit(item, UPDATED);
    }

    @PreRemove
    public void preRemove(Auditable item) {
        audit(item, REMOVED);
    }

    public void audit(Auditable item, AuditLogAction action) {
        AuditLogEntity log = new AuditLogEntity();
        Gson gson = new Gson();
        log.setEntityState(gson.toJson(item));
        log.setTimestamp(LocalDateTime.now());
        log.setEntityRef(String.valueOf(item.getId()));
        log.setEntityName(item.getClass().getSimpleName());
        log.setEntityState(item.getState());
        log.setAction(action);
        log.setUsername(SecurityUtils.getSubject().getPrincipal().toString());
        controller.create(log);
    }
}
