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

import com.bizstudio.application.managers.NavigationManger;
import com.bizstudio.ui.components.application.NavigationBar;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author ObinnaAsuzu
 */
public class ApplicationContainer extends AnchorPane{

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private StackPane navContainer;
    @FXML
    private AnchorPane mainContainer;
    
    public ApplicationContainer() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/application/Application.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setResources(ResourceBundle.getBundle("bundles.application.lang", new Locale("en")));
        
        try {
            fxmlLoader.load();
            NavigationBar navigationBar = new NavigationBar(navContainer);
            navContainer.getChildren().add(navigationBar);
            mainContainer.getChildren().add(NavigationManger.getInstance(navigationBar).getStackPane());
            navContainer.setManaged(false);
            navContainer.setVisible(false);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    

}
