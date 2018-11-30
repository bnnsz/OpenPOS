/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.service;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.env.DefaultEnvironment;

/**
 *
 * @author ObinnaAsuzu
 */
public class BasicIniEnvironment extends DefaultEnvironment { 

    public BasicIniEnvironment(Ini ini) { 
        setSecurityManager(new IniSecurityManagerFactory(ini).getInstance()); 
    } 

    public BasicIniEnvironment(String iniResourcePath) { 
        this(Ini.fromResourcePath(iniResourcePath)); 
    } 
}
