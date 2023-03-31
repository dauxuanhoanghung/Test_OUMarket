/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import com.tester.constant.UIConstant;
import com.tester.utils.MessageBox;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author LENOVO
 */
public class ManageServiceController extends AbstractManageController {

    public void logout(ActionEvent event) {
        Alert alert = MessageBox.AlertBox("LOGOUT", "Are you sure to exit this session?", Alert.AlertType.CONFIRMATION);
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                App.setCurrentEmployee(null);
                try {
                    App.setSceneSize(UIConstant.loginWidth, UIConstant.loginHeight);
                    App.setRoot("Login");
                } catch (IOException ex) {
                    Logger.getLogger(ManageServiceController.class.getName()).log(Level.SEVERE,
                            "There's something error in ManageServiceController", ex);
                }
            }
        });
    }

    private void changeScene(String fxml) throws IOException {
        App.setRoot(fxml);
    }

    public void manageBranch(ActionEvent event) throws IOException {
        changeScene("ManageBranchMarket");
    }

    public void manageEmployee(ActionEvent event) throws IOException {
        changeScene("ManageEmployeePage");
    }

    public void manageProduct(ActionEvent event) throws IOException {
        changeScene("ManageProductPage");
    }
}
