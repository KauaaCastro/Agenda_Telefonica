module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    opens com.example to javafx.fxml;
    opens com.example.Alerts to javafx.fxml;
    opens com.example.warnings to javafx.fxml;
    opens com.example.ContactsTable to javafx.fxml, javafx.base;

    exports com.example.warnings to javafx.fxml;
    exports com.example;
}
