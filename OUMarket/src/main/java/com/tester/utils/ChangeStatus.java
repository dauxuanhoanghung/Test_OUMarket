/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.utils;

import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author LENOVO
 */
public class ChangeStatus {

    public static void adjustButton(Button btn, String text, String className) {
        btn.getStyleClass().clear();
        btn.setText(text);
        btn.getStyleClass().add(className);
    }

    public static void toggleEnabledButton(Button... buttons) {
        for (Button button : buttons) {
            button.setDisable(!button.isDisable());
        }
    }

    public static void disabledButton(List<Button> buttons) {
        for (Button button : buttons) {
            button.setDisable(true);
        }
    }
    
    public static void disabledButton(Button button) { 
        button.setDisable(true);
    }

    public static void enableButton(List<Button> buttons) {
        for (Button button : buttons) {
            button.setDisable(false);
        }
    }
    
    public static void disableTextField(TextField... es) {
        for (TextField tf : es) {
            tf.setDisable(true);
        }
    }
    public static void enableTextField(TextField... es) {
        for (TextField tf : es) {
            tf.setDisable(false);
        }
    }
}
