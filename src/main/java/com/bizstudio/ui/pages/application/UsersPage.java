/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.ui.pages.application;

import com.bizstudio.security.entities.UserAccountEntity;
import com.bizstudio.security.services.UserService;
import com.bizstudio.ui.components.application.Pagination;
import com.bizstudio.utils.Page;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 *
 * @author obinna.asuzu
 */
public class UsersPage extends ApplicationPage {

    @FXML
    private FlowPane menuItems;

    @FXML
    private Button createButton;
    @FXML
    private TableView<UserAccountEntity> userTable;
    @FXML
    private TableColumn<UserAccountEntity, Boolean> selectCol;
    @FXML
    private TableColumn<UserAccountEntity, String> usernameCol;
    @FXML
    private TableColumn<UserAccountEntity, String> nameCol;
    @FXML
    private TableColumn<UserAccountEntity, String> emailCol;
    @FXML
    private HBox paneFooter;
    
    private Pagination pagination;

    private UserService userService;

    public UsersPage() {
        userService = UserService.getInstance();
        
    }

    private void initComponents() {
        
        userTable.setRowFactory(tableView -> {
            final TableRow<UserAccountEntity> row = new TableRow<>();
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(createContextMenu(row))
                            .otherwise((ContextMenu) null));
            return row;
        });
        
        nameCol.setCellValueFactory((Callback<CellDataFeatures<UserAccountEntity, String>, ObservableValue<String>>) p -> {
            String firstname  = p.getValue().getPrincipal("firstname");
            String lastname  = p.getValue().getPrincipal("lastname");
            firstname = firstname == null? "": firstname;
            lastname = lastname == null? "": lastname;
            return new SimpleStringProperty((firstname + " " + lastname).trim());
        });
        usernameCol.setCellValueFactory((Callback<CellDataFeatures<UserAccountEntity, String>, ObservableValue<String>>) p -> {
            return new SimpleStringProperty(p.getValue().getUsername());
        });
        emailCol.setCellValueFactory((Callback<CellDataFeatures<UserAccountEntity, String>, ObservableValue<String>>) p -> {
            return new SimpleStringProperty(p.getValue().getPrincipal("email"));
        });
        
        selectCol.setCellValueFactory((Callback<CellDataFeatures<UserAccountEntity, Boolean>, ObservableValue<Boolean>>) p -> {
            return new SimpleBooleanProperty(p.getValue().isSelected());
        });

        selectCol.setCellFactory(new Callback<TableColumn<UserAccountEntity, Boolean>, TableCell<UserAccountEntity, Boolean>>() {
            @Override
            public TableCell<UserAccountEntity, Boolean> call(TableColumn<UserAccountEntity, Boolean> param) {
                return new CheckBoxTableCell<UserAccountEntity, Boolean>() {
                    {
                        setAlignment(Pos.CENTER);
                    }

                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        if (!empty) {
                            TableRow row = getTableRow();

                            if (row != null) {
                                int rowNo = row.getIndex();
                                TableViewSelectionModel sm = getTableView().getSelectionModel();
                                if (item) {
                                    sm.select(rowNo);
                                } else {
                                    sm.clearSelection(rowNo);
                                }
                            }
                        }
                        super.updateItem(item, empty);
                    }
                };
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

    private ContextMenu createContextMenu(TableRow<UserAccountEntity> row) {
        UserAccountEntity user = row.getItem();
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

    public void viewUser(UserAccountEntity user) {
        System.out.println("View "+user.getUsername());
    }

    public void deactivateUser(UserAccountEntity user) {
        System.out.println("deactivate "+user.getUsername());
    }

    public void sendMessage(UserAccountEntity user) {
        System.out.println("Send message to "+user.getUsername());
    }

    @Override
    public void onPageCreate() {
        //TODO ADD MENU
        initComponents();
        Page<UserAccountEntity> allUsers = userService.getAllUsers(Page.PageRequest.of(0, 10));
        pagination = new Pagination(allUsers, this::loadData);
        paneFooter.getChildren().add(pagination);
        userTable.setItems(FXCollections.observableArrayList(allUsers.getContent()));
    }

    private void loadData(Page.PageRequest request) {
        Page<UserAccountEntity> allUsers = userService.getAllUsers(request);
        userTable.getItems().clear();
        userTable.getItems().addAll(allUsers.getContent());
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
