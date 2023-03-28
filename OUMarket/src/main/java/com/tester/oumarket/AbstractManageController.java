/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
}
