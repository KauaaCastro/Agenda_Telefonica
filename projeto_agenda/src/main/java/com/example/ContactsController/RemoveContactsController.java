package com.example.ContactsController;

import java.io.IOException;
import java.util.List;

import com.example.ContactsTable.AppState;
import com.example.ContactsTable.ContactService;
import com.example.ContactsTable.LocalStorageManager;
import com.example.warnings.AlertController;
import com.example.warnings.AlertViewController;

import javafx.collections.FXCollections;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RemoveContactsController {

    @FXML
    private TextField remove_SearchContacts;

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
        table_1.setItems(AppState.getContacts());
        LocalStorageManager manager = new LocalStorageManager();
        List<ContactService> contact = manager.LoadContact();

        // Atualiza a lista central (não substitua a lista, só atualize)
        AppState.getContacts().setAll(contact);
        // CheckBox dentro da tableView
        table1_Selected.setCellValueFactory(new PropertyValueFactory<>("selected"));
        table1_Selected.setCellFactory(CheckBoxTableCell.forTableColumn(table1_Selected));

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

        // Ativando o botão esquerdo do mouse
        table_1.setRowFactory(tv -> {
            TableRow<ContactService> row = new TableRow<>() {

                // Aplicar cor ao item selecionado
                @Override
                protected void updateItem(ContactService item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setStyle("");
                    } else if (item.getSelected()) {
                        setStyle("-fx-background-color: rgb(228, 18, 18);");
                    } else {
                        setStyle("");
                    }
                }
            };

            // Menu de opções com o botão esquerdo
            ContextMenu contextMenu = new ContextMenu();

            MenuItem shortcutDelete = new MenuItem("Excluir contato");
            shortcutDelete.setOnAction(event -> {
                ContactService selected = row.getItem();
                if (selected != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/com/example/Alerts/AlertExclude.fxml"));
                        Parent root = loader.load();
                        AlertController controller = loader.getController();
                        controller.setDeleteContact(List.of(selected));

                        Stage shortRemoveOpen = new Stage();
                        shortRemoveOpen.setTitle("Excluir contato - atalho");
                        shortRemoveOpen.setScene(new Scene(root));
                        shortRemoveOpen.initModality(Modality.APPLICATION_MODAL);
                        shortRemoveOpen.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            MenuItem shortcutView = new MenuItem("Visualizar contato");
            shortcutView.setOnAction(event -> {
                ContactService selected = row.getItem();
                if (selected != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/com/example/Alerts/AlertViewContacts(Add).fxml"));
                        Parent root = loader.load();
                        AlertViewController controller = loader.getController();
                        controller.ShowInformations(selected);

                        Stage viewOption = new Stage();
                        viewOption.setTitle("Visualizar contato - atalho");
                        viewOption.setScene(new Scene(root));
                        viewOption.initModality(Modality.APPLICATION_MODAL);
                        viewOption.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            contextMenu.getItems().addAll(shortcutView, shortcutDelete);
            row.setContextMenu(contextMenu);

            return row;
        });

        // Barra de pesquisa
        ObservableList<ContactService> contactsList = AppState.getContacts();

        FilteredList<ContactService> filteredData = new FilteredList<>(contactsList, p -> true);

        remove_SearchContacts.textProperty().addListener((Observable, OldValue, NewValue) -> {
            filteredData.setPredicate(contacts -> {
                if (NewValue == null || NewValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = NewValue.toLowerCase();

                return (contacts.getName() != null && contacts.getName().toLowerCase().contains(lowerCaseFilter))
                        || (contacts.getNickName() != null
                                && contacts.getNickName().toLowerCase().contains(lowerCaseFilter))
                        || (contacts.getTellNumber() != null
                                && contacts.getTellNumber().toLowerCase().contains(lowerCaseFilter))
                        || (contacts.getEmailContact() != null
                                && contacts.getEmailContact().toLowerCase().contains(lowerCaseFilter))
                        || (contacts.getDateBirthday() != null
                                && contacts.getDateBirthday().toLowerCase().contains(lowerCaseFilter))
                        || (contacts.getGender() != null
                                && contacts.getGender().toLowerCase().contains(lowerCaseFilter))
                        || (contacts.getWorkContact() != null
                                && contacts.getWorkContact().toLowerCase().contains(lowerCaseFilter))
                        || (contacts.getEndressContact() != null
                                && contacts.getEndressContact().toLowerCase().contains(lowerCaseFilter))
                        || (contacts.getRelationContact() != null
                                && contacts.getRelationContact().toLowerCase().contains(lowerCaseFilter));
            });
        });

        SortedList<ContactService> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_1.comparatorProperty());

        table_1.setItems(sortedData);
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
}
