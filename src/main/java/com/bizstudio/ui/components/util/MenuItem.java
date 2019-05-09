/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.ui.components.util;

import com.bizstudio.application.enums.NavigationRoute;
import static com.bizstudio.application.managers.NavigationManger.getInstance;
import com.bizstudio.utils.SvgLoader;
import java.io.IOException;
import java.io.InputStream;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author obinna.asuzu
 */
public class MenuItem extends Button {
    
    @FXML
    ImageView imageView;

    public MenuItem(String name, String iconURL, String styleClass, NavigationRoute route) {
        this(name, iconURL, styleClass);
        setOnAction(a -> getInstance().navigate(route));
    }

    /**
     * The icon file must bear the same name specified and must be Icon file
     * path:
     * "src/main/resources/images/application/icons/menu-icon-<b><i>name</i></b>.png"
     *
     * @param name
     * @param route
     */
    public MenuItem(String name, NavigationRoute route) {
        this(name, "/images/application/icons/svg/menu-icon-" + name.toLowerCase() + ".svg", name.toLowerCase());
        setOnAction(a -> getInstance().navigate(route));
    }

    public MenuItem(String name, String iconURL, String styleClass) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/application/components/util/MenuItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setText(name.toUpperCase());
        getStyleClass().add(styleClass);
        imageView.setImage(SvgLoader.getInstance().loadSvgImage(iconURL));
    }

    public void setOnActionEvent(EventHandler eventHandler) {
        setOnAction(eventHandler);
    }

    
}
