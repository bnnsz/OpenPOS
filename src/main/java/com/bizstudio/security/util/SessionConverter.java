/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.util;

import com.google.gson.Gson;
import javax.persistence.AttributeConverter;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;

/**
 *
 * @author ObinnaAsuzu
 */
public class SessionConverter implements AttributeConverter<Session, byte[]> {

    Gson gson = new Gson();

    @Override
    public byte[] convertToDatabaseColumn(Session session) {
        String data = gson.toJson((SimpleSession) session);
        return data.getBytes();
    }

    @Override
    public Session convertToEntityAttribute(byte[] credential) {
        String sessionData = new String(credential);
        return sessionData.isEmpty() ? null : gson.fromJson(sessionData, SimpleSession.class);
    }
}
