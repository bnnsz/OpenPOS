/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author ObinnaAsuzu
 */
@Converter
public class SessionAttributesConverter implements AttributeConverter<Map<Object, Object>, byte[]> {

   

    @Override
    public byte[] convertToDatabaseColumn(Map<Object, Object> attributes) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(attributes);
            out.flush();
            return bos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(SessionAttributesConverter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return null;
    }

    @Override
    public Map<Object, Object> convertToEntityAttribute(byte[] attributes) {

        ByteArrayInputStream bis = new ByteArrayInputStream(attributes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            return (Map<Object, Object>) in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SessionAttributesConverter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        
        return new HashMap<>();
    }
}
