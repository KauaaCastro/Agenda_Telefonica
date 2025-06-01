package com.example;

import java.io.IOException;
import java.util.List;
import com.example.ContactsTable.AppState;
import com.example.ContactsTable.ContactService;
import com.example.ContactsTable.LocalStorageManager;
import com.example.warnings.AlertController;
import com.example.warnings.AlertEditController;
import com.example.warnings.AlertViewController;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//Controlador da tela inicial, controller principal

public class PrimaryController {

    @FXML
    private Button pro_TaskScreen;

    @FXML
    private VBox dashBoard;

    @FXML
    private VBox mainContent;

    @FXML
    private TextField pro_SearchContacts;

    @FXML
    private Button pro_addContact;

    @FXML
    private Button pro_removeContacts;

    @FXML
    private Button pro_visualContacts;

    @FXML
    private Button returnHomeScreen;

    @FXML
    private Button config_table;

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

    LocalStorageManager storage = new LocalStorageManager();

    // Atualizar dinamicamente a lista de contatos
    @SuppressWarnings("null") // Remover para realização de testes!!!!!!
    @FXML
    public void initialize() {

        LocalStorageManager manager = new LocalStorageManager();
        List<ContactService> contact = manager.LoadContact();

        // Atualiza a lista central (não substitua a lista, só atualize)
        AppState.getContacts().setAll(contact);

        // Configura colunas da tabela
        table_1Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table1_birthdayDate.setCellValueFactory(new PropertyValueFactory<>("dateBirthday"));
        table1_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        table_1NumeroTelefone.setCellValueFactory(new PropertyValueFactory<>("tellNumber"));
        table_1Apelido.setCellValueFactory(new PropertyValueFactory<>("nickName"));
        table1_email.setCellValueFactory(new PropertyValueFactory<>("emailContact"));
        table1_endress.setCellValueFactory(new PropertyValueFactory<>("endressContact"));
        table1_work.setCellValueFactory(new PropertyValueFactory<>("workContact"));
        table1_relation.setCellValueFactory(new PropertyValueFactory<>("relationContact"));

        // Configura filtro e busca
        // Barra de pesquisa
        ObservableList<ContactService> contactsList = AppState.getContacts();

        FilteredList<ContactService> filteredData = new FilteredList<>(contactsList, p -> true);

        pro_SearchContacts.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(contacts -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

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

        // Ativar botão esquerdo do mouse e colocar os atalhos para as funções:
        table_1.setRowFactory(tv -> {
            TableRow<ContactService> row = new TableRow<>();

            ContextMenu contextMenu = new ContextMenu();
            MenuItem ShortcutView = new MenuItem("Visualizar contato");

            ShortcutView.setOnAction(event -> {
                ContactService selected = row.getItem();

                if (selected != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/com/example/Alerts/AlertViewContacts(Add).fxml"));
                        Parent root = loader.load();

                        AlertViewController controller = loader.getController();
                        controller.ShowInformations(selected);

                        Stage visualization = new Stage();
                        visualization.setTitle("Visualizar contato - atalho");
                        visualization.setScene(new Scene(root));
                        visualization.initModality(Modality.APPLICATION_MODAL);
                        visualization.showAndWait();

                    } catch (IOException e) {
                        System.out.println("Erro ao inicializar o view contacts pelo atalho");
                        e.printStackTrace();
                    }
                    System.out.println("Visualizar " + selected.getName());
                }
            });

            MenuItem ShortcutEdit = new MenuItem("Editar contato");

            ShortcutEdit.setOnAction(event -> {
                ContactService selected = row.getItem();

                if (selected != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/com/example/Alerts/AlertEditScreen.fxml"));
                        Parent root = loader.load();

                        AlertEditController controller = loader.getController();
                        controller.setContactToEdit(selected);

                        Stage editionOption = new Stage();
                        editionOption.setTitle("Editar contato - atalho");
                        editionOption.setScene(new Scene(root));
                        editionOption.initModality(Modality.APPLICATION_MODAL);
                        editionOption.showAndWait();

                        table_1.refresh();

                    } catch (IOException e) {
                        System.out.println("Erro ao inicializar o editor de contatos pelo atalho");
                        e.printStackTrace();
                    }

                } else {
                    System.out.println("Editar: " + selected.getName());

                    Alert warning = new Alert(Alert.AlertType.ERROR);

                    warning.setTitle("Erro ao selecionar contato");
                    warning.setHeaderText("Nenhum contato foi selecionado");
                    warning.setContentText("Por favor, selecione novamente o contato");
                    warning.showAndWait();
                }

            });

            MenuItem ShortcutRemove = new MenuItem("Excluir contato");

            ShortcutRemove.setOnAction(event -> {
                ContactService selected = row.getItem();

                if (selected != null) {
                    try {

                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/com/example/Alerts/AlertExclude.fxml"));
                        Parent root = loader.load();

                        AlertController controller = loader.getController();
                        controller.setDeleteContact(List.of(selected));

                        Stage removeOption = new Stage();
                        removeOption.setTitle("Remover contato - atalho");
                        removeOption.setScene(new Scene(root));
                        removeOption.initModality(Modality.APPLICATION_MODAL);
                        removeOption.showAndWait();

                    } catch (IOException e) {
                        System.out.println("Ocorreu um erro ao inicializar o removedor de contatos pelo atalho");
                        e.getStackTrace();
                    }
                    System.out.println("Removendo: " + selected.getName());
                }
            });

            contextMenu.getItems().addAll(ShortcutEdit, ShortcutView, ShortcutRemove);
            row.setContextMenu(contextMenu);
            table_1.setOnContextMenuRequested(event -> {
                contextMenu.show(table_1, event.getScreenX(), event.getScreenY());
            });

            return row;
        });

        // Menu de contexto de configuração da tabela de contatos
        // Criação do menu de colunas
        ContextMenu columnMenu = new ContextMenu();
        List<TableColumn<ContactService, ?>> columns = List.of(
                table_1Name, table_1Apelido, table_1NumeroTelefone, table1_gender,
                table1_birthdayDate, table1_email, table1_relation, table1_work,
                table1_endress);

        for (TableColumn<ContactService, ?> column : columns) {
            CheckMenuItem item = new CheckMenuItem(column.getText());
            item.setSelected(column.isVisible());
            item.selectedProperty().addListener((obs, oldVal, newVal) -> column.setVisible(newVal));
            columnMenu.getItems().add(item);
        }

        config_table.setOnAction(e -> {
            columnMenu.show(config_table, Side.BOTTOM, 0, 0);
        });
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

    @FXML
    void ViewContacts(ActionEvent event) {
        System.out.println("\033\143");

        loadViewContacts();
    }

    @FXML
    void GoToTaskScreen(ActionEvent event) {
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

    // Criar uma lixeira temporária para os contatos deletados

    // Formatação de datas de forma padronizada
}
