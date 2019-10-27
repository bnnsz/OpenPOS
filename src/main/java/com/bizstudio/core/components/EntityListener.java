/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.core.components;

import com.bizstudio.core.entities.AbstractEntity;
import com.bizstudio.core.entities.AuditLogEntity;
import com.bizstudio.core.enums.AuditLogAction;
import static com.bizstudio.core.enums.AuditLogAction.*;
import com.bizstudio.core.repositories.AuditLogEntityRepository;
import com.bizstudio.core.utils.AutowireHelper;
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
    AuditLogEntityRepository repository;

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
        AutowireHelper.autowire(this, this.repository);
        AuditLogEntity log = new AuditLogEntity();
        log.setTimestamp(LocalDateTime.now());
        log.setEntityRef(String.valueOf(item.getId()));
        log.setEntityName(item.getClass().getSimpleName());
        log.setAction(action);
        try {
            Object username = SecurityUtils.getSubject().getPrincipal();
            log.setUsername(username == null ? "system" : username.toString());
        } catch (Exception e) {
            log.setUsername("anonymous");
        }
        repository.save(log);
    }
}


