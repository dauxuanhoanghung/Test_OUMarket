/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import com.tester.pojo.Employee;
import com.tester.service.EmployeeService;
import com.tester.service.impl.EmployeeServiceImpl;
import com.tester.utils.MessageBox;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author LENOVO
 */
public class ManageEmployeeController extends AbstractManageController {

    @FXML
    private TableView tbvEmp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        loadTableColumn();
        loadContentToTableView();
        tbvEmp.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                Employee selectedItem = (Employee) tbvEmp.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // perform some action with the selected item
                    System.out.println("Selected: " + selectedItem.toString());
                }
            }
        });
    }

    public void loadTableColumn() {
        TableColumn<Employee, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Employee, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Employee, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Employee, Date> joinDateCol = new TableColumn<>("Join Date");
        joinDateCol.setCellValueFactory(new PropertyValueFactory<>("joinDate"));

        TableColumn<Employee, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Employee, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<Employee, Integer> branchIdCol = new TableColumn<>("Branch ID");
        branchIdCol.setCellValueFactory(new PropertyValueFactory<>("branchId"));

        TableColumn updateCol = new TableColumn();
        updateCol.setCellFactory(clbck -> {
            Button btn = new Button("Update");
            btn.getStyleClass().add("update");
            btn.setOnAction(event -> {
                Alert alert = MessageBox.AlertBox("Update", "Chỉnh sửa ?", Alert.AlertType.CONFIRMATION);
                alert.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {

                    }
                });
            });
            TableCell tbc = new TableCell();
            tbc.setGraphic(btn);
            return tbc;
        });
        this.tbvEmp.getColumns().addAll(idCol, nameCol, usernameCol, joinDateCol,
                phoneCol, roleCol, branchIdCol, updateCol);
    }

    public void loadContentToTableView() {
        EmployeeService s = new EmployeeServiceImpl();
        List<Employee> employees = s.getEmployees(null);
        this.tbvEmp.getItems().clear();
        this.tbvEmp.setItems(FXCollections.observableList(employees));
    }
}
