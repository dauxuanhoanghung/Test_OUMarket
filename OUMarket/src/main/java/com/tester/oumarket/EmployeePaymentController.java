/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import com.tester.pojo.Customer;
import com.tester.pojo.Product;
import com.tester.service.CustomerService;
import com.tester.service.ProductService;
import com.tester.service.impl.CustomerServiceImpl;
import com.tester.service.impl.ProductServiceImpl;
import com.tester.utils.ChangeStatus;
import com.tester.utils.CheckUtils;
import com.tester.utils.MessageBox;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author LENOVO
 */
public class EmployeePaymentController extends AbstractManageController {

    @FXML
    private Button addBtn;
    @FXML
    private Label lblCustomerBirthday;
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblCustomerPhone;
    @FXML
    private Label lblTotal;
    @FXML
    private TableView tbvOrderDetail;
    @FXML
    private TableView tbvSearch;
    @FXML
    private TextField txtProductId;
    @FXML
    private TextField txtCustomerPhone;
    @FXML
    private Button registerBtn;
    @FXML
    private Button payBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        loadOrderDetailTableColumn();
        loadTableSearchColumn();
        setHandling();
        this.txtProductId.setOnKeyPressed(event -> {
            ProductService ps = new ProductServiceImpl();
            Map<String, String> params = new HashMap<>();
            params.put("branch_id", String.valueOf(App.getCurrentEmployee().getBranchId()));
            params.put("id", String.valueOf(txtProductId.getText()));
            List<Product> products = ps.getProducts(params);
            tbvSearch.getItems().clear();
            tbvSearch.getItems().addAll(products);
        });
    }

    public void setHandling() {
        this.addBtn.setOnAction(this::handleAddButton);
        this.registerBtn.setOnAction(this::handleRegisterButton);
        this.payBtn.setOnAction(this::handlePaymentButton);
        handleSearchCustomer();
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
        Product product = (Product) tbvSearch.getSelectionModel().getSelectedItem();
        if (true) {
            tbvOrderDetail.getItems().add(product);
            ChangeStatus.clearText(txtProductId);
        }
    }

    public void handlePaymentButton(ActionEvent event) {

    }

    private void loadOrderDetailTableColumn() {
        TableColumn idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(120);

        TableColumn nameCol = new TableColumn<>("Tên SP");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(300);

        TableColumn priceCol = new TableColumn<>("Giá");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(100);

        TableColumn<Product, Float> quantityCol = new TableColumn<>("Số lượng");
        quantityCol.setCellFactory(column -> new TableCell<Product, Float>() {
            private final TextField txtField = new TextField();

            {
                txtField.setTextFormatter(new TextFormatter<>(new FloatStringConverter()));
                txtField.setText("1");

                txtField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue) {
                        String text = txtField.getText();
                        // Check for errors in the text and show a message if necessary
                        if (text.isEmpty() || !text.matches("\\d+")) {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid number");
                            alert.showAndWait();
                        }
                    } else {
                        MessageBox.AlertBox("Error", "Không thể để trống", Alert.AlertType.WARNING).show();
                        txtField.setText("1");
                        txtField.requestFocus();
                    }
                });
            }

            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    txtField.setText(item.toString());
                    setGraphic(txtField);
                }
            }
        });

        this.tbvOrderDetail.getColumns().addAll(idCol, nameCol, priceCol, quantityCol);
    }

    private void loadTableSearchColumn() {
        TableColumn idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(120);

        TableColumn nameCol = new TableColumn<>("Tên SP");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(300);

        TableColumn priceCol = new TableColumn<>("Giá");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(100);

        this.tbvSearch.getColumns().addAll(idCol, nameCol, priceCol);
    }

    public void handleSearchCustomer() {
        txtCustomerPhone.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 10 || !newValue.matches("\\d*")) {
                txtCustomerPhone.setText(oldValue);
            }
        });

        txtCustomerPhone.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String phone = txtCustomerPhone.getText();
                int phoneCheck = CheckUtils.isValidPhoneNumber(phone);
                switch (phoneCheck) {
                    case 1:
                        CustomerService cs = new CustomerServiceImpl();
                        Customer customer = cs.getCustomerByPhone(phone);
                        lblCustomerName.setText(customer.getName());
                        lblCustomerPhone.setText(customer.getPhone());
                        lblCustomerBirthday.setText(customer.getBirthday().toString());
                        break;
                    case 0:
                        MessageBox.AlertBox("Error", "Không thể để rỗng", Alert.AlertType.WARNING).showAndWait();
                        break;
                    case -1:
                        MessageBox.AlertBox("Error", "Không phải số điện thoại", Alert.AlertType.WARNING).showAndWait();
                        break;
                    case -2:
                        MessageBox.AlertBox("Error", "SĐT chỉ chứa số", Alert.AlertType.WARNING).showAndWait();
                        break;
                    case -3:
                        MessageBox.AlertBox("Error", "SĐT phải bắt đầu = số 0", Alert.AlertType.WARNING).showAndWait();
                        break;
                }
            }
        });
    }
}
