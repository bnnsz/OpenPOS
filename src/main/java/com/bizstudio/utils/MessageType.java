/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.utils;

/**
 *
 * @author ObinnaAsuzu
 */
public enum MessageType {
    DEFAULT("bg-color-white"),
    ERROR("bg-color-danger"),
    WARN("bg-color-warn"),
    INFO("bg-color-primary"),
    FAILURE("bg-color-danger"),
    SUCCESS("bg-color-success");
    String colorClass;

    private MessageType(String colorClass) {
        this.colorClass = colorClass;
    }

    public String getColorClass() {
        return colorClass;
    }
    
    
    
}
