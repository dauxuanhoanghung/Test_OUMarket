/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author LENOVO
 */
public class LoginTest extends ApplicationTest {
//    private Scene scene;
//    private TextField usernameField;
//    private PasswordField passwordField;
//    private Button cancelButton;
//    private Button loginButton;
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        URL resourceUrl = getClass().getResource("/com/tester/oumarket/Login.fxml");
//        Parent root = FXMLLoader.load(resourceUrl);
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//        WaitForAsyncUtils.waitForFxEvents();
//
//        // Initialize UI elements
//        usernameField = lookup("#txtUsername").query();
//        passwordField = lookup("#txtPassword").query();
//        cancelButton = lookup("#closeBtn").query();
//        loginButton = lookup("#loginBtn").query();
//    }
//
//    @Test
//    public void testLogin() throws IOException {
//        // Enter the username and password
//        FxRobot robot = new FxRobot();
//        robot.clickOn(usernameField).write("admin");
//        robot.clickOn(passwordField).write("admin@123");
//
//        // Click the login button
//        robot.clickOn(loginButton);
//        
//        WaitForAsyncUtils.waitForFxEvents(10);
//        Pane pane = lookup("#pane").query();
//        assertNotNull(pane);
//    }

}
