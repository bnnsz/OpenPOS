/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.core.conveters;

import com.bizstudio.ui.pages.application.ApplicationPage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.AttributeConverter;

/**
 *
 * @author ObinnaAsuzu
 */
public class PageConverter  implements AttributeConverter<Class<? extends ApplicationPage>, String> {

    @Override
    public String convertToDatabaseColumn(Class<? extends ApplicationPage> page) {
        return page.getClass().getName();
    }

    @Override
    public Class<? extends ApplicationPage> convertToEntityAttribute(String page) {
        try {
            return (Class<? extends ApplicationPage>) Class.forName(page);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PageConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
