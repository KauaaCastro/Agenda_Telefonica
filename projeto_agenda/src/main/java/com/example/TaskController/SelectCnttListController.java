package com.example.TaskController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.ContactsTable.AppState;
import com.example.ContactsTable.ContactService;
import com.example.ContactsTable.LocalStorageManager;
import com.example.warnings.AlertViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SelectCnttListController {

    @FXML
    private TableView<ContactService> Table_ListTask;

    @FXML
    private TableColumn<ContactService, String> table_NickName;

    @FXML
    private TableColumn<ContactService, String> table_Name;

    @FXML
    private TableColumn<ContactService, String> table_TellNumber;

    @FXML
    private TableColumn<ContactService, Boolean> table_SelectContact;

    @FXML
    private Button view_Return;

    @FXML
    private Button view_save;

    @FXML
    private Button view_ViewContact;

    @FXML
    private TextField search_ListTask;

    private AddTaskController addTaskController;

    private EditTaskController editTaskController;

    @FXML
    private static ObservableList<ContactService> contacts = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        LocalStorageManager manager = new LocalStorageManager();
        List<ContactService> contacts = manager.LoadContact();

        AppState.getContacts().setAll(contacts);

        table_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_TellNumber.setCellValueFactory(new PropertyValueFactory<>("tellNumber"));
        table_NickName.setCellValueFactory(new PropertyValueFactory<>("nickName"));
        table_SelectContact.setCellValueFactory(new PropertyValueFactory<>("selected"));

        table_SelectContact.setCellFactory(CheckBoxTableCell.forTableColumn(table_SelectContact));

        // Barra de pesquisa
        ObservableList<ContactService> contactsList = AppState.getContacts();
        FilteredList<ContactService> filteredData = new FilteredList<>(contactsList, p -> true);
        Table_ListTask.setItems(filteredData);

        search_ListTask.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(contact -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return (contact.getName() != null && contact.getName().toLowerCase().contains(lowerCaseFilter))
                        || (contact.getNickName() != null
                                && contact.getNickName().toLowerCase().contains(lowerCaseFilter))
                        || (contact.getTellNumber() != null
                                && contact.getTellNumber().toLowerCase().contains(lowerCaseFilter));
            });
        });

        // Clique duplo para selecionar
        Table_ListTask.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ContactService contact = Table_ListTask.getSelectionModel().getSelectedItem();

                if (contact != null) {
                    contact.setSelected(!contact.getSelected());
                    Table_ListTask.refresh();
                }
            }
        });

        Table_ListTask.setRowFactory(tv -> {

            TableRow<ContactService> row = new TableRow<>() {

                // Aplicar cor ao item selecionado
                @Override
                protected void updateItem(ContactService item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setStyle("");
                    } else if (item.getSelected()) {
                        setStyle("-fx-background-color: #0a7acc");
                    } else {
                        setStyle("");
                    }
                }
            };

            // ContextMenu e atalhos
            ContextMenu contextMenu = new ContextMenu();
            MenuItem shortcutView = new MenuItem("Visualizar contato");

            shortcutView.setOnAction(event -> {
                ViewContactInfo(event);
            });

            MenuItem shortcutSelect = new MenuItem("Selecionar contato");

            shortcutSelect.setOnAction(event -> {
                ContactService selected = row.getItem();

                if (selected != null) {
                    selected.setSelected(true);
                }
            });

            MenuItem shortcutDeselect = new MenuItem("Deselecionar contato");

            shortcutDeselect.setOnAction(event -> {
                ContactService selected = row.getItem();

                if (selected != null) {
                    selected.setSelected(false);
                }
            });

            contextMenu.getItems().addAll(shortcutView, shortcutSelect, shortcutDeselect);
            row.setContextMenu(contextMenu);

            return row;
        });
    }

    // Botão de retorno
    @FXML
    void GoToHomeScreen(ActionEvent event) {
        Stage stage = (Stage) view_Return.getScene().getWindow();
        stage.close();
    }

    // Botão de visualizar contatos
    @FXML
    void ViewContactInfo(ActionEvent event) {
        ContactService selected = Table_ListTask.getSelectionModel().getSelectedItem();

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/Alerts/AlertViewContacts(Add).fxml"));
            Parent root = loader.load();

            AlertViewController controller = loader.getController();
            controller.ShowInformations(selected);

            Stage stage = new Stage();
            stage.setTitle("Seleção de contatos");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("");
            System.out.println("Ocorreu um erro ao visualizar contato (Selecionado)");
        }
    }

    // Botão se salvar
    @FXML
    void view_Save(ActionEvent event) {

        List<ContactService> selectedContact = AppState.getContacts().filtered(ContactService::getSelected);

        if (selectedContact.isEmpty()) {

            Alert information = new Alert(AlertType.INFORMATION);
            information.setTitle("Ocorreu um erro!");
            information.setHeaderText("Ocorreu um erro ao adicionar os contatos!");
            information.setContentText("Nenhum contato foi selecionado!");
            information.getButtonTypes().clear();
            information.initModality(Modality.APPLICATION_MODAL);

            ButtonType insert = new ButtonType("Continuar mesmo assim", ButtonBar.ButtonData.OK_DONE);
            ButtonType newCancel = new ButtonType("Retornar e selecionar", ButtonBar.ButtonData.CANCEL_CLOSE);
            information.getButtonTypes().addAll(insert, newCancel);
            Optional<ButtonType> result = information.showAndWait();

            if (result.isPresent() && result.get() != newCancel) {
                Stage stage = (Stage) view_save.getScene().getWindow();
                stage.close();

            }

        } else {
            if (addTaskController != null) {
                System.out.println("Salvo em adicionando contatos");

                List<String> selectedContactIDs = selectedContact.stream().map(ContactService::getId)
                        .collect(Collectors.toList());

                addTaskController.setSelectedContactIDs(selectedContactIDs);

            } else if (editTaskController != null) { // Falta verificar
                System.out.println("Salvo em editar contatos");

                List<String> selectedContactIDs = selectedContact.stream().map(ContactService::getId)
                        .collect(Collectors.toList());

                editTaskController.setSelectedContactIDs(selectedContactIDs);

            }

            Stage stage = (Stage) view_save.getScene().getWindow();
            stage.close();

        }
    }

    // Função para adicionar os contatos em: addContacts
    public void setAddTaskController(AddTaskController controller) {
        this.addTaskController = controller;
    }

    @FXML
    public static ObservableList<ContactService> getContacts() {
        return contacts;
    }

    // Função para adicionar os contatos em EditTask
    public void setEditTaskConroller(EditTaskController controller) {
        this.editTaskController = controller;
    }
}
