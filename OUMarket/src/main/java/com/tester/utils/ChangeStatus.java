/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.utils;

import javafx.scene.control.Button;

/**
 *
 * @author LENOVO
 */
public class ChangeStatus {
    public static void  adjustButton(Button btn, String text, String className) {
        btn.getStyleClass().clear();
        btn.setText(text);
        btn.getStyleClass().add(className);
    }
}
