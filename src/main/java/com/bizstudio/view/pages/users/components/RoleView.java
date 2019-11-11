/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.pages.users.components;

import com.bizstudio.core.utils.SvgLoader;
import com.bizstudio.view.models.Role;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 *
 * @author obinna.asuzu
 */
public class RoleView extends HBox {

    private static String ICON_PATH = "/images/application/icons/svg/";

    private static String ICON_CLASS = "-theme-primary-foreground-dark";

    @FXML
    Button removeButton;
    @FXML
    ImageView removeButtonIcon;
    @FXML
    Label nameLabel;
    Role role;
    
    Consumer<Role> callback;

    public RoleView(Role role) {

        try {
            String fxmlResource = "/fxml/components/user/RoleView.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlResource));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
            this.role = role;
            initializeComponents();
        } catch (IOException ex) {
            Logger.getLogger(CreateUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initializeComponents() {
        nameLabel.setText(role.getName());
        removeButtonIcon.setImage(SvgLoader.getInstance().loadSvgImage(ICON_PATH + "trash.svg", ICON_CLASS, true));
        removeButton.setOnAction(action -> {
            callback.accept(role);
            ((Pane) getParent()).getChildren().remove(this);
        });
    }

    public void setOnDelete(Consumer<Role> callback) {
        this.callback = callback;
    }

}










