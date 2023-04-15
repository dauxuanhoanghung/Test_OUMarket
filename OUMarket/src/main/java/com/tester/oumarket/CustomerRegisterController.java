/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import com.tester.pojo.Customer;
import com.tester.service.CustomerService;
import com.tester.service.impl.CustomerServiceImpl;
import com.tester.utils.CheckUtils;
import com.tester.utils.MessageBox;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;

/**
 *
 * @author LENOVO
 */
public class CustomerRegisterController implements Initializable {

    @FXML
    private Button cancelBtn;
    @FXML
    private DatePicker dpBirthday;
    @FXML
    private Label errorNameMessage;
    @FXML
    private Label errorPhoneMessage;
    @FXML
    private Button registerBtn;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPhone;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setHandling();
        handleDatePicker(dpBirthday);
        Platform.runLater(() -> this.txtName.requestFocus());
    }

    public void handleDatePicker(DatePicker datePicker) {
        datePicker.setShowWeekNumbers(true);
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter
                    = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        datePicker.setConverter(converter);
        datePicker.setPromptText("dd/MM/yyyy");
        datePicker.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(newText, formatter);
                datePicker.setValue(date);
            } catch (DateTimeParseException e) {
            }
        });
    }

    public void setHandling() {
        this.registerBtn.setOnAction(this::handleRegisterButton);
        this.cancelBtn.setOnAction(this::handleCancelButton);
    }

    public void handleRegisterButton(ActionEvent event) {
        String name = txtName.getText();
        String phone = txtPhone.getText();
        LocalDate birthday = dpBirthday.getValue();

        int nameCheck = CheckUtils.isValidName(name);
        int phoneCheck = CheckUtils.isValidPhoneNumber(phone);

        if (nameCheck == 1 && phoneCheck == 1) {
            errorNameMessage.setText("");
            errorPhoneMessage.setText("");
            Customer customer = new Customer(name, phone, birthday);
            CustomerService cs = new CustomerServiceImpl();
            if (cs.addCustomer(customer) > 0) {
                MessageBox.AlertBox("SUCCESSFUL", "Đăng ký khách hàng thành viên thành công!!!",
                        Alert.AlertType.CONFIRMATION).show();
                Stage stage = (Stage) registerBtn.getScene().getWindow();
                stage.close();
            }
            else
                MessageBox.AlertBox("FAILED", "Hệ thống có lỗi!!!",
                        Alert.AlertType.WARNING).show();
        } else {
            if (nameCheck != 1) {
                switch (nameCheck) {
                    case 0:
                        errorNameMessage.setText("Không thể để trống tên khách hàng");
                        break;
                    case -1:
                        errorNameMessage.setText("Tên có kí tự lạ, không hợp lệ");
                        break;
                    case -2:
                        errorNameMessage.setText("Tên dài hơn 50");
                        break;
                }
            }

            if (phoneCheck != 1) {
                switch (phoneCheck) {
                    case 0:
                        errorPhoneMessage.setText("Không thể để trống sđt");
                        break;
                    case -1:
                        errorPhoneMessage.setText("Độ dài không bằng 10");
                        break;
                    case -2:
                        errorPhoneMessage.setText("SĐT chỉ có số");
                        break;
                    case -3:
                        errorPhoneMessage.setText("Số điện thoại cần bắt đầu bằng số 0");
                        break;
                }
            }
        }
    }

    public void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
