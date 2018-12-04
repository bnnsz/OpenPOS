/*
 * Copyright 2018 BizStudioSoft.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bizstudio.ui.pages.application;

import com.bizstudio.application.OpenPOS;
import com.bizstudio.application.enums.NavigationRoute;
import com.bizstudio.application.managers.NavigationManger;
import com.bizstudio.application.managers.NetworkCommunicationManager;
import com.bizstudio.security.entities.interfaces.Principal;
import com.bizstudio.security.service.PinToken;
import com.bizstudio.ui.components.login.PinLoginInput;
import com.bizstudio.ui.components.login.UsernamePasswordInput;
import com.bizstudio.ui.pages.handlers.InputAuthentcationHandler;
import com.bizstudio.utils.ApplicationMessageUtil;
import com.bizstudio.xmpp.dtos.XMPPMessage;
import com.bizstudio.xmpp.dtos.XMPPPayload;
import com.bizstudio.xmpp.enums.XmppPayloadAction;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXCheckBox;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;

/**
 * FXML Controller class
 *
 * @author ObinnaAsuzu
 */
public class LoginPage extends ApplicationPage {

    @FXML
    StackPane inputStack;
    @FXML
    ToggleButton passwordToggle;
    @FXML
    ToggleButton pinToggle;
    @FXML
    ToggleGroup loginInputType;
    @FXML
    private JFXCheckBox rememberMe;
    @FXML
    private Button loginButton;

    AuthenticationToken token;

    public LoginPage() {
        setKeepInHistory(false);
    }
    
    

    @Override
    public void onPageCreate() {
        PinLoginInput pinLoginInput = new PinLoginInput();
        UsernamePasswordInput passwordInput = new UsernamePasswordInput();

        pinLoginInput.setValueChangeHandler((AuthenticationToken passwordToken) -> {
            ((PinToken) passwordToken).setRememberMe(rememberMe.selectedProperty().getValue());
            token = passwordToken;
        });

        passwordInput.setValueChangeHandler((AuthenticationToken passwordToken) -> {
            ((UsernamePasswordToken) passwordToken).setRememberMe(rememberMe.selectedProperty().getValue());
            token = passwordToken;
        });

        loginButton.setOnAction((ActionEvent event) -> {
            login();
        });

        inputStack.getChildren().clear();
        inputStack.getChildren().add(passwordInput);
        passwordToggle.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue) {
                inputStack.getChildren().clear();
                inputStack.getChildren().add(passwordInput);
            }
        });

        pinToggle.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue) {
                inputStack.getChildren().clear();
                inputStack.getChildren().add(pinLoginInput);
            }
        });

    }

    public void login() {
//        NetworkCommunicationManager.getInstance().addIncomingMessageListener(XmppPayloadAction.INENTORY_TRANSFER, (String from, String message, byte[] payload) -> {
//            OpenPOS.Test obj = new Gson().fromJson(new String(payload), OpenPOS.Test.class);
//        });
//        
//
//        NetworkCommunicationManager.getInstance().sendData(new XMPPMessage("Hi! World", new XMPPPayload(XmppPayloadAction.INENTORY_TRANSFER, data)), "bnnsz@asuzuobinna.com");
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken();
        usernamePasswordToken.setUsername("superuser");
        usernamePasswordToken.setPassword("password123".toCharArray());
        if (token != null) {
            try {
                SecurityUtils.getSubject().login(token);
                Session session = SecurityUtils.getSubject().getSession(true);
                session.setAttribute("user", ((Principal) SecurityUtils.getSubject().getPrincipal()).getValue());
                NavigationManger.getInstance().navigate(NavigationRoute.HOME);
            } catch (AuthenticationException e) {
                ApplicationMessageUtil.getInstance().addMessage(e.getMessage());
                System.out.println();
            }
        } else {
            ApplicationMessageUtil.getInstance().addMessage("Invalid Credential");
        }

    }

    @Override

    public void onPageDestroy() {
    }

    @Override
    public void onPageResume() {
    }

    @Override
    public void onPagePause() {
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof LoginPage;
    }

    @Override
    public void onNavigateEvent(Map<String, Object> parameters) {

    }

}
