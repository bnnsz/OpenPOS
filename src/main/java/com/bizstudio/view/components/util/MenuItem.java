/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.components.util;

import com.bizstudio.core.enums.NavigationRoute;
import static com.bizstudio.core.managers.NavigationManger.getInstance;
import com.bizstudio.core.utils.SvgLoader;
import java.io.IOException;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author obinna.asuzu
 */
public class MenuItem extends Button {

    public static MenuItem menu(String name, NavigationRoute route, Map<String, Object> params) {
        params.putAll(route.getDefaultParameters());
        return new MenuItem(name, route, params);
    }

    public static MenuItem menu(String name, NavigationRoute route) {
        return new MenuItem(name, route, route.getDefaultParameters());
    }

    @FXML
    ImageView imageView;

    private static String ICON_CLASS = "-theme-primary-foreground-dark";

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

    /**
     * The icon file must bear the same name specified and must be Icon file
     * path:
     * "src/main/resources/images/application/icons/menu-icon-<b><i>name</i></b>.png"
     *
     * @param name
     * @param route
     */
    private MenuItem(String name, NavigationRoute route, Map<String, Object> params) {

        this(name, "/images/application/icons/svg/menu-icon-" + name.toLowerCase() + ".svg", name.toLowerCase());
        setOnAction(a -> getInstance().navigate(route, params));
    }

    private MenuItem(String name, String iconURL, String styleClass) {
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


