package com.example;

import java.io.IOException;
import com.example.ContactsTable.AppState;
import com.example.ContactsTable.ContactService;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//Controlador da tela inicial, controller principal

public class PrimaryController {

    @FXML
    private VBox dashBoard;

    @FXML
    private VBox mainContent;

    @FXML
    private TextField pro_SearchContacts;

    @FXML
    private Button pro_addContact;

    @FXML
    private Button pro_editContact;

    @FXML
    private Button pro_removeContacts;

    @FXML
    private Button pro_visualContacts;

    @FXML
    private Button returnHomeScreen;

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

    @FXML
    private TableColumn<ContactService, String> table1_email;

    @FXML
    private TableColumn<ContactService, String> table1_endress;

    @FXML
    private TableColumn<ContactService, String> table1_relation;

    @FXML
    private TableColumn<ContactService, String> table1_work;

    // Atualizar de maneira dinamica a lista de contatos
    @FXML
    public void initialize() {

        // Atualizar os campos obrigatórios
        table_1Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table1_birthdayDate.setCellValueFactory(new PropertyValueFactory<>("dateBirthday"));
        table1_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        table_1NumeroTelefone.setCellValueFactory(new PropertyValueFactory<>("tellNumber"));
        table_1Apelido.setCellValueFactory(new PropertyValueFactory<>("nickName"));

        // Atualizar os campos opcionais
        table1_email.setCellValueFactory(new PropertyValueFactory<>("emailContact"));
        table1_endress.setCellValueFactory(new PropertyValueFactory<>("endressContact"));
        table1_work.setCellValueFactory(new PropertyValueFactory<>("workContact"));
        table1_relation.setCellValueFactory(new PropertyValueFactory<>("relationContact"));

        table_1.setItems(AppState.getContacts());
    }

    // Pesquisar contato
    @FXML
    void SearchContacs(ActionEvent event) {

    }

    // Adicionar contatos
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

    // Métodos de telas
    // Return home screen
    @FXML
    void goToHomeScreen(ActionEvent event) {
        try {
            System.out.println("\033\143");
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

    // Método de remoção dos contatos
    @FXML
    void RemoveContacts(ActionEvent event) {
        System.out.println("\033\143");

        loadRemoveContacts();
    }

    // Tela de edição dos contatos
    @FXML
    void EditContacts(ActionEvent event) throws IOException {

        // Abre o POP UP como modal, ou seja, abre bloqueando a janela anterior
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/EditionScreen.fxml"));
        Parent root = loader.load();

        Stage alertStage = new Stage();
        alertStage.setTitle("Adicionar contatos");
        alertStage.setScene(new Scene(root));

        alertStage.initModality(Modality.APPLICATION_MODAL);

        alertStage.showAndWait();

    }

    // Funcionamento da Dash Board
    public void loadAddContacts() {
        System.out.println("\033\143");

        loadUI("AddContacts");
    }

    public void loadRemoveContacts() {
        System.out.println("\033\143");

        loadUI("RemoveContact");
    }

    public void loadViewContacts() {
        System.out.println("\033\143");

        loadUI("ViewContacts");
    }

    private void loadUI(String dashBoard) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/" + dashBoard + ".fxml"));
            mainContent.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Tela de visualização de contatos (apenas visualizar os dados do contato)
    @FXML
    void ViewContacts(ActionEvent event) {
        System.out.println("\033\143");

        loadViewContacts();
    }

    // Criar uma lixeira temporária para os contatos deletados

    // Remover vários itens selecionados ao mesmo tempo
}