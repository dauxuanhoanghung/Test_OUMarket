/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author LENOVO
 */
public class EmployeePaymentController extends AbstractManageController {

    @FXML
    private Button addBtn;
    @FXML
    private TableView tbvOrderDetail;
    @FXML
    private TextField txtProductId;
    @FXML
    private TextField txtProductName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
//        this.addBtn.setOnAction(eh);
    }
    
    
}
