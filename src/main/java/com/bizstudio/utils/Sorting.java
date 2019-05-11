/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.utils;

import com.bizstudio.enums.SortOrder;

/**
 *
 * @author obinna.asuzu
 */
public class Sorting {
    private final String field;
    private final SortOrder order;

    private Sorting(String field, SortOrder order) {
        this.field = field;
        this.order = order;
    }
    
    public static Sorting by(String field, SortOrder order){
        return new Sorting(field, order);
    }

    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @return the order
     */
    public SortOrder getOrder() {
        return order;
    }
    
    
    
}
