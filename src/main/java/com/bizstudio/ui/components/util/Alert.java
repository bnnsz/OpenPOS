/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.ui.components.util;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author ObinnaAsuzu
 */
public class Alert extends VBox {
    public static Alert create(String title, String message) {
        return Alert.create(title, message, null, null);
    }
    public static Alert create(String title, String message, String actionName, EventHandler<ActionEvent> actionEvent) {
        Alert alert = new Alert();
        alert.setPickOnBounds(true);
        alert.setMouseTransparent(false);
        if (title == null || title.isEmpty()) {
            alert.getChildren().remove(alert.titlePanel);
        } else {
            alert.titleLabel.setText(title);
        }
        
        alert.messageLabel.setText(message);
        if (actionEvent == null) {
            ((HBox) alert.actionButton.getParent()).getChildren().remove(alert.actionButton);
        } else {
            alert.actionButton.setText(actionName);
            alert.actionButton.setOnAction(actionEvent);
        }
        alert.addEvents();
        return alert;
    }

    @FXML
    private Label titleLabel;
    @FXML
    private Text messageLabel;
    @FXML
    private Button actionButton;
    @FXML
    private Button closeButton;
    @FXML
    private HBox titlePanel;

    public Alert() {
        try {
            URL location = getClass().getResource("/fxml/application/components/util/Alert.fxml");
            ResourceBundle bundle = ResourceBundle.getBundle("bundles.application.lang", new Locale("en"));
            FXMLLoader fxmlLoader = new FXMLLoader(location, bundle);
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(Alert.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void close() {
        animateTransitionOut(this, (ActionEvent event) -> {
            ((VBox) this.getParent()).getChildren().remove(this);
        });
    }

    private void addEvents(){
        closeButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            close();
        });
    }

    public Alert create(String message) {
        return Alert.create(null, message, null, null);
    }


    public Alert create(String message, String actionName, EventHandler<ActionEvent> actionEvent) {
        return Alert.create(null, message, actionName, actionEvent);
    }


    private void animateTransitionOut(Pane pane, EventHandler<ActionEvent> onfinish) {
        TranslateTransition translateTransition = new TranslateTransition(javafx.util.Duration.millis(200), pane);
        translateTransition.setFromX(pane.getLayoutBounds().getMinX());
        translateTransition.setToX(pane.getWidth());

        FadeTransition fadeTransition = new FadeTransition(javafx.util.Duration.millis(200), pane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        ScaleTransition scaleTransition = new ScaleTransition(javafx.util.Duration.millis(200), pane);
        scaleTransition.setFromX(1);
        scaleTransition.setToX(0);
        scaleTransition.setFromY(1);
        scaleTransition.setToY(0);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                fadeTransition,
                translateTransition,
                scaleTransition
        );

        parallelTransition.setOnFinished(onfinish);
        parallelTransition.play();
    }

}
