/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.pages.users;

import com.bizstudio.core.utils.SvgLoader;
import com.bizstudio.security.entities.UserEntity;
import com.bizstudio.security.services.UserService;
import com.bizstudio.view.pages.application.components.Pagination;
import com.bizstudio.view.models.FXTable;
import com.bizstudio.view.models.Role;
import com.bizstudio.view.models.User;
import com.bizstudio.view.pages.application.ApplicationPage;
import com.bizstudio.view.pages.users.components.UserView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 *
 * @author obinna.asuzu
 */
@Component
@Scope("prototype")
public class UsersPage extends ApplicationPage implements FXTable<User> {

    @FXML
    private FlowPane menuItems;

    @FXML
    private Button createButton;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Boolean> selectCol;
    @FXML
    private TableColumn<User, String> usernameCol;
    @FXML
    private TableColumn<User, String> nameCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private HBox paneFooter;
    @FXML
    private VBox detailsPanel;
    @FXML
    private VBox tablePanel;

    private Pagination pagination;

    @Autowired
    private UserService userService;

    @FXML
    ImageView searchIcon;

    private void initComponents() {

        searchIcon.setImage(SvgLoader.getInstance().loadSvgImage("/images/application/icons/svg/search.svg", "-theme-primary-foreground-dark", true));

        userTable.setRowFactory(getRowFactory(createContextMenu()));
        userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        nameCol.setCellValueFactory(c -> c.getValue().getFirstnameProperty());
        usernameCol.setCellValueFactory(c -> c.getValue().getUsernameProperty());
        emailCol.setCellValueFactory(c -> c.getValue().getEmailProperty());
        selectCol.setCellValueFactory(c -> c.getValue().getSelectedProperty());

        selectCol.setCellFactory(getCellFactory());
        selectCol.setEditable(true);
        selectCol.setMaxWidth(50);
        selectCol.setMinWidth(50);

        setWidth(selectCol, 0.1);
        setWidth(usernameCol, 0.2);
        setWidth(nameCol, 0.4);
        setWidth(emailCol, 0.3);

        detailsPanel.setManaged(false);
        detailsPanel.setVisible(false);

        detailsPanel.managedProperty().addListener((ov, oldValue, isManaged) -> {
            if (isManaged && getWidth() < 620) {
                tablePanel.setManaged(false);
                tablePanel.setVisible(false);
            } else {
                tablePanel.setManaged(true);
                tablePanel.setVisible(true);
            }
        });

        widthProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue.doubleValue() < 620 && detailsPanel.isManaged()) {
                tablePanel.setManaged(false);
                tablePanel.setVisible(false);
            } else if (newValue.doubleValue() >= 620 && !tablePanel.isManaged()) {
                tablePanel.setManaged(true);
                tablePanel.setVisible(true);
            }
        });

        createButton.setOnAction(action -> {
            createUser();
        });

    }

    @Override
    public TableView<User> getTable() {
        return userTable;
    }

    private void createUser() {
        UserView createUserView = UserView.create();
        createUserView.onSave(u -> {
            this.createUser(u);
        });

        createUserView.onCancle(() -> {

        });
        detailsPanel.getChildren().clear();
        detailsPanel.getChildren().add(createUserView);
        detailsPanel.setManaged(true);
        detailsPanel.setVisible(true);

    }

    private Map<String, Consumer<User>> createContextMenu() {
        Map<String, Consumer<User>> menu = new HashMap<>();
        menu.put("More", user -> viewUser(user));
        menu.put("Deactivate", user -> deactivateUser(user));
        menu.put("Send Message", user -> deactivateUser(user));
        return menu;
    }

    public void viewUser(User user) {
        UserView userView = UserView.view(user);
        userView.onUpdate(u -> {
            this.updateUser(u);
        });
        detailsPanel.getChildren().clear();
        detailsPanel.getChildren().add(userView);
        detailsPanel.setManaged(true);
        detailsPanel.setVisible(true);
    }

    public void deactivateUser(User user) {
        System.out.println("deactivate " + user.getUsername());
    }

    public void sendMessage(User user) {
        System.out.println("Send message to " + user.getUsername());
    }

    @Override
    public void onPageCreate() {
        //TODO ADD MENU
        initComponents();
        Page<UserEntity> allUsers = userService.getAllUsers(PageRequest.of(0, 10));
        pagination = new Pagination(allUsers, this::loadData);
        paneFooter.getChildren().add(pagination);
        userTable.setItems(FXCollections.observableArrayList(allUsers.getContent().stream()
                .map(User::new).collect(toList())));
    }

    private void loadData(PageRequest request) {
        Page<UserEntity> allUsers = userService.getAllUsers(request);
        userTable.getItems().clear();
        userTable.getItems().addAll(allUsers.getContent().stream()
                .map(User::new).collect(toList()));
        pagination.update(allUsers);
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
        return object instanceof UsersPage;
    }

    @Override
    public void onNavigateEvent(Map<String, Object> parameters) {

    }

    private void createUser(User user) {
        try {
            UserEntity createdUser = userService.createUser(
                    user.getUsername(),
                    user.getFirstname(),
                    user.getLastname(),
                    user.getOthernames(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getRoles().stream().map(Role::getName).collect(toList()));
            userTable.getItems().add(new User(createdUser));
        } catch (Exception ex) {
            Logger.getLogger(UsersPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateUser(User user) {
        try {
            Map<String, String> principles = new HashMap<>();
            String username = user.getUsername();

            principles.put("firstname", user.getFirstname());
            principles.put("lastname", user.getLastname());
            principles.put("othernames", user.getOthernames());
            principles.put("email", user.getEmail());
            principles.put("phone", user.getPhone());
            List<String> roles = user.getRoles().stream().map(r -> r.getName()).collect(toList());
            userService.updateUserProfile(username, principles, roles);
        } catch (Exception ex) {
            Logger.getLogger(UsersPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}




