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
package com.bizstudio.application.managers;

import com.bizstudio.application.enums.NavigationRoute;
import static com.bizstudio.application.enums.NavigationRoute.*;
import com.bizstudio.application.enums.PageState;
import com.bizstudio.ui.components.application.PageStack;
import com.bizstudio.ui.pages.application.ApplicationPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;

/**
 *
 * @author ObinnaAsuzu
 */
public class NavigationManger {

    private final PageStack stackPane;
    ObservableList<ApplicationPage> applicationPages;

    ApplicationPage handler;

    private NavigationManger() {
        this.stackPane = new PageStack();
        this.applicationPages = FXCollections.observableList(new ArrayList<>());
        this.applicationPages.addListener(new ListChangeListenerImpl());
    }

    /**
     * @return the stackPane
     */
    public PageStack getStackPane() {
        return stackPane;
    }

    public void navigate(NavigationRoute route, Map<String, Object> parameters) {
        //TODO Navigation operation goes here;
        openPage(route.page(), Collections.unmodifiableMap(parameters));
    }

    public void navigate(NavigationRoute route) {
        //TODO Navigation operation goes here;
        openPage(route.page(), Collections.unmodifiableMap(new HashMap<>()));
    }

    public void previousPage() {
        if (applicationPages.size() > 1) {
            applicationPages.remove(applicationPages.size() - 1);
        }
    }

    public static NavigationManger getInstance() {
        NavigationManger instance = NavigationMangerHolder.INSTANCE;
        if (instance.handler == null) {
            instance.openPage(LOGIN.page(), Collections.unmodifiableMap(new HashMap<>()));
        }
        return instance;
    }

    private void openPage(Class<? extends ApplicationPage> cls, Map<String, Object> parameters) {
        Optional<ApplicationPage> page = applicationPages.stream()
                .filter(p -> p.getClass() == cls)
                .findFirst();
        if (page.isPresent()) {
            ApplicationPage current = page.get();
            if (!handler.equals(current)) {
                current.onPageResume();
                applicationPages.add(current);
                handler = current;
            }
        } else {
            try {
                ApplicationPage newPage = cls.newInstance();
                String simpleName = cls.getSimpleName();
                String fxmlResource = String.format("/fxml/pages/%s.fxml", simpleName);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlResource));
                fxmlLoader.setRoot(newPage);
                fxmlLoader.setController(newPage);
                fxmlLoader.load();
                //TODO PASS PAGE PARAMS
                newPage.setPageState(PageState.NEW);
                newPage.onPageCreate();
                newPage.onNavigateEvent(parameters);
                applicationPages.add(newPage);
                handler = newPage;
            } catch (IOException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(NavigationManger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static class NavigationMangerHolder {

        private static final NavigationManger INSTANCE = new NavigationManger();
    }

    private class ListChangeListenerImpl implements ListChangeListener<ApplicationPage> {

        @Override
        public void onChanged(Change<? extends ApplicationPage> c) {
            c.next();
            if (c.wasAdded()) {
                ApplicationPage newPage = c.getList().get(c.getList().size() - 1);
                if (stackPane.getChildren().isEmpty()) {
                    if (newPage != null) {
                        getStackPane().getChildren().add(newPage);
                        handler = newPage;
                        newPage.setPageState(PageState.ACTIVE);
                    }
                } else {
                    ApplicationPage previousPage = (ApplicationPage) getStackPane().getChildren().get(getStackPane().getChildren().size() - 1);
                    if (newPage != null && previousPage != null && !newPage.equals(previousPage)) {
                        //ANIMATE IN NEW

                        // Set up a Translate Transition for the Text object
                        newPage.setTranslateX(getStackPane().getWidth());
                        newPage.setOpacity(0);
                        newPage.setScaleX(0);
                        newPage.setScaleY(0);
                        getStackPane().getChildren().add(newPage);

                        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), newPage);
                        translateTransition.setFromX(getStackPane().getWidth());
                        translateTransition.setToX(getStackPane().getLayoutBounds().getMinX());

                        FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), newPage);
                        fadeTransition.setFromValue(0);
                        fadeTransition.setToValue(1);

                        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), newPage);
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

                        parallelTransition.setOnFinished((ActionEvent event) -> {
                            getStackPane().getChildren().remove(previousPage);
                        });
                        parallelTransition.play();

                        handler = newPage;
                        previousPage.setPageState(PageState.PAUSED);
                        previousPage.onPagePause();
                        newPage.setPageState(PageState.ACTIVE);
                    }
                }
            } else if (c.wasRemoved()) {
                if (c.getList().isEmpty()) {
                    navigate(NavigationRoute.LOGIN);
                } else {
                    ApplicationPage newPage = c.getList().get(c.getList().size() - 1);
                    if (!stackPane.getChildren().isEmpty()) {
                        ApplicationPage previousPage = (ApplicationPage) getStackPane().getChildren().get(getStackPane().getChildren().size() - 1);
                        if (previousPage != null && !newPage.equals(previousPage)) {
                            getStackPane().getChildren().add(getStackPane().getChildren().size() - 1, newPage);

                            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), previousPage);
                            translateTransition.setFromX(previousPage.getLayoutBounds().getMinX());
                            translateTransition.setToX(previousPage.getWidth());

                            FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), previousPage);
                            fadeTransition.setFromValue(1);
                            fadeTransition.setToValue(0);

                            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), previousPage);
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

                            parallelTransition.setOnFinished((ActionEvent event) -> {
                                getStackPane().getChildren().remove(previousPage);
                                previousPage.setTranslateX(getStackPane().getLayoutBounds().getMinX());
                                previousPage.setOpacity(1);
                                previousPage.setScaleX(1);
                                previousPage.setScaleY(1);
                            });
                            parallelTransition.play();
                            //ANIMATE AWAY LAST
                            newPage.setPageState(PageState.ACTIVE);
                            handler = newPage;
                            if (!c.getList().contains(previousPage)) {
                                previousPage.onPageDestroy();
                            } else {
                                previousPage.setPageState(PageState.PAUSED);
                                previousPage.onPagePause();
                            }

                        }
                    }
                }
            }
        }
    }
}
