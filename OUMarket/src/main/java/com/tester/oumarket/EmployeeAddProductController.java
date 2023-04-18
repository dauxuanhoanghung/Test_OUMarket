package com.tester.oumarket;

import com.tester.pojo.Event;
import com.tester.pojo.EventProduct;
import com.tester.pojo.Product;
import com.tester.pojo.sub.CartItem;
import com.tester.service.EventService;
import com.tester.service.impl.EventServiceImpl;
import com.tester.utils.ChangeStatus;
import com.tester.utils.CheckUtils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 *
 * @author LENOVO
 */
public class EmployeeAddProductController implements Initializable {

    @FXML
    private Button addBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Label errorQuantityMessage;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNumber;
    @FXML
    private TextField txtPrice;
    private Product product;
    private CartItem cartItem;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setHandling();
        ChangeStatus.disable(txtId, txtPrice);
    }

    private void setHandling() {
        this.cancelBtn.setOnAction(this::handleCancelButton);
        this.addBtn.setOnAction(this::handleAddButton);
        txtNumber.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                addToCart();
            }
        });
    }

    public void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void handleAddButton(ActionEvent event) {
        addToCart();
    }

    private void addToCart() {
        String id = txtId.getText();
        Float price = Float.valueOf(txtPrice.getText());

        int quantityCheck = CheckUtils.isValidQuantity(product, txtNumber.getText());

        if (quantityCheck == 1) {
            errorQuantityMessage.setText("");
            Float quantity = Float.valueOf(txtNumber.getText());
            CartItem item = new CartItem(id, product.getName(), price, product.getPrice(), quantity);
            setCartItem(item);
            Stage stage = (Stage) addBtn.getScene().getWindow();
            stage.close();
        } else {
            errorQuantityMessage.setText("Số lượng nhập không đúng");
            txtNumber.requestFocus();
        }
    }

    public void updateUI(Product p) {
        txtId.setText(p.getId());
        EventService es = new EventServiceImpl();
        Event e = es.getCurrentEvent();
        if (e == null) {
            txtPrice.setText(p.getPrice() + "");
            return;
        }

        EventProduct ep = es.getEventProduct(e, product);
        if (ep == null) {
            txtPrice.setText(p.getPrice() + "");
        } else {
            txtPrice.setText(ep.getDiscountPrice() + "");
        }

        txtNumber.setText("1");
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return the cartItem
     */
    public CartItem getCartItem() {
        return cartItem;
    }

    /**
     * @param cartItem the cartItem to set
     */
    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }
}
