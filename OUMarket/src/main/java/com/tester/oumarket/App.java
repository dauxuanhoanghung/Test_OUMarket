package com.tester.oumarket;

import com.tester.pojo.Employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.application.Platform;
import javafx.stage.Window;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Employee currentEmployee;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Login"));
        stage.setTitle("OU MARKET by Group 4 - DH20IT03");
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void setSceneSize(double width, double height) {
        Window window = scene.getWindow();
        window.setWidth(width);
        window.setHeight(height);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void exitApplication() {
        Platform.exit();
    }

    public static void main(String[] args) {
        launch();
        setCurrentEmployee(null);
    }

    /**
     * @return the currentEmployee
     */
    public static Employee getCurrentEmployee() {
        return currentEmployee;
    }

    /**
     * @param aCurrentEmployee the currentEmployee to set
     */
    public static void setCurrentEmployee(Employee aCurrentEmployee) {
        currentEmployee = aCurrentEmployee;
    }

}
