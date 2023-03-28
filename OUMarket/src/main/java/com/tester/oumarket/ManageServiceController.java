/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

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
        Alert alert = MessageBox.AlertBox("Delete", "R U sure to delete", Alert.AlertType.CONFIRMATION);
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                App.setCurrentEmployee(null);
                try {
                    App.setRoot("Login");
                    App.setSceneSize(540, 320);
                } catch (IOException ex) {
                    Logger.getLogger(ManageServiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void changeScene(String fxml) throws IOException {
        App.setRoot(fxml);
        App.setSceneSize(1280, 820);
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
