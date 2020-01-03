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
import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author obinna.asuzu
 */
public class UserView extends VBox implements Initializable {

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

    private Consumer<User> saveCallback;
    private Consumer<User> editCallback;

    Runnable cancelCallback;

    UserService userService;

    List<Role> roles;

    User user = new User();
    SimpleBooleanProperty writeProperty = new SimpleBooleanProperty(false);
    private boolean edit = false;

    public static UserView create() {
        return new UserView(new User(), false);
    }

    public static UserView view(User user) {
        return new UserView(user, true);
    }

    public static UserView edit(User user) {
        UserView userView = new UserView(user, false);
        userView.edit = true;
        return userView;
    }

    private UserView(User user, boolean readOnly) {
        try {
            String fxmlResource = "/fxml/components/user/UserView.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlResource));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
            this.user = user;
            this.writeProperty.set(!readOnly);
            initializeComponents();
        } catch (IOException ex) {
            Logger.getLogger(UserView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void initializeComponents() {
        userService = AutowireHelper.getBean(UserService.class);
        username.editableProperty().bind(writeProperty);
        username.textProperty().bindBidirectional(user.getUsernameProperty());
        email.editableProperty().bind(writeProperty);
        email.textProperty().bindBidirectional(user.getEmailProperty());
        phone.editableProperty().bind(writeProperty);
        phone.textProperty().bindBidirectional(user.getPhoneProperty());
        firstName.editableProperty().bind(writeProperty);
        firstName.textProperty().bindBidirectional(user.getFirstnameProperty());
        lastName.editableProperty().bind(writeProperty);
        lastName.textProperty().bindBidirectional(user.getLastnameProperty());
        otherNames.editableProperty().bind(writeProperty);
        otherNames.textProperty().bindBidirectional(user.getOthernamesProperty());
        addRoleButton.managedProperty().bind(writeProperty);
        addRoleButton.visibleProperty().bind(writeProperty);
        addRoleButtonIcon.setImage(SvgLoader.getInstance().loadSvgImage(ICON_PATH + "add.svg", ICON_CLASS, true));
        roleComboBox.setCellFactory(roleCellFactory);
        roleComboBox.managedProperty().bind(writeProperty);
        roleComboBox.visibleProperty().bind(writeProperty);
        roleComboBox.setButtonCell(roleButtonCell);

        roles = userService.getAllRoles().stream()
                .map(r -> new Role(r)).collect(Collectors.toList());
        roleComboBox.getItems().addAll(roles);

        user.getRoles().forEach(role -> {
            RoleView roleView = new RoleView(role, writeProperty);
            roleView.setOnDelete(removeUserRole());
            rolesVBox.getChildren().add(roleView);
            roleComboBox.getItems().remove(role);
        });

        

        addRoleButton.setOnAction(t -> {
            Role role = roleComboBox.getValue();
            user.getRoles().add(role);
            RoleView roleView = new RoleView(role, writeProperty);
            roleView.setOnDelete(removeUserRole());
            rolesVBox.getChildren().add(roleView);
            roleComboBox.getItems().remove(role);
        });

        cancelButton.textProperty().bind(Bindings.createStringBinding(() -> writeProperty.get() ? "CANCEL" : "OK", writeProperty));
        saveButton.textProperty().bind(Bindings.createStringBinding(() -> writeProperty.get() ? "SAVE" : "EDIT", writeProperty));

        cancelButton.setOnAction(action -> {
            close();
            if (cancelCallback != null) {
                cancelCallback.run();
            }
        });

        saveButton.setOnAction(action -> {
            if (writeProperty.get()) {
                close();
                if (edit && editCallback != null) {
                    editCallback.accept(user);
                } else if (saveCallback != null) {
                    saveCallback.accept(user);
                }
            } else {
                writeProperty.set(true);
                edit = true;
            }
        });

    }

    private Consumer<Role> removeUserRole() {
        return r -> {
            roleComboBox.getItems().add(r);
            user.getRoles().remove(r);
        };
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

    public void onUpdate(Consumer<User> callback) {
        editCallback = callback;
    }

    public void onCancle(Runnable callback) {
        cancelCallback = callback;
    }

    public void close() {
        ((Pane) getParent()).setManaged(false);
        ((Pane) getParent()).setVisible(false);
        ((Pane) getParent()).getChildren().clear();
    }

    final Callback<ListView<Role>, ListCell<Role>> roleCellFactory = p -> new ListCell<Role>() {
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
    };

    final ListCell<Role> roleButtonCell = new ListCell<Role>() {
        @Override
        protected void updateItem(Role t, boolean bln) {
            super.updateItem(t, bln);
            if (bln) {
                setText("");
            } else {
                setText(t.getName());
            }
        }
    };

}



