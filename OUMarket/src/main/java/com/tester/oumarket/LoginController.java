/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import com.tester.constant.UIConstant;
import com.tester.pojo.Employee;
import com.tester.service.EmployeeService;
import com.tester.service.impl.EmployeeServiceImpl;
import com.tester.utils.CheckUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 *
 * @author LENOVO
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblUsernameError;
    @FXML
    private Label lblPasswordError;
    @FXML
    private Label lblLoginFailed;

    private EmployeeService employeeService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.txtUsername.positionCaret(0);
        this.txtUsername.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                this.txtPassword.requestFocus();
            }
        });
        this.txtPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    loginHandler();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, 
                            "There's something error in LoginController!!!", ex);
                }
            }
        });
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

        if (CheckUtils.isNotNullAndBlankText(password, username)) {
            this.employeeService = new EmployeeServiceImpl();
            Employee emp = employeeService.authencateEmployee(username, password);
            if (emp != null) { 
                String root = emp.getRole().equals(Employee.ADMIN) ? "ManageServicePage" : "EmployeeServicePage";
                App.setCurrentEmployee(emp);            
                App.setSceneSize(UIConstant.otherWidth, UIConstant.otherHeight);
                App.setRoot(root);
            } else {
                this.lblLoginFailed.setVisible(true);
                this.txtUsername.positionCaret(username.length());
                this.txtUsername.requestFocus();
            }
        } else {
            if (!CheckUtils.isNotNullAndBlankText(this.txtPassword)) {
                this.lblPasswordError.setVisible(true);
                this.txtPassword.requestFocus();
            }
            if (!CheckUtils.isNotNullAndBlankText(this.txtUsername)) {
                this.lblUsernameError.setVisible(true);
                this.txtUsername.requestFocus();
            }
        }
    }
}
