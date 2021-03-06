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
package com.bizstudio.view.pages.application;

import com.bizstudio.core.entities.NotificationAction;
import com.bizstudio.core.managers.NavigationManger;
import com.bizstudio.core.managers.NotificationManager;
import com.bizstudio.view.pages.application.components.NavigationBar;
import com.bizstudio.view.pages.application.components.Alert;
import com.bizstudio.core.utils.ApplicationMessageUtil;
import com.bizstudio.view.pages.application.components.PageStack;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author ObinnaAsuzu
 */
@Component
public class ApplicationContainer extends AnchorPane implements Initializable{

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private StackPane navContainer;
    @FXML
    private AnchorPane mainContainer;
    @FXML
    private VBox notificationContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NavigationBar navigationBar = new NavigationBar(navContainer);
        navContainer.getChildren().add(navigationBar);
        PageStack stackPane = NavigationManger.getInstance(navigationBar).getStackPane();
        mainContainer.getChildren().add(stackPane);
        AnchorPane.setBottomAnchor(stackPane, 0.0);
        AnchorPane.setTopAnchor(stackPane, 0.0);
        AnchorPane.setLeftAnchor(stackPane, 0.0);
        AnchorPane.setRightAnchor(stackPane, 0.0);
        navContainer.setManaged(false);
        navContainer.setVisible(false);

        ApplicationMessageUtil.instantiate(this);

        initNotifications();
    }
    

    private void initNotifications() {
        notificationContainer.setManaged(false);
        notificationContainer.setVisible(false);
        notificationContainer.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Node> c) {
                notificationContainer.setManaged(!notificationContainer.getChildren().isEmpty());
                notificationContainer.setVisible(!notificationContainer.getChildren().isEmpty());
            }
        });
        NotificationManager.getInstance().addOnCreateListener(notification -> {
            NotificationAction action = notification.getAction();
            Alert alert;
            if (action != null) {
                alert = Alert.create(notification.getTitle(), notification.getMessage(), action.getName(), event -> {
                    action.execute();
                });
            } else {
                alert = Alert.create(notification.getTitle(), notification.getMessage());
            }
            addAlert(alert);
        });
    }

    public void addAlert(Alert alert) {
        animateTransitionIn(alert, notificationContainer);
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(Duration.ofSeconds(3).toMillis());
                Platform.runLater(() -> {
                    if (notificationContainer.getChildren().contains(alert)) {
                        animateTransitionOut(alert, (ActionEvent event) -> {
                            notificationContainer.getChildren().remove(alert);
                        });
                    }
                });
            } catch (InterruptedException ex) {
                Logger.getLogger(ApplicationContainer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread.start();
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

    private void animateTransitionIn(Pane pane, Pane parent) {
        animateTransitionIn(pane, parent, null);
    }

    private void animateTransitionIn(Pane pane, Pane parent, EventHandler<ActionEvent> onfinish) {
        pane.setTranslateX(parent.getWidth());
        pane.setOpacity(0);
        pane.setScaleX(0);
        pane.setScaleY(0);
        parent.getChildren().add(pane);

        TranslateTransition translateTransition = new TranslateTransition(javafx.util.Duration.millis(200), pane);
        translateTransition.setFromX(parent.getWidth());
        translateTransition.setToX(parent.getLayoutBounds().getMinX());

        FadeTransition fadeTransition = new FadeTransition(javafx.util.Duration.millis(200), pane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        ScaleTransition scaleTransition = new ScaleTransition(javafx.util.Duration.millis(200), pane);
        scaleTransition.setFromX(0);
        scaleTransition.setToX(1);
        scaleTransition.setFromY(0);
        scaleTransition.setToY(1);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                fadeTransition,
                translateTransition,
                scaleTransition
        );

        if (onfinish != null) {
            parallelTransition.setOnFinished(onfinish);
        }
        parallelTransition.play();

    }

    

}










