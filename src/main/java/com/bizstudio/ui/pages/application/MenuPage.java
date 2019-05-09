/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.ui.pages.application;

import com.bizstudio.application.enums.NavigationRoute;
import com.bizstudio.ui.components.util.MenuItem;
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
        initializeMenuItems();
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
        return object instanceof MenuPage;
    }

    @Override
    public void onNavigateEvent(Map<String, Object> parameters) {

    }

    private void initializeMenuItems() {
        menuItems.getChildren().addAll(
                new MenuItem("Home", NavigationRoute.HOME),
                new MenuItem("Transactions", NavigationRoute.TRANSACTIONS),
                new MenuItem("Inventory", NavigationRoute.INVENTORY),
                new MenuItem("Settings", NavigationRoute.SETTINGS)
        );
    }

}
