/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.ui.components.login;

import com.bizstudio.ui.pages.handlers.InputAuthentcationHandler;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * FXML Controller class
 *
 * @author ObinnaAsuzu
 */
public class UsernamePasswordInput extends VBox {

    @FXML
    TextField username;
    @FXML
    PasswordField password;
    

    private UsernamePasswordToken token;
    

    public UsernamePasswordInput() {
        try {
            URL location = getClass().getResource("/fxml/components/login/UsernamePasswordInput.fxml");
            ResourceBundle bundle = ResourceBundle.getBundle("bundles.application.lang", new Locale("en"));
            FXMLLoader fxmlLoader = new FXMLLoader(location, bundle);
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(UsernamePasswordInput.class.getName()).log(Level.SEVERE, null, ex);
        }
        token = new UsernamePasswordToken(username.getText(), password.getText());
    }
    public void setValueChangeHandler(InputAuthentcationHandler handler) {
        username.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            token.setUsername(newValue);
            handler.handle(token);
        });
        
        password.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            token.setPassword(newValue.toCharArray());
            handler.handle(token);
        });
    }

}
