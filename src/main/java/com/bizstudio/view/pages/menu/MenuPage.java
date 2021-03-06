/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.pages.menu;

import com.bizstudio.core.configs.MenuItemMap;
import com.bizstudio.view.pages.application.ApplicationPage;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author obinna.asuzu
 */
@Component
@Scope("prototype")
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
    public void onPageResume() {
    }

    @Override
    public void onPagePause() {
    }
    
    @Override
    public void onPageDestroy() {
    
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof MenuPage
                && (this.getTitle().equals(((MenuPage) object).getTitle()));
    }

    @Override
    public void onNavigateEvent(Map<String, Object> parameters) {
        setTitle((String) parameters.get("title"));
        menuItems.getChildren().clear();
        MenuItemMap menu = (MenuItemMap) parameters.get("menu");
        menu.getMenuItems().forEach(menuItem -> {
            menuItems.getChildren().add(menuItem);
        });
    }

}





