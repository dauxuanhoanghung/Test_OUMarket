/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import com.tester.pojo.Customer;
import com.tester.pojo.Order;
import com.tester.pojo.Product;
import com.tester.pojo.sub.CartItem;
import com.tester.service.CustomerService;
import com.tester.service.OrderService;
import com.tester.service.ProductService;
import com.tester.service.impl.CustomerServiceImpl;
import com.tester.service.impl.OrderServiceImpl;
import com.tester.service.impl.ProductServiceImpl;
import com.tester.utils.ChangeStatus;
import com.tester.utils.CheckUtils;
import com.tester.utils.MessageBox;
import com.tester.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private Label lblExchangeMoney;
    @FXML
    private Label errorMoneyMessage;
    @FXML
    private TableView tbvOrderDetail;
    @FXML
    private TableView tbvSearch;
    @FXML
    private TextField txtProductId;
    @FXML
    private TextField txtCustomerPhone;
    @FXML
    private TextField txtMoney;
    @FXML
    private Button registerBtn;
    @FXML
    private Button payBtn;
    @FXML
    private Button delCusBtn;

    private Customer customer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        loadOrderDetailTableColumn();
        loadTableSearchColumn();
        setHandling();
        ChangeStatus.invisible(errorMoneyMessage);
    }

    public void setHandling() {
        this.addBtn.setOnAction(this::handleAddButton);
        this.registerBtn.setOnAction(this::handleRegisterButton);
        this.payBtn.setOnAction(this::handlePaymentButton);
        this.delCusBtn.setOnAction(this::handleDeleteCustomerButton);
        this.txtMoney.setOnKeyPressed(this::handleMoneyCustomer);
        handleSearchCustomer();
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
        Stage subStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeAddProduct.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ManageEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        Stage mainStage = (Stage) addBtn.getScene().getWindow();
        // Set the controller for the sub stage
        EmployeeAddProductController eaController = loader.getController();
        eaController.setProduct(product);
        eaController.updateUI(product);

        subStage.setScene(scene);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.initOwner(mainStage);
        subStage.showAndWait();
        CartItem cartItem = eaController.getCartItem();
        if (cartItem != null) {
            List<CartItem> cart = tbvOrderDetail.getItems();
            Optional<CartItem> existingItem = cart.stream()
                    .filter(item -> ((CartItem) item).isSameId(cartItem)).findFirst();
            if (existingItem.isPresent()) {
                // Update the existing CartItem if necessary
                CartItem itemToUpdate = existingItem.get();
                itemToUpdate.setQuantity(itemToUpdate.getQuantity() + cartItem.getQuantity());
                tbvOrderDetail.refresh();
                // Call the update method if needed
            } else {
                cart.add(cartItem);
            }
            lblTotal.setText(Utils.calculate(cart) + "");
        }
        ChangeStatus.clearText(txtProductId);
        tbvSearch.getItems().clear();
    }

    public void handlePaymentButton(ActionEvent event) {
        OrderService os = new OrderServiceImpl();
        ArrayList<CartItem> cartItems = new ArrayList<>(tbvOrderDetail.getItems());
        Order order = new Order(Float.parseFloat(lblTotal.getText()),
                App.getCurrentEmployee().getId(), customer != null ? customer.getId() : null);
        if (os.addOrder(order, cartItems) > 0) {

            ChangeStatus.clearText(lblCustomerPhone, lblCustomerName, lblCustomerBirthday);
            ChangeStatus.clearText(txtCustomerPhone, txtMoney);
            lblTotal.setText("0");
            lblExchangeMoney.setText("0");
            tbvOrderDetail.getItems().clear();
            MessageBox.AlertBox("Success", "Thanh toán thành công", Alert.AlertType.CONFIRMATION).show();
        } else {
            MessageBox.AlertBox("FAILED", "Có lỗi", Alert.AlertType.ERROR).show();
        }

    }

    public void handleDeleteCustomerButton(ActionEvent event) {
        if (this.customer == null) {
            return;
        }
        ChangeStatus.clearText(lblCustomerBirthday, lblCustomerName, lblCustomerPhone);
        this.customer = null;
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

        TableColumn quantityCol = new TableColumn<>("SL");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityCol.setPrefWidth(100);

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
                        Customer cus = cs.getCustomerByPhone(phone);
                        if (cus == null) {
                            break;
                        }
                        lblCustomerName.setText(cus.getName());
                        lblCustomerPhone.setText(cus.getPhone());
                        lblCustomerBirthday.setText(cus.getBirthday().toString());
                        this.customer = cus;
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

    public void handleMoneyCustomer(KeyEvent event) {
        String moneyText = txtMoney.getText();
        int priceCheck = CheckUtils.isValidPrice(moneyText);
        if (priceCheck == 1) {
            ChangeStatus.invisible(errorMoneyMessage);
            Float total = Float.valueOf(lblTotal.getText());
            Float money = Float.valueOf(moneyText);
            Float exchange = money - total;
            if (exchange < 0) {
                errorMoneyMessage.setText("Chưa đủ tiền");
                ChangeStatus.visible(errorMoneyMessage);
                return;
            }
            lblExchangeMoney.setText(exchange + "");
        } else {
            ChangeStatus.visible(errorMoneyMessage);
            switch (priceCheck) {
                case 0:
                    errorMoneyMessage.setText("Chưa nhập tiền");
                    break;
                case -1:
                    errorMoneyMessage.setText("Nhập ko phải tiền");
                    break;
                case -2:
                    errorMoneyMessage.setText("Nhập số âm !!!");
                    break;
            }
        }
    }
}
