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

import com.bizstudio.application.enums.NavigationRoute;
import com.bizstudio.application.managers.NavigationManger;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ObinnaAsuzu
 */
public class NavigationBar extends VBox{

    public NavigationBar() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/fxml/application/NavigationBar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    } 
    
    @FXML
    private void gotoHome(ActionEvent event) {
        System.out.println("Go to Home!");
        NavigationManger.getInstance().navigate(NavigationRoute.HOME);
    }
    
    @FXML
    private void gotoLogin(ActionEvent event) {
        System.out.println("Go to Login!");
        NavigationManger.getInstance().navigate(NavigationRoute.LOGIN);
    }
    
    @FXML
    private void goBack(ActionEvent event) {
        System.out.println("Go Back");
        NavigationManger.getInstance().previousPage();
    }
    
}
