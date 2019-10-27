/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.entities;

import com.bizstudio.core.entities.AbstractEntity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.apache.shiro.authz.Permission;

/**
 *
 * @author obinna.asuzu
 */
@Entity
public class PrivilegeEntity extends AbstractEntity implements Serializable, Permission {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String value;

    @Column
    private boolean system;

    @ManyToOne
    private PrivilegeEntity parent;

    @OneToMany(mappedBy = "parent")
    private Set<PrivilegeEntity> children = new HashSet<>();

    public PrivilegeEntity() {
    }

    public PrivilegeEntity(String value) {
        this.value = value;
    }

    public PrivilegeEntity(String value, boolean system) {
        this.value = value;
        this.system = system;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the parent
     */
    public PrivilegeEntity getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(PrivilegeEntity parent) {
        this.parent = parent;
    }

    /**
     * @return the children
     */
    public Set<PrivilegeEntity> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(Set<PrivilegeEntity> children) {
        this.children = children;
    }

    /**
     * @return the system
     */
    public boolean isSystem() {
        return system;
    }

    /**
     * @param system the system to set
     */
    public void setSystem(boolean system) {
        this.system = system;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        PrivilegeEntity other = (PrivilegeEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getAuthority() {
        StringBuilder sb = new StringBuilder();
        if (getParent() != null) {
            sb.append(getParent().getAuthority()).append("_");
        }
        sb.append(value);
        return sb.toString();
    }

    @Override
    public String toString() {
        return "com.encooked.data.entities.PrivilegeEntity[ id=" + id + " ]";
    }

    @Override
    public String getDescription() {
        return "Privilege";
    }

    @Override
    public boolean implies(Permission p) {
        if (p instanceof PrivilegeEntity) {
            return this.value.equalsIgnoreCase(((PrivilegeEntity) p).value);
        }
        return false;
    }
}





