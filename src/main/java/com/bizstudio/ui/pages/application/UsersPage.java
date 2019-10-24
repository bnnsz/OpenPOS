/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.ui.pages.application;

import com.bizstudio.security.entities.data.UserAccountEntity;
import com.bizstudio.security.services.UserService;
import com.bizstudio.ui.components.application.Pagination;
import com.bizstudio.ui.models.User;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 *
 * @author obinna.asuzu
 */
@Component
public class UsersPage extends ApplicationPage {

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

    private Pagination pagination;

    @Autowired
    private UserService userService;

    

    private void initComponents() {

        userTable.setRowFactory(tableView -> {
            final TableRow<User> row = new TableRow<>();
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(createContextMenu(row))
                            .otherwise((ContextMenu) null));
            return row;
        });
        userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        nameCol.setCellValueFactory(c -> c.getValue().getFullname());
        usernameCol.setCellValueFactory(c -> c.getValue().getUsername());
        emailCol.setCellValueFactory(c -> c.getValue().getEmail());
        selectCol.setCellValueFactory(c -> c.getValue().getSelected());

        selectCol.setCellFactory(new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
            @Override
            public TableCell<User, Boolean> call(TableColumn<User, Boolean> param) {
                CheckBoxTableCell<User, Boolean> checkBoxTableCell = new CheckBoxTableCell<User, Boolean>() {
                    {
                        setAlignment(Pos.CENTER);
                    }
                    
                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        if (!empty) {
                            TableRow row = getTableRow();

                            if (row != null) {
                                Integer rowNo = row.getIndex();
                                TableViewSelectionModel sm = getTableView().getSelectionModel();
                                if (item) {
                                    sm.select(rowNo);
                                    System.out.println("select "+rowNo);
                                    userTable.getItems().forEach(u -> {
                                        System.out.println("User "+u.getUsername()+" is selected? "+u.getSelected().getValue());
                                    });
                                } else {
                                    sm.clearSelection(rowNo);
                                    System.out.println("unselect "+rowNo);
                                    userTable.getItems().forEach(u -> {
                                        System.out.println("User "+u.getUsername()+" is selected? "+u.getSelected().getValue());
                                    });
                                }
                            }
                        }
                        super.updateItem(item, empty);
                    }
                };
                
                checkBoxTableCell.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        checkBoxTableCell.getTableRow();
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
                
                return checkBoxTableCell;
            }
        });
        selectCol.setEditable(true);
        selectCol.setMaxWidth(50);
        selectCol.setMinWidth(50);

        setWidth(selectCol, 0.1);
        setWidth(usernameCol, 0.2);
        setWidth(nameCol, 0.4);
        setWidth(emailCol, 0.3);

    }

    public void setWidth(TableColumn col, double width) {
        col.prefWidthProperty().bind(userTable.widthProperty().multiply(width));
        col.maxWidthProperty().bind(userTable.widthProperty().multiply(width));
        col.minWidthProperty().bind(userTable.widthProperty().multiply(width));
    }

    private ContextMenu createContextMenu(TableRow<User> row) {
        User user = row.getItem();
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem view = new MenuItem("More");
        view.setOnAction(event -> viewUser(user));

        MenuItem deactivate = new MenuItem("Deactivate");
        deactivate.setOnAction(event -> deactivateUser(user));

        MenuItem sendMessage = new MenuItem("Deactivate");
        sendMessage.setOnAction(event -> deactivateUser(user));
        rowMenu.getItems().addAll(view, sendMessage, deactivate);

        return rowMenu;
    }

    public void viewUser(User user) {
        System.out.println("View " + user.getUsername());
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
        Page<UserAccountEntity> allUsers = userService.getAllUsers(PageRequest.of(0, 10));
        pagination = new Pagination(allUsers, this::loadData);
        paneFooter.getChildren().add(pagination);
        userTable.setItems(FXCollections.observableArrayList(allUsers.getContent().stream()
                .map(User::new).collect(toList())));
    }

    private void loadData(PageRequest request) {
        Page<UserAccountEntity> allUsers = userService.getAllUsers(request);
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

}

