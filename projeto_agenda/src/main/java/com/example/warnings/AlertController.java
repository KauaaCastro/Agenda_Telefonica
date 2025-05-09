package com.example.warnings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

//Controlador dos Alertas
public class AlertController {

    @FXML
    private Button Alert_Ok;

    @FXML
    private Label Alert_Menssage;

    @FXML
    void Confirmation(ActionEvent event) {
        Stage stage = (Stage) Alert_Ok.getScene().getWindow();
        stage.close();
    }

}
