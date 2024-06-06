module com.cms {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.cms to javafx.fxml;
    opens com.cms.controller to javafx.fxml;

    exports com.cms;
    exports com.cms.controller;
}
