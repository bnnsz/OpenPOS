/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.models;

import com.jfoenix.controls.JFXCheckBox;
import java.util.Map;
import java.util.function.Consumer;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;

/**
 *
 * @author obinna.asuzu
 */
public interface FXTable<T> {

    default Callback<TableColumn<T, Boolean>, TableCell<T, Boolean>> getCellFactory() {
        return (TableColumn<T, Boolean> param) -> {
            CheckBoxTableCell<T, Boolean> checkBoxTableCell = new CheckBoxTableCell<T, Boolean>() {
                {
                    setAlignment(Pos.CENTER);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }

                @Override
                public void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        TableRow row = getTableRow();
                        if (row != null) {
                            Integer rowNo = row.getIndex();
                            TableView.TableViewSelectionModel sm = getTableView().getSelectionModel();
                            JFXCheckBox checkBox = new JFXCheckBox();
                            checkBox.setSelected(item);
                            setGraphic(checkBox);
                            if (item) {
                                sm.select(rowNo);
                            } else {
                                sm.clearSelection(rowNo);
                            }
                        }
                    }

                }
            };

            checkBoxTableCell.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                checkBoxTableCell.getTableRow();
            });

            return checkBoxTableCell;
        };
    }

    default Callback<TableView<T>, TableRow<T>> getRowFactory(Map<String, Consumer<T>> menu) {
        return tableView -> {
            final TableRow<T> row = new TableRow<>();

            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(createContextMenu(row, menu))
                            .otherwise((ContextMenu) null));
            return row;
        };
    }

    default ContextMenu createContextMenu(TableRow<T> row, Map<String, Consumer<T>> menu) {
        final ContextMenu rowMenu = new ContextMenu();
        menu.entrySet().forEach(menuItem -> {
            MenuItem view = new MenuItem(menuItem.getKey());
            view.setOnAction(event -> menuItem.getValue().accept(row.getItem()));
            rowMenu.getItems().add(view);
        });
        return rowMenu;
    }

    default void setWidth(TableColumn col, double width) {
        col.prefWidthProperty().bind(getTable().widthProperty().multiply(width));
        col.maxWidthProperty().bind(getTable().widthProperty().multiply(width));
        col.minWidthProperty().bind(getTable().widthProperty().multiply(width));
    }

    public TableView<T> getTable();
}



