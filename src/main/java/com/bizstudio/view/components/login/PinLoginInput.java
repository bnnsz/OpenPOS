/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.components.login;

import com.bizstudio.core.utils.SvgLoader;
import com.bizstudio.security.services.PinToken;
import com.bizstudio.view.pages.handlers.InputAuthentcationHandler;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ObinnaAsuzu
 */
public class PinLoginInput extends VBox {

    private static String ICON_PATH = "/images/application/icons/svg/";

    private static String ICON_CLASS = "-fx-color-info-black";

    @FXML
    private PasswordField pin;
    @FXML
    ImageView backImageView;
    @FXML
    ImageView clearImageView;
    private PinToken token;

    public PinLoginInput() {
        try {
           
            URL location = getClass().getResource("/fxml/components/login/PinLoginInput.fxml");
            ResourceBundle bundle = ResourceBundle.getBundle("bundles.application.lang", new Locale("en"));
            FXMLLoader fxmlLoader = new FXMLLoader(location, bundle);
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
            System.out.println("--pin");
            backImageView.setImage(SvgLoader.getInstance().loadSvgImage(ICON_PATH+"back.svg","-fx-color-black",true,300,300));
            clearImageView.setImage(SvgLoader.getInstance().loadSvgImage(ICON_PATH+"trash.svg","-fx-color-red",true,300,300));
            System.out.println("--pin");
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






