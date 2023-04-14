/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import com.tester.pojo.Product;
import com.tester.service.ProductService;
import com.tester.service.impl.ProductServiceImpl;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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
    @FXML
    private Button registerBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        setHandling();
        this.txtProductId.setOnKeyPressed(event -> {
            ProductService ps = new ProductServiceImpl();
            Map<String, String> params = new HashMap<>();
            params.put("branch_id", String.valueOf(App.getCurrentEmployee().getBranchId()));
            params.put("id", String.valueOf(txtProductId.getText()));
            List<Product> products = ps.getProducts(params);
            System.out.println(products);
        });
    }

    public void setHandling() {
        this.addBtn.setOnAction(this::handleAddButton);
        this.registerBtn.setOnAction(this::handleRegisterButton);
    }

    public void handleRegisterButton(ActionEvent event) {
        Stage subStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerRegister.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ManageEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        Stage mainStage = (Stage) registerBtn.getScene().getWindow();
        // Set the controller for the sub stage
        CustomerRegisterController crController = new CustomerRegisterController();

        subStage.setScene(scene);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.initOwner(mainStage);
        subStage.showAndWait();
    }

    public void handleAddButton(ActionEvent event) {

    }
}
