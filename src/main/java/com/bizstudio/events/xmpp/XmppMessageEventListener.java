/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.events.xmpp;

/**
 *
 * @author ObinnaAsuzu
 */
@FunctionalInterface
public interface XmppMessageEventListener{
    public void handle(String from, String message, byte[] payload);
}
