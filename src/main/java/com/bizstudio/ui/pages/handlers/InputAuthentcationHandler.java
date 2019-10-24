/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.ui.pages.handlers;

import org.apache.shiro.authc.AuthenticationToken;

/**
 *
 * @author ObinnaAsuzu
 */
public interface InputAuthentcationHandler {
    public void handle(AuthenticationToken token);
}
