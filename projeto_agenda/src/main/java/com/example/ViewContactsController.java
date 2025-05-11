package com.example;

import java.io.IOException;

import com.example.ContactsTable.AppState;
import com.example.ContactsTable.ContactService;
import com.example.warnings.AlertViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewContactsController {

    @FXML
    private TextField View_SearchContacts;

    @FXML
    private Button View_ViewContacts;

    @FXML
    private TableColumn<ContactService, String> table1_birthdayDate;

    @FXML
    private TableColumn<ContactService, String> table1_email;

    @FXML
    private TableColumn<ContactService, String> table1_endress;

    @FXML
    private TableColumn<ContactService, String> table1_gender;

    @FXML
    private TableColumn<ContactService, String> table1_relation;

    @FXML
    private TableColumn<ContactService, String> table1_work;

    @FXML
    private TableView<ContactService> table_1;

    @FXML
    private TableColumn<ContactService, String> table_1Apelido;

    @FXML
    private TableColumn<ContactService, String> table_1Name;

    @FXML
    private TableColumn<ContactService, String> table_1NumeroTelefone;

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

        // Dois cliques para selecionar
        table_1.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && table_1.getSelectionModel().getSelectedItem() != null) {
                try {
                    ViewContacts(null);
                } catch (IOException e) {
                    System.out.println("Erro ao abrir a tela de visualização!");
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    void SearchContacs(ActionEvent event) {

    }

    @FXML
    void ViewContacts(ActionEvent event) throws IOException {
        ContactService selectedContact = table_1.getSelectionModel().getSelectedItem();

        if (selectedContact == null) {
            Alert warning = new Alert(Alert.AlertType.ERROR);
            warning.setTitle("Erro na seleção!");
            warning.setHeaderText("Ocorreu um erro ao carregar os detalhes do contato!");
            warning.setContentText("Nenhum contato foi selecionado, por favor selecione o contato e tente novamente!");
            warning.showAndWait();

            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Alerts/AlertViewContact.fxml"));
        Parent root = loader.load();

        AlertViewController controller = loader.getController();
        controller.ShowInformations(selectedContact);

        Stage alertStage = new Stage();
        alertStage.setTitle("Visualizar Contato!");
        alertStage.setScene(new Scene(root));

        alertStage.initModality(Modality.APPLICATION_MODAL);

        alertStage.showAndWait();

    }

    @FXML
    void ViewSearchContacs(ActionEvent event) {

    }
}
