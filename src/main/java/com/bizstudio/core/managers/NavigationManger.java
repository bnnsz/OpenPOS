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
package com.bizstudio.core.managers;

import com.bizstudio.core.enums.NavigationRoute;
import static com.bizstudio.core.enums.NavigationRoute.*;
import com.bizstudio.core.enums.PageState;
import com.bizstudio.core.utils.AutowireHelper;
import com.bizstudio.view.pages.application.components.NavigationBar;
import com.bizstudio.view.pages.application.components.PageStack;
import com.bizstudio.view.pages.application.ApplicationPage;
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
import javafx.util.Duration;

/**
 *
 * @author ObinnaAsuzu
 */
public class NavigationManger {

    public static NavigationManger getInstance(NavigationBar navigationBar) {
        NavigationManger instance = NavigationMangerHolder.INSTANCE;
        if (navigationBar != null) {
            instance.navigationBar = navigationBar;
        }
        if (instance.handler == null) {
            instance.openPage(LOGIN.page(), Collections.unmodifiableMap(new HashMap<>()));
        }
        return instance;
    }

    public static NavigationManger getInstance() {
        return getInstance(null);
    }

    private final PageStack stackPane;
    ObservableList<ApplicationPage> applicationPages;
    NavigationBar navigationBar;

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

    public void logout() {
        //TODO Navigation operation goes here;
        applicationPages.clear();
    }

    public void navigate(String route) {
        //TODO Navigation operation goes here;
        openPage(NavigationRoute.valueOf(route).page(), Collections.unmodifiableMap(new HashMap<>()));
    }

    public void navigate(NavigationRoute route) {
        //TODO Navigation operation goes here;
        openPage(route.page(), Collections.unmodifiableMap(new HashMap<>()));
    }

    public void showNavigation() {
        navigationBar.showNavigation();
    }

    public void hideNavigation() {
        navigationBar.hideNavigation();
    }

    public void previousPage() {
        if (applicationPages.size() > 1) {
            applicationPages.remove(applicationPages.size() - 1);
        }
    }

    /**
     * Initializes the page if not created and adds to page stack
     *
     * @param cls
     * @param parameters
     */
    private void openPage(Class<? extends ApplicationPage> cls, Map<String, Object> parameters) {
        System.out.println("lookup class: " + cls.getSimpleName() + " title: " + parameters.get("title"));
        Optional<ApplicationPage> page = applicationPages.stream()
                .filter(p -> {
                    System.out.println("     > class: " + p.getClass().getSimpleName() + " title: " + p.getTitle());
                    return p.getClass() == cls && p.getTitle().equals(parameters.get("title"));
                }).findFirst();
        if (page.isPresent()) {
            ApplicationPage current = page.get();
            if (!handler.equals(current)) {
                current.onPageResume();
                applicationPages.add(current);
                if (current.isShowNavigationBar()) {
                    showNavigation();
                } else {
                    hideNavigation();
                }
                handler = current;
            }
        } else {
            try {
                ApplicationPage newPage = AutowireHelper.getBean(cls);
                
                //TODO PASS PAGE PARAMS
                newPage.setPageState(PageState.NEW);
                newPage.onPageCreate();
                newPage.onNavigateEvent(parameters);
                newPage.setNavButton(navigationBar.getSelectedItem());
                if (newPage.isKeepInHistory()) {
                    applicationPages.add(newPage);
                } else {
                    openPage(newPage);
                }

                if (newPage.isShowNavigationBar()) {
                    showNavigation();
                } else {
                    hideNavigation();
                }
                handler = newPage;
            } catch (Exception ex) {
                Logger.getLogger(NavigationManger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void openPage(ApplicationPage newPage) {
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
    }

    private static class NavigationMangerHolder {

        private static final NavigationManger INSTANCE = new NavigationManger();
    }

    private class ListChangeListenerImpl implements ListChangeListener<ApplicationPage> {

        @Override
        public void onChanged(ListChangeListener.Change<? extends ApplicationPage> c) {
            c.next();
            if (c.wasAdded()) {
                ApplicationPage newPage = c.getList().get(c.getList().size() - 1);
                openPage(newPage);
            } else if (c.wasRemoved()) {
                if (c.getList().isEmpty()) {
                    navigate(NavigationRoute.LOGIN);
                } else {
                    ApplicationPage newPage = c.getList().get(c.getList().size() - 1);
                    if (!stackPane.getChildren().isEmpty()) {
                        ApplicationPage previousPage = (ApplicationPage) getStackPane().getChildren().get(getStackPane().getChildren().size() - 1);
                        if (previousPage != null && !newPage.equals(previousPage)) {
                            navigationBar.setSelectedItem(newPage.getNavButton());
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

            System.out.println("---> Check size");
            System.out.println("---> Check size  --> " + c.getList().size());
            if (c.getList().size() < 2) {
                System.out.println("---> Check size  < 2 ");
                navigationBar.hideBack();
            } else {
                System.out.println("---> Check size  > 1 ");
                navigationBar.showBack();
            }

            System.out.println("----------------------stack----------------------");
            for (ApplicationPage p : c.getList()) {
                System.out.println("[" + p.toString() + "]");
            }
        }
    }
}







