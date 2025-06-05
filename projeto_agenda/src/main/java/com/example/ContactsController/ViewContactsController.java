package com.example.ContactsController;

import java.io.IOException;
import com.example.ContactsTable.AppState;
import com.example.ContactsTable.ContactService;
import com.example.warnings.AlertViewController;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.stage.Stage;

public class ViewContactsController {

    @FXML
    private TextField view_SearchContacts;

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

        // Barra de pesquisa
        ObservableList<ContactService> contactsList = AppState.getContacts();

        FilteredList<ContactService> filteredData = new FilteredList<>(contactsList, p -> true);

        view_SearchContacts.textProperty().addListener((Observable, OldValue, NewValue) -> {
            filteredData.setPredicate(contacts -> {
                if (NewValue == null || NewValue.isEmpty()) {
                    return true;
                }

                String LowerCaseFilter = NewValue.toLowerCase();

                return (contacts.getName() != null && contacts.getName().toLowerCase().contains(LowerCaseFilter))
                        || (contacts.getNickName() != null
                                && contacts.getNickName().toLowerCase().contains(LowerCaseFilter))
                        || (contacts.getTellNumber() != null
                                && contacts.getTellNumber().toLowerCase().contains(LowerCaseFilter))
                        || (contacts.getEmailContact() != null
                                && contacts.getEmailContact().toLowerCase().contains(LowerCaseFilter))
                        || (contacts.getDateBirthday() != null
                                && contacts.getDateBirthday().toLowerCase().contains(LowerCaseFilter))
                        || (contacts.getGender() != null
                                && contacts.getGender().toLowerCase().contains(LowerCaseFilter))
                        || (contacts.getWorkContact() != null
                                && contacts.getWorkContact().toLowerCase().contains(LowerCaseFilter))
                        || (contacts.getEndressContact() != null
                                && contacts.getEndressContact().toLowerCase().contains(LowerCaseFilter))
                        || (contacts.getRelationContact() != null
                                && contacts.getRelationContact().toLowerCase().contains(LowerCaseFilter));
            });
        });

        SortedList<ContactService> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_1.comparatorProperty());

        table_1.setItems(sortedData);

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

        alertStage.showAndWait();
    }
}
