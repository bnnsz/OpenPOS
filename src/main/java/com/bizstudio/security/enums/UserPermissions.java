/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.enums;

import org.apache.shiro.authz.Permission;

/**
 *
 * @author ObinnaAsuzu
 */
public enum UserPermissions implements Permission{
    
    
    ;
    final String value;
    private UserPermissions(String value) {
        this.value = value;
    }
    
    @Override
    public boolean implies(Permission p) {
        if(p instanceof UserPermissions){
            return this == (UserPermissions) p;
        }
        return false;
    }

    public String getValue() {
        return value;
    }
    
    
    
}
