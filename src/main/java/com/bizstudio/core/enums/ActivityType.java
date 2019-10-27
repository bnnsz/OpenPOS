/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.core.enums;

/**
 *
 * @author ObinnaAsuzu
 */
public enum ActivityType {
    ACTIVATED("activated","success"),
    CREATED("created","success"),
    DEACTIVATED("deactivated","danger"),
    FEATURED("featured","success"),
    UNFEATURED("unfeatured","danger"),
    REGISTERED("registered","success"),
    REMOVED("removed","danger"),
    UPDATED("updated","primary"),
    LOGGED_IN("logged in","primary"),
    LOGGED_OUT("logged out","primary");

    private final String value;
    private final String actionClass;

    private ActivityType(String value) {
        this.value = value;
        this.actionClass = "default";
    }

    private ActivityType(String value, String actionClass) {
        this.value = value;
        this.actionClass = actionClass;
    }

    public String getValue() {
        return value;
    }

    /**
     * @return the actionClass
     */
    public String getActionClass() {
        return actionClass;
    }

}
