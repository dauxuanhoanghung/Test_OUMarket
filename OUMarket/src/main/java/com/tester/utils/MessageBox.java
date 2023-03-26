/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.utils;

import javafx.scene.control.Alert;

/**
 *
 * @author LENOVO
 */
public class MessageBox {
    public static Alert AlertBox(String title, String content, Alert.AlertType type) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setContentText(content);
        return a;
    }
}