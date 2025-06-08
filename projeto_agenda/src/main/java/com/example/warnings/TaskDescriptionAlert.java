package com.example.warnings;

import com.example.JavaStyle.ManualStyle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TaskDescriptionAlert {

    @FXML
    private Button description_Return;

    @FXML
    private Text text_Description;

    private String finalDescription;

    @FXML
    void initialize() {
        ManualStyle.TextStyle(text_Description);
    }

    @FXML
    void descriptionReturn(ActionEvent event) {
        Stage stage = (Stage) description_Return.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void getDescription(String toDescription) {
        this.finalDescription = toDescription;
        text_Description.setText(finalDescription);
    }
}
