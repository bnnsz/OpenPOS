/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.ui.components.login;

import com.bizstudio.security.service.PinToken;
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
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ObinnaAsuzu
 */
public class PinLoginInput extends VBox {

    @FXML
    PasswordField pin;
    private PinToken token;
    
    public PinLoginInput() {
        try {
            URL location = getClass().getResource("/fxml/components/login/PinLoginInput.fxml");
            ResourceBundle bundle = ResourceBundle.getBundle("bundles.application.lang", new Locale("en"));
            FXMLLoader fxmlLoader = new FXMLLoader(location, bundle);
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(PinLoginInput.class.getName()).log(Level.SEVERE, null, ex);
        }
        token = new PinToken(pin.getText());
    }
    
    public void setValueChangeHandler(InputAuthentcationHandler handler) {
        pin.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            token.setPin(newValue.toCharArray());
            handler.handle(token);
        });
    }

}
