/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.ui.components.util;

import com.bizstudio.application.enums.NavigationRoute;
import static com.bizstudio.application.managers.NavigationManger.getInstance;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author obinna.asuzu
 */
public class MenuItem extends VBox {

    @FXML
    private Button button;
    @FXML
    private ImageView imageView;
    @FXML
    private Text label;

    
    
    public MenuItem(String name, String iconURL, String styleClass, NavigationRoute route) {
        this(name, iconURL, styleClass);
        button.setOnAction(a -> getInstance().navigate(route));
    }
    
    /**
     * The icon file must bear the same name specified and must be
     * Icon file path: "src/main/resources/images/application/icons/menu-icon-<b><i>name</i></b>.png"
     * @param name
     * @param route 
     */
    public MenuItem(String name, NavigationRoute route) {
        this(name, "/images/application/icons/menu-icon-"+name.toLowerCase()+"png", name.toLowerCase());
        button.setOnAction(a -> getInstance().navigate(route));
    }

    public MenuItem(String name, String iconURL, String styleClass) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/fxml/application/MenuIcon.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        label.setText(name);
        imageView.setImage(new Image(getClass().getResourceAsStream(iconURL), 96, 96, true, true));
        button.getStyleClass().add(styleClass);
    }

    public void setOnActionEvent(EventHandler eventHandler) {
        button.setOnAction(eventHandler);
    }

}
