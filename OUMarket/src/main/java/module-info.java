module com.tester.oumarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens com.tester.oumarket to javafx.fxml;
    exports com.tester.oumarket;
}
