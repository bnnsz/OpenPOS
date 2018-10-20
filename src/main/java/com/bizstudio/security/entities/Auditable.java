/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.entities;

import com.google.gson.Gson;
import java.time.LocalDateTime;

/**
 *
 * @author ObinnaAsuzu
 */
public interface Auditable {

    public Long getId();

    public void setCreatedTimestamp (LocalDateTime dateTime);
    
    public void setUpdatedTimestamp (LocalDateTime dateTime);
    
    default String getState (){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
