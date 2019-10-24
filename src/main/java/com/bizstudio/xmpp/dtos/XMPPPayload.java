/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.xmpp.dtos;

import com.bizstudio.xmpp.enums.XmppPayloadAction;
import org.jivesoftware.smack.util.stringencoder.Base64;

/**
 *
 * @author ObinnaAsuzu
 */
public class XMPPPayload {
    private XmppPayloadAction action;
    private String data;

    public XMPPPayload(XmppPayloadAction action, byte[] data) {
        this.action = action;
        this.data = Base64.encodeToString(data);
    }

    /**
     * @return the action
     */
    public XmppPayloadAction getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(XmppPayloadAction action) {
        this.action = action;
    }

    /**
     * @return the base64EncodedString
     */
    public String getData() {
        return data;
    }
    
    public byte[] getDecodedData(){
        return Base64.decode(data);
    }

    /**
     * @param base64EncodedString the data to set
     * 
     */
    public void setData(String base64EncodedString) {
        this.data = base64EncodedString;
    }
    
}
