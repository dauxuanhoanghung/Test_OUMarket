/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import com.tester.pojo.Product;
import com.tester.pojo.sub.SubProduct;
import com.tester.utils.ChangeStatus;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;

/**
 *
 * @author LENOVO
 */
public class EventDetailsController implements Initializable {

    @FXML
    private ToggleGroup Choice;
    @FXML
    private Label errorMessage;
    @FXML
    private Button addBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private RadioButton percentDiscountRadio;
    @FXML
    private RadioButton priceDiscountRadio;
    @FXML
    private TextField txtDiscountPrice;
    @FXML
    private Label txtNameProduct;
    @FXML
    private TextField txtPrice;

    private Product product;
    private SubProduct result;

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> this.txtDiscountPrice.requestFocus());
        setHandling();
        raiseErrorWhenInput();
        ChangeStatus.disable(txtPrice);

        txtDiscountPrice.setTextFormatter(new TextFormatter<>(new FloatStringConverter()));
    }

    public void showProduct(Product p) {
        txtNameProduct.setText(p.getName());
        txtPrice.setText(String.valueOf(p.getPrice()));
    }

    public void setHandling() {
        cancelBtn.setOnAction(this::handlerCancelButton);
        addBtn.setOnAction(this::handlerAddButton);

        Choice.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle) -> {
            txtDiscountPrice.setText("");
            errorMessage.setText("");
        });

    }

    public void handlerCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void handlerAddButton(ActionEvent event) {
        if (errorMessage.getText().isEmpty()) {
            
            SubProduct sp;
            if (priceDiscountRadio.isSelected())
                sp = new SubProduct(product.getId(), product.getName(),
                        Float.parseFloat(txtDiscountPrice.getText()));
            else
                sp = new SubProduct(product.getId(), product.getName(),
                        (100 - Float.parseFloat(txtDiscountPrice.getText())) / 100 
                                * Float.parseFloat(txtPrice.getText()));
            setResult(sp);
            Stage stage = (Stage) addBtn.getScene().getWindow();
            stage.close();
        }
    }

    public void raiseErrorWhenInput() {
        txtDiscountPrice.focusedProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double discount = Double.parseDouble(txtDiscountPrice.getText());
                double originalPrice = Double.parseDouble(txtPrice.getText());
                if (priceDiscountRadio.isSelected()) {
                    if (discount >= originalPrice) {
                        errorMessage.setText("Số tiền giảm giá cần bé hơn số tiền gốc!!!");
                    } else if (discount <= 0) {
                        errorMessage.setText("Số tiền không thể bé hơn 1");
                    } else {
                        errorMessage.setText("");
                    }
                }
                if (percentDiscountRadio.isSelected()) {
                    if (discount >= 100 || discount <= 0) {
                        errorMessage.setText("Giá trị nằm giữ 0 - 100");
                    } else {
                        errorMessage.setText("");
                    }
                }
            } catch (NumberFormatException e) {
                if (txtDiscountPrice.getText().length() > 0) {
                    errorMessage.setText("Please enter a valid number");
                }
            }
        });
    }

    public void setResult(SubProduct result) {
        this.result = result;
    }

    public SubProduct getResult() {
        return result;
    }
}
