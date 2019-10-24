/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.core.enums;

/**
 *
 * @author obinna.asuzu
 */
public enum SortOrder {
    ASC("ASC"), DESC("DESC");
    String value;

    private SortOrder(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
