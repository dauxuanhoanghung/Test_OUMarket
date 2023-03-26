/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import com.tester.pojo.Employee;
import com.tester.service.EmployeeService;
import com.tester.service.impl.EmployeeServiceImpl;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author LENOVO
 */
public class LoginController implements Initializable {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblUsernameError;
    @FXML private Label lblPasswordError;
    @FXML private Label lblLoginFailed;

    private EmployeeService employeeService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.txtUsername.positionCaret(0);
        Platform.runLater(() -> this.txtUsername.requestFocus());
    }

    public void closeApp(ActionEvent event) {
        App.exitApplication();
    }

    public void loginHandler() throws IOException {
        this.lblPasswordError.setVisible(false);
        this.lblUsernameError.setVisible(false);
        this.lblLoginFailed.setVisible(false);

        String password = this.txtPassword.getText();
        String username = this.txtUsername.getText();

        if (!password.isBlank() && !username.isBlank()) {
            this.employeeService = new EmployeeServiceImpl();
            Employee emp = employeeService.authencateEmployee(username, password);
            if (emp != null) {
                App.setCurrentEmployee(emp);
                App.setRoot("ManagerServicePage");
                App.setSceneSize(1280, 820);
            } else {
                this.lblLoginFailed.setVisible(true);
                this.txtUsername.positionCaret(username.length());
                this.txtUsername.requestFocus();
            }
        } else {
            if (this.txtPassword.getText().isBlank()) {
                this.lblPasswordError.setVisible(true);
                this.txtPassword.requestFocus();
            }
            if (this.txtUsername.getText().isBlank()) {
                this.lblUsernameError.setVisible(true);
                this.txtUsername.requestFocus();
            }
        }
    }
}
