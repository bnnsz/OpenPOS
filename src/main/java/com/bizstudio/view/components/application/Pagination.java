/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.view.components.application;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * FXML Controller class
 *
 * @author obinna.asuzu
 */
public class Pagination extends HBox {

    @FXML
    private ComboBox<Integer> dropdown;
    @FXML
    private Button first;

    @FXML
    private HBox groupButtons;
    @FXML
    private Button last;

    private Page page;

    private final Consumer<PageRequest> consumer;

    private final ToggleGroup paginationGroup;

    public Pagination(Page page, Consumer<PageRequest> consumer) {
        this.page = page;
        this.consumer = consumer;
        this.paginationGroup = new ToggleGroup();

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/fxml/components/Pagination.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initiate();
            dropdown.setItems(FXCollections.observableArrayList(10, 20, 50, 100, 1000));
            dropdown.setValue(10);
            HBox.setHgrow(this, Priority.ALWAYS);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    private void initiate() {

        System.out.println("page no--> " + page.getNumber());
        System.out.println("page size--> " + page.getSize());
        System.out.println("Total elements--> " + page.getTotalElements());
        System.out.println("Total pages--> " + page.getTotalPages());

        int firstPage = page.isFirst() ? 1 : page.getNumber() - 1;
        int lastPage = page.getTotalPages();
        lastPage = firstPage + 5 > lastPage ? lastPage + 1 : firstPage + 5;

        first.setOnAction(event -> consume(1));
        first.setVisible(!page.isFirst());
        first.setManaged(!page.isFirst());

        last.setOnAction(event -> consume(page.getTotalPages()));
        last.setVisible(!page.isLast());
        last.setManaged(!page.isLast());

        List<ToggleButton> paginations = IntStream.range(firstPage, lastPage)
                .mapToObj(this::createToggleButton)
                .collect(Collectors.toList());

        groupButtons.getChildren().clear();
        groupButtons.getChildren().addAll(paginations);

    }

    public void update(Page page) {
        this.page = page;
        initiate();
    }

    
    private void consume(int pageNo) {
       consumer.accept(PageRequest.of(pageNo, dropdown.getValue()));
    }

    private ToggleButton createToggleButton(int pageNo) {
        ToggleButton button = new ToggleButton(pageNo + "");
        button.setSelected(page.getNumber() + 1 == pageNo);
        button.selectedProperty().addListener(new OnSelectListener(pageNo));
        button.setToggleGroup(paginationGroup);
        button.getStyleClass().add("btn-flat");
        return button;
    }

    class OnSelectListener implements ChangeListener<Boolean> {

        int pageNo;

        public OnSelectListener(int pageNo) {
            this.pageNo = pageNo - 1;
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue) {
                consumer.accept(PageRequest.of(pageNo, dropdown.getValue().intValue()));
            }
        }
    }

}



