package com.example.ContactsController;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartScreenController {

    @FXML
    private Button GoToAgenda;

    @FXML
    private Button TaskManager;

    @FXML
    void StartAgenda(ActionEvent event) {
        try {
            System.out.println("Abrindo tela inicial!");
            Parent secondView = FXMLLoader
                    .load(getClass().getResource("/com/example/HomeScreen.fxml"));
            Scene secondScene = new Scene((secondView), 1280, 800);

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

    @FXML
    void StartTaskManager(ActionEvent event) {
        try {
            System.out.println("Abrindo tela de tarefas!");
            Parent secondView = FXMLLoader
                    .load(getClass().getResource("/com/example/HomeScreen(Tasks).fxml"));
            Scene secondScene = new Scene(secondView);

            // Para retornar a janela para a tela inicial
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(secondScene);
            currentStage.show();

        } catch (IOException e) {
            System.out.println("\033\143");
            e.printStackTrace();
            System.out.println("Ocorreu um erro ao abrir a tela de tarefas");
        }
    }

}
