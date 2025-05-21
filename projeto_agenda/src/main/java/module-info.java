module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive javafx.base;

    // json
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires java.desktop;

    opens com.example.ContactsController to javafx.fxml;
    opens com.example to javafx.fxml;
    opens com.example.warnings to javafx.fxml;
    opens com.example.ContactsTable to javafx.fxml, javafx.base, com.fasterxml.jackson.databind;
    opens com.example.TaskController to javafx.fxml, javafx.base, com.fasterxml.jackson.databind;
    opens com.example.TaskTable;

    exports com.example.ContactsController;
    exports com.example;
    exports com.example.ContactsTable;
    exports com.example.warnings to javafx.fxml;
    exports com.example.TaskTable;
}
