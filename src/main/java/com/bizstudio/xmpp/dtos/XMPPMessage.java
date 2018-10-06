/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.xmpp.dtos;

/**
 *
 * @author ObinnaAsuzu
 */
public class XMPPMessage {
    private String message;
    private XMPPPayload payload;

    public XMPPMessage(String message, XMPPPayload payload) {
        this.message = message;
        this.payload = payload;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the payload
     */
    public Object getPayload() {
        return payload;
    }

    /**
     * @param payload the payload to set
     */
    public void setPayload(XMPPPayload payload) {
        this.payload = payload;
    }
    
}
