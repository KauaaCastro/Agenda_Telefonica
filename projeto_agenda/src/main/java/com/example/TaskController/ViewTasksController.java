package com.example.TaskController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import com.example.ContactsTable.AppState;
import com.example.ContactsTable.ContactService;
import com.example.TaskTable.TaskContactRelation;
import com.example.TaskTable.TaskService;
import com.example.warnings.AlertExcludeTask;
import com.example.warnings.AlertViewController;
import com.example.warnings.TaskDescriptionAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewTasksController {

    @FXML
    private Button newExcludeTask;

    @FXML
    private Label show_EventDate;

    @FXML
    private Label show_Hoursevent;

    @FXML
    private Label show_eventEndress;

    @FXML
    private Label show_eventName;

    @FXML
    private TableView<ContactService> showContactTable;

    @FXML
    private TableColumn<ContactService, String> table_ShowName;

    @FXML
    private TableColumn<ContactService, String> table_ShowTell;

    @FXML
    private TableColumn<ContactService, String> table_showNick;

    @FXML
    private Button view_EditTask;

    @FXML
    private Button view_return;

    @FXML
    private Button description_Return;

    @FXML
    private String text_Description;

    private TaskService currentTask;

    @FXML
    void initialize() {
        table_ShowName.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_ShowTell.setCellValueFactory(new PropertyValueFactory<>("tellNumber"));
        table_showNick.setCellValueFactory(new PropertyValueFactory<>("nickName"));

        showContactTable.setRowFactory(tv -> {
            TableRow<ContactService> row = new TableRow<>();

            ContextMenu contextMenu = new ContextMenu();

            MenuItem viewContact = new MenuItem("Visualizar Contato");
            viewContact.setOnAction(event -> {

                ContactService selected = row.getItem();
                if (selected != null) {

                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/com/example/Alerts/AlertViewContacts(Add).fxml"));
                        Parent root = loader.load();

                        AlertViewController controller = loader.getController();
                        controller.ShowInformations(selected);

                        Stage stage = new Stage();
                        stage.setTitle("Visualizar Contato");
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();

                    } catch (IOException e) {
                        System.out.println("Erro ao visualizar contato.");
                        e.printStackTrace();
                    }
                }
            });

            contextMenu.getItems().addAll(viewContact);
            row.setContextMenu(contextMenu);
            return row;
        });

        // Clicar duas vezes no contato para abrir a tela de visualização de contatos
        showContactTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ContactService contact = showContactTable.getSelectionModel().getSelectedItem();

                if (contact != null) {
                    contact.setSelected(!contact.getSelected());

                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/com/example/Alerts/AlertViewContacts(Add).fxml"));
                        Parent root = loader.load();

                        AlertViewController controller = loader.getController();
                        controller.ShowInformations(contact);

                        Stage alertStage = new Stage();
                        alertStage.setTitle("Visualizar Contato!");
                        alertStage.setScene(new Scene(root));

                        alertStage.showAndWait();

                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Ocorreu um erro ao visualizar o contato selecionado (Tabela de tarefas)");
                    }
                }
            }
        });

    }

    // Recebe os itens selecionados!
    public void setTask(TaskService task, TaskContactRelation relation) {
        this.currentTask = task;
        List<String> relatedContactIds = relation.getContactId();

        List<ContactService> contactsFiltered = AppState.getContacts().stream()
                .filter(c -> relatedContactIds.contains(c.getId()))
                .collect(Collectors.toList());

        ObservableList<ContactService> observableFiltered = FXCollections.observableArrayList(contactsFiltered);

        showContactTable.setItems(observableFiltered);

        show_eventName.setText(task.getTaskName());
        show_eventEndress.setText(task.getTaskEndress());
        show_Hoursevent.setText(task.getTaskTime());

        try {
            DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
            DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(task.getTaskDate(), isoFormatter);
            String formattedDate = date.format(displayFormatter);
            show_EventDate.setText(formattedDate);

        } catch (DateTimeParseException e) {
            e.printStackTrace();
            System.out.println("Erro ao converter data: " + task.getTaskDate());
            show_EventDate.setText(task.getTaskDate()); // Fallback
        }

        this.text_Description = task.getTaskDescription();
    }

    @FXML
    void GoToDescription(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/Alerts/TaskScreenDescription.fxml"));
            Parent root = loader.load();

            TaskDescriptionAlert controller = loader.getController();
            controller.getDescription(text_Description);

            Stage stage = new Stage();
            stage.setTitle("Descrição da tarefa");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            System.out.println();
            e.printStackTrace();
            System.out.println("Ocorreu um erro ao carregar a tela de descrição!");
        }
    }

    @FXML
    void GoToEditTask(ActionEvent event) {
        Stage oldStage = (Stage) view_return.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/TaskScreen/TaskEdit.fxml"));
            Parent root = loader.load();

            EditTaskController controller = loader.getController();
            controller.setTaskShow(currentTask);

            Stage stage = new Stage();
            stage.setTitle("Editar tarefa");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            oldStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ocorreu um erro ao abrir a tela de edição de tarefas!");
        }
    }

    @FXML
    void GoToExcludeTask(ActionEvent event) {
        Stage oldStage = (Stage) view_return.getScene().getWindow();
        oldStage.hide();

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/Alerts/ExcludeTaskConfirm.fxml"));
            Parent root = loader.load();

            AlertExcludeTask controller = loader.getController();
            controller.setTasksToDelete(List.of(currentTask));

            Stage stage = new Stage();
            stage.setTitle("Exclusão da tarefa");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            oldStage.hide();
            stage.show();

            oldStage.close();

        } catch (IOException e) {
            System.out.println();
            e.printStackTrace();
            System.out.println("Ocorreu um erro ao carregar a tela de exclusão!");
        }

    }

    @FXML
    void ReturnScreen(ActionEvent event) {
        Stage stage = (Stage) view_return.getScene().getWindow();
        stage.close();
    }
}