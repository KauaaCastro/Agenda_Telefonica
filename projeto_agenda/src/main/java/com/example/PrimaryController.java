package com.example;

import java.io.IOException;

import com.example.ContactsTable.Contacts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//Controlador da tela inicial, controller principal

public class PrimaryController {

    @FXML
    private Button pro_addContact;

    @FXML
    private Button pro_addTarefa;

    @FXML
    private Button pro_editTarefa;

    @FXML
    private Button pro_editcontact;

    @FXML
    private Button pro_removeContact;

    @FXML
    private Button pro_removeTarefa;

    @FXML
    private TextField pro_search;

    @FXML
    private TextField pro_searchTarefa;

    @FXML
    private TableView<?> table2;

    @FXML
    private TableColumn<?, ?> table2Data;

    @FXML
    private TableColumn<?, ?> table2Descrição;

    @FXML
    private TableColumn<?, ?> table2Endereço;

    @FXML
    private TableColumn<?, ?> table2Local;

    @FXML
    private TableColumn<?, ?> table2Pessoas;

    @FXML
    private TableColumn<?, ?> table2Pessoas_2;

    @FXML
    private TableColumn<?, ?> table2Pessoas_3;

    @FXML
    private TableColumn<?, ?> table2Pessoas_4;

    @FXML
    private TableColumn<?, ?> table2Pessoas_5;

    @FXML
    private TableColumn<?, ?> table2Pessoas_6;

    @FXML
    private TableColumn<?, ?> table2Pessoas_7;

    @FXML
    private TableColumn<?, ?> table2Tarefa;

    @FXML
    private TableView<Contacts> table_1;

    @FXML
    private TableColumn<Contacts, String> table_1Apelido;

    @FXML
    private TableColumn<Contacts, String> table_1Name;

    @FXML
    private TableColumn<Contacts, Integer> table_1NumeroTelefone;

    // Adicionar contatos: Abrir nova tela para adição de contatos.
    @FXML
    void AddContactsScreen(ActionEvent event) throws InterruptedException, IOException {
        try {
            System.out.println("Abrindo tela de adição de contatos!");
            Parent secondView = FXMLLoader.load(getClass().getResource("/com/example/AddContacts.fxml"));
            Scene secondScene = new Scene(secondView);

            // Para substituir a janela atual
            // Stage currentStage = (Stage) ((Node)
            // event.getSource()).getScene().getWindow();

            Stage newStage = new Stage(); // Para abrir uma nova janela
            newStage.setScene((secondScene));
            newStage.show();

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao tentar abrir a nova janela");
            System.out.println("Código do erro: ");
            System.out.println();
            e.getStackTrace();
        }
    }
}
