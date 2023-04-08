/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.utils;

import java.util.List;
import javafx.scene.control.Control;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;

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

    public static void disable(List<Button> buttons) {
        for (Button button : buttons) {
            button.setDisable(true);
        }
    }    
    public static void disable(Control... es) {
        for (Control c : es) {
            c.setDisable(true);
        }
    }
    public static void enable(List<Button> buttons) {
        for (Button button : buttons) {
            button.setDisable(false);
        }
    }
    public static void enable(Control... es) {
        for (Control c : es) {
            c.setDisable(false);
        }
    }
    public static void clearText(TextInputControl... es) {
        for (TextInputControl t : es) {
            t.setText("");
        }
    }
    
    public static void invisible(Control... es) {
        for (Control c : es) {
            c.setVisible(false);
        }
    }
    public static void visible(Control... es) {
        for (Control c : es) {
            c.setVisible(true);
        }
    }
}
