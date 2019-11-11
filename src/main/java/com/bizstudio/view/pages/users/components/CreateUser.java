/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.pages.users.components;

import com.bizstudio.core.utils.AutowireHelper;
import com.bizstudio.core.utils.SvgLoader;
import com.bizstudio.security.services.UserService;
import com.bizstudio.view.models.Role;
import com.bizstudio.view.models.User;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author obinna.asuzu
 */
public class CreateUser extends VBox implements Initializable {

    private static String ICON_PATH = "/images/application/icons/svg/";

    private static String ICON_CLASS = "-theme-primary-foreground-dark";

    @FXML
    TextField username;
    @FXML
    TextField email;
    @FXML
    TextField phone;
    @FXML
    TextField firstName;
    @FXML
    TextField lastName;
    @FXML
    TextField otherNames;
    @FXML
    ComboBox<Role> roleComboBox;
    @FXML
    Button addRoleButton;
    @FXML
    Button saveButton;
    @FXML
    Button cancelButton;
    @FXML
    ImageView addRoleButtonIcon;
    @FXML
    VBox rolesVBox;

    Consumer<User> saveCallback;

    Runnable cancelCallback;

    UserService userService;

    List<Role> roles;

    User user;

    public CreateUser() {
        try {
            String fxmlResource = "/fxml/components/user/CreateUser.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlResource));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
            initializeComponents();
        } catch (IOException ex) {
            Logger.getLogger(CreateUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initializeComponents() {
        userService = AutowireHelper.getBean(UserService.class);
        user = new User();
        username.textProperty().bindBidirectional(user.getUsernameProperty());
        email.textProperty().bindBidirectional(user.getEmailProperty());
        phone.textProperty().bindBidirectional(user.getPhoneProperty());
        firstName.textProperty().bindBidirectional(user.getFirstnameProperty());
        lastName.textProperty().bindBidirectional(user.getLastnameProperty());
        otherNames.textProperty().bindBidirectional(user.getOthernamesProperty());

        addRoleButtonIcon.setImage(SvgLoader.getInstance().loadSvgImage(ICON_PATH + "add.svg", ICON_CLASS, true));

        roles = userService.getAllRoles().stream()
                .map(r -> new Role(r)).collect(Collectors.toList());

        roleComboBox.getItems().addAll(roles);
        roleComboBox.setCellFactory((ListView<Role> p) -> new ListCell<Role>() {
            @Override
            protected void updateItem(Role role, boolean empty) {
                super.updateItem(role, empty);

                if (empty) {
                    setText("");
                    setGraphic(null);
                } else {
                    setText(role.getName());
                }
            }
        });

        roleComboBox.setButtonCell(new ListCell<Role>() {
            @Override
            protected void updateItem(Role t, boolean bln) {
                super.updateItem(t, bln);
                if (bln) {
                    setText("");
                } else {
                    setText(t.getName());
                }
            }
        });

        addRoleButton.setOnAction(t -> {
            Role role = roleComboBox.getValue();
            user.getRoles().add(role);
            RoleView roleView = new RoleView(role);
            roleView.setOnDelete(r -> {
                roleComboBox.getItems().add(r);
                user.getRoles().remove(r);
            });
            rolesVBox.getChildren().add(roleView);
            roleComboBox.getItems().remove(role);
        });

        saveButton.setOnAction(action -> {
            close();
            saveCallback.accept(user);
        });

        cancelButton.setOnAction(action -> {
            close();
            cancelCallback.run();
        });

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void onSave(Consumer<User> callback) {
        saveCallback = callback;
    }

    public void onCancle(Runnable callback) {
        cancelCallback = callback;
    }

    public void close() {
        ((Pane) getParent()).setManaged(false);
        ((Pane) getParent()).setVisible(false);
        ((Pane) getParent()).getChildren().clear();
    }

}












