module com.tester.oumarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires cloudinary.core;
    opens com.tester.oumarket to javafx.fxml;
    exports com.tester.oumarket;
    opens com.tester.pojo to javafx.fxml;
    exports com.tester.pojo;
    exports com.tester.utils;
    exports com.tester.constant;
}
