/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import com.tester.constant.UIConstant;
import com.tester.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 *
 * @author LENOVO
 */
public abstract class AbstractManageController implements Initializable {

    @FXML private Label lblNameAdmin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.lblNameAdmin.setText("Welcome " + App.getCurrentEmployee().getName());
    }

    public void returnMenuService() throws IOException {
        App.setRoot("ManageServicePage");
    }
    
    public void logout(ActionEvent event) {
        Alert alert = MessageBox.AlertBox("LOGOUT", "Are you sure to exit this session?", Alert.AlertType.CONFIRMATION);
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                App.setCurrentEmployee(null);
                try {
                    App.setSceneSize(UIConstant.loginWidth, UIConstant.loginHeight);
                    App.setRoot("Login");
                } catch (IOException ex) {
                    Logger.getLogger(AbstractManageController.class.getName()).log(Level.SEVERE,
                            "There's something error in ManageServiceController", ex);
                }
            }
        });
    }
}
