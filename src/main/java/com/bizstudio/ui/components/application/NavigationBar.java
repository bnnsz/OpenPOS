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
package com.bizstudio.ui.components.application;

import com.bizstudio.application.configs.MenuItemMap;
import com.bizstudio.application.enums.NavigationRoute;
import com.bizstudio.application.managers.NavigationManger;
import com.bizstudio.ui.components.util.MenuItem;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * FXML Controller class
 *
 * @author ObinnaAsuzu
 */
public class NavigationBar extends VBox {

    @FXML
    ToggleGroup navToggles;
    @FXML
    Button backButton;

    private final StackPane navContainer;

    public NavigationBar(StackPane navContainer) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/fxml/application/NavigationBar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.navContainer = navContainer;
    }

    @FXML
    private void gotoHome(ActionEvent event) {
        System.out.println("Go to Home!");
        NavigationManger.getInstance().navigate(NavigationRoute.HOME);
    }
    
    @FXML
    private void gotoMenu(ActionEvent event) {
        System.out.println("Go to Main Menu!");
        Map<String, Object> params = new HashMap<>();
        params.put("title", "Main Menu");
        params.put("menu", MenuItemMap.MAIN);
        NavigationManger.getInstance().navigate(NavigationRoute.MENU, params);
    }

    @FXML
    private void gotoLogin(ActionEvent event) {
        System.out.println("Go to Login!");
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            System.out.println("Logout user ==> " + event.toString());
            subject.logout();
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        System.out.println("Go Back");
        NavigationManger.getInstance().previousPage();
    }

    public ToggleButton getSelectedItem() {
        return (ToggleButton) navToggles.getSelectedToggle();
    }

    public void setSelectedItem(ToggleButton navButton) {
        navToggles.selectToggle(navButton);
    }

    public void showNavigation() {
        navContainer.setVisible(true);
        navContainer.setManaged(true);
    }

    public void hideNavigation() {
        navContainer.setVisible(false);
        navContainer.setManaged(false);
    }

    public void hideBack() {
        backButton.setVisible(false);
        backButton.setManaged(false);
    }

    public void showBack() {
        backButton.setVisible(true);
        backButton.setManaged(true);
    }

}
