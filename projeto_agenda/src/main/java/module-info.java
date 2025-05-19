module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive javafx.base;

    // json
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    opens com.example.ContactsController to javafx.fxml;
    opens com.example to javafx.fxml;
    opens com.example.warnings to javafx.fxml;
    opens com.example.ContactsTable to javafx.fxml, javafx.base, com.fasterxml.jackson.databind;

    exports com.example.ContactsController;
    exports com.example;
    exports com.example.ContactsTable;
    exports com.example.warnings to javafx.fxml;
}
