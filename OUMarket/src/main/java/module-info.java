module com.tester.oumarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires cloudinary.core;
    requires org.apache.poi.ooxml;
    requires org.apache.poi.poi;
    opens com.tester.oumarket to javafx.fxml;
    exports com.tester.oumarket;
    opens com.tester.pojo to javafx.fxml;
    exports com.tester.pojo;
    opens com.tester.pojo.sub to javafx.fxml;
    exports com.tester.pojo.sub;
    exports com.tester.utils;
    exports com.tester.constant;
}
