package com.example;

import java.io.IOException;

import com.example.ContactsTable.AppState;
import com.example.ContactsTable.ContactService;
import com.example.warnings.AlertController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RemoveContactsController {

    @FXML
    private Button pro_exclude;

    @FXML
    private TableColumn<ContactService, Boolean> table1_Selected;

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

        // CheckBox dentro da tableView
        table1_Selected.setCellValueFactory(new PropertyValueFactory<>("selected"));
        table1_Selected.setCellFactory(CheckBoxTableCell.forTableColumn(table1_Selected));

        table_1.setItems(AppState.getContacts());

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

        // Dois cliques para selecionar o contato
        table_1.setItems(AppState.getContacts());

        table_1.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ContactService contacts = table_1.getSelectionModel().getSelectedItem();

                if (contacts != null) {
                    contacts.setSelected(!contacts.getSelected());
                    table_1.refresh();
                }
            }
        });

        // Alteração da cor quando selecionado
        table_1.setRowFactory(tv -> new TableRow<ContactService>() {

            @Override
            protected void updateItem(ContactService item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");

                } else if (item.getSelected()) {
                    setStyle("-fx-background-color:rgb(228, 18, 18);");

                } else {
                    setStyle("");
                }
            }
        });
    }

    @FXML
    void ExcludeContact(ActionEvent event) {

        // começaremos um for para armanezar os itens selecionados
        ObservableList<ContactService> selectedContacts = FXCollections.observableArrayList();
        for (ContactService contacts : table_1.getItems()) {
            if (contacts.getSelected()) {
                selectedContacts.add(contacts);
            }
        }

        // Confirmações
        if (selectedContacts.isEmpty()) {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Nenhuma seleção de contato!");
            warning.setHeaderText("Nenhum contato foi selecionado!");
            warning.setContentText("Por favor, selecione algum contato da lista de contatos e tente novamente!");
            warning.showAndWait();

            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Alerts/AlertExclude.fxml"));
            Parent root = loader.load();

            AlertController controller = loader.getController();
            controller.setDeleteContact(selectedContacts);

            Stage alertStage = new Stage();
            alertStage.setTitle("Alerta!");
            alertStage.setScene(new Scene(root));

            alertStage.initModality(Modality.APPLICATION_MODAL);

            alertStage.showAndWait();

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao tentar exibir o alerta!");
            System.out.println("Código do erro: ");
            System.out.println();
            e.getStackTrace();
        }
    }

    @FXML
    void SearchContacs(ActionEvent event) {

    }
}
