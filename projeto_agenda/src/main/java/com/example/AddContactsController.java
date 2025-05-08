package com.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddContactsController {

    @FXML
    private Button HomeScreen;

    @FXML
    private Button ShowInfos;

    @FXML
    private TextField pro_NickName;

    @FXML
    private TextField pro_Relation;

    @FXML
    private TextField pro_birthday;

    @FXML
    private TextField pro_endress;

    @FXML
    private TextField pro_extra;

    @FXML
    private TextField pro_gender;

    @FXML
    private TextField pro_name;

    @FXML
    private TextField pro_numberTell;

    @FXML
    private TextField pro_work;

    @FXML
    private Button saveContact;

    // Retorna para a tela inicial
    @FXML
    void ReturnHomeScreen(ActionEvent event) {
        try {
            System.out.println("Abrindo tela inicial!");
            Parent secondView = FXMLLoader.load(getClass().getResource("/com/example/HomeScreen.fxml"));
            Scene secondScene = new Scene(secondView);

            // Para retornar a janela para a tela inicial
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(secondScene);
            currentStage.show();

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao tentar abrir a nova janela");
            System.out.println("Código do erro: ");
            System.out.println();
            e.getStackTrace();
        }
    }
}