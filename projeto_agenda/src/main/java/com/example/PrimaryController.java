package com.example;

import java.io.IOException;
import com.example.ContactsTable.AppState;
import com.example.ContactsTable.ContactService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
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
    private TableView<ContactService> table_1;

    @FXML
    private TableColumn<ContactService, String> table_1Apelido;

    @FXML
    private TableColumn<ContactService, String> table_1Name;

    @FXML
    private TableColumn<ContactService, Integer> table_1NumeroTelefone;

    @FXML
    private TableColumn<ContactService, String> table1_birthdayDate;

    @FXML
    private TableColumn<ContactService, String> table1_gender;

    // Adicionar contatos: Abrir nova tela para adição de contatos.
    @FXML
    void AddContactsScreen(ActionEvent event) throws InterruptedException, IOException {
        try {
            // Abre o POP UP como modal, ou seja, abre bloqueando a janela anterior
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/AddContacts.fxml"));
            Parent root = loader.load();

            Stage alertStage = new Stage();
            alertStage.setTitle("Adicionar contatos");
            alertStage.setScene(new Scene(root));

            alertStage.initModality(Modality.APPLICATION_MODAL);

            alertStage.showAndWait();

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao tentar abrir a nova janela");
            System.out.println("Código do erro: ");
            System.out.println();
            e.getStackTrace();
        }
    }

    // Atualizar de maneira dinamica a lista de contatos
    @FXML
    public void initialize() {
        table_1Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table1_birthdayDate.setCellValueFactory(new PropertyValueFactory<>("dateBirthday"));
        table1_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        table_1NumeroTelefone.setCellValueFactory(new PropertyValueFactory<>("tellNumber"));
        table_1Apelido.setCellValueFactory(new PropertyValueFactory<>("nickName"));

        table_1.setItems(AppState.getContacts());
    }

    // Tela de edição dos contatos
    @FXML
    void EditContacts(ActionEvent event) {

    }
}