module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive javafx.base;

    opens com.example to javafx.fxml;
    opens com.example.warnings to javafx.fxml;
    opens com.example.ContactsTable to javafx.fxml, javafx.base;

    exports com.example;
    exports com.example.ContactsTable;
    exports com.example.warnings to javafx.fxml;
}
