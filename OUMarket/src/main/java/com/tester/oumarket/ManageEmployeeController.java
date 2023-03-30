/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author LENOVO
 */
public class ManageEmployeeController extends AbstractManageController {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        loadTableColumn();

    }

    public void loadTableColumn() {
        TableColumn idCol = new TableColumn("Mã nhân viên");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn nameCol = new TableColumn("Tên nhân viên");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        
        TableColumn joinCol = new TableColumn("Ngày bắt đầu");
        joinCol.setCellValueFactory(new PropertyValueFactory("join_date"));
        
        TableColumn phoneCol = new TableColumn("Số điện thoại");
        phoneCol.setCellValueFactory(new PropertyValueFactory("phone"));
        
        TableColumn roleCol = new TableColumn("Vai trò");
        roleCol.setCellValueFactory(new PropertyValueFactory("role"));
        
        TableColumn branchCol = new TableColumn("Chi nhánh");
        branchCol.setCellValueFactory(new PropertyValueFactory("branch_id"));
        
    }

    public void loadTableContent() {

    }
}
