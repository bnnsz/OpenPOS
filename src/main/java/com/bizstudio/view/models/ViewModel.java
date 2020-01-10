/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.models;

import com.bizstudio.security.entities.AbstractEntity;

/**
 *
 * @author obinna.asuzu
 */
@FunctionalInterface
public interface ViewModel<Entity extends AbstractEntity> {
    public abstract Entity toEntity();
}




