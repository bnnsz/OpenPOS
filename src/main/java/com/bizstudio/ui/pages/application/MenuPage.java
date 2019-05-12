/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.ui.pages.application;

import com.bizstudio.application.configs.MenuItemMap;
import com.bizstudio.application.enums.NavigationRoute;
import com.bizstudio.ui.components.util.MenuItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author obinna.asuzu
 */
public class MenuPage extends ApplicationPage {

    @FXML
    private FlowPane menuItems;

    public MenuPage() {
        setKeepInHistory(false);
        setShowNavigationBar(false);

    }

    @Override
    public void onPageCreate() {
        //TODO ADD MENU
    }

    @Override
    public void onPageDestroy() {
    }

    @Override
    public void onPageResume() {
    }

    @Override
    public void onPagePause() {
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof MenuPage && 
                
                (this.getTitle().equals(((MenuPage) object).getTitle()));
    }

    @Override
    public void onNavigateEvent(Map<String, Object> parameters) {
        setTitle((String) parameters.get("title"));
        MenuItemMap menu = (MenuItemMap) parameters.get("menu");
        if (menuItems.getChildren().isEmpty()) {
            menuItems.getChildren().addAll(menu.getMenuItems());
        }
    }

}
