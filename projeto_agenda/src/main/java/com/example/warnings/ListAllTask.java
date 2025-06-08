package com.example.warnings;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import com.example.ContactsTable.AppState;
import com.example.ContactsTable.ContactService;
import com.example.TaskController.ViewTasksController;
import com.example.TaskTable.TaskAppState;
import com.example.TaskTable.TaskContactRelation;
import com.example.TaskTable.TaskContactState;
import com.example.TaskTable.TaskService;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListAllTask {

    @FXML
    private Button Return;

    @FXML
    private Button list_ViewTask;

    @FXML
    private TextField search_ListTask;

    @FXML
    private TableView<TaskService> Table_ListTask;

    @FXML
    private TableColumn<TaskService, String> date_Hours;

    @FXML
    private TableColumn<TaskService, String> table_Date;

    @FXML
    private TableColumn<TaskService, String> table_Name;

    @FXML
    void initialize() {
        table_Name.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        table_Date.setCellValueFactory(new PropertyValueFactory<>("taskDate"));
        date_Hours.setCellValueFactory(new PropertyValueFactory<>("taskTime"));

        // Formatação da data usando UpdateItem
        table_Date.setCellFactory(column -> new TableCell<TaskService, String>() {

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);

                } else {
                    LocalDate date = LocalDate.parse(item);
                    setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
            }
        });

        // Barra de pesquisa
        ObservableList<TaskService> taskList = TaskAppState.getTasks();
        FilteredList<TaskService> filteredData = new FilteredList<>(taskList, p -> true);
        Table_ListTask.setItems(filteredData);

        search_ListTask.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tasks -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return (tasks.getTaskName() != null && tasks.getTaskName().toLowerCase().contains(lowerCaseFilter))
                        || (tasks.getTaskDate() != null
                                && tasks.getTaskDate().toLowerCase().contains(lowerCaseFilter))
                        || (tasks.getTaskTime() != null
                                && tasks.getTaskTime().toLowerCase().contains(lowerCaseFilter));
            });
        });

        // Clique duplo para selecionar
        Table_ListTask.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TaskService taskService = Table_ListTask.getSelectionModel().getSelectedItem();

                if (taskService != null) {
                    String taskId = taskService.getTaskId();

                    // Responsavel por pegar as relaçoes cntt - tarefas
                    ObservableList<TaskContactRelation> relations = TaskContactState.getRelationsByTaskId(taskId);

                    List<String> contactIds = relations.stream() // Responsavel por resgatar os Ids dos contatos
                            .flatMap(rel -> rel.getContactId().stream())
                            .collect(Collectors.toList());

                    @SuppressWarnings("unused")
                    List<ContactService> relatedContacts = AppState.getContacts().stream() // Responsavel por pegar os
                            .filter(contact -> contactIds.contains(contact.getId())) // contatos correspondentes
                            .collect(Collectors.toList()); // ao id

                    Stage oldStage = (Stage) Return.getScene().getWindow();
                    oldStage.hide();

                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/com/example/TaskScreen/ViewTaskScreen.fxml"));
                        Parent root = loader.load();

                        ViewTasksController controller = loader.getController();
                        TaskContactRelation relation = relations.get(0); // por exemplo, o primeiro da lista
                        controller.setTask(taskService, relation);

                        Stage stage = new Stage();
                        stage.setTitle("Seleção de contatos");
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.show();

                        stage.setOnHiding(e -> oldStage.close());

                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println();
                        System.out.println("Ocorre um erro ao tentar abrir a lista de selecao contatos");
                    }
                }
            }
        });

    }

    // Mostrar tela da tarefa
    @FXML
    void ShowTaskSelected(ActionEvent event) {
        TaskService taskService = Table_ListTask.getSelectionModel().getSelectedItem();

        if (taskService != null) {
            String taskId = taskService.getTaskId();

            // Responsavel por pegar as relaçoes cntt - tarefas
            ObservableList<TaskContactRelation> relations = TaskContactState.getRelationsByTaskId(taskId);

            List<String> contactIds = relations.stream() // Responsavel por resgatar os Ids dos contatos
                    .flatMap(rel -> rel.getContactId().stream())
                    .collect(Collectors.toList());

            @SuppressWarnings("unused")
            List<ContactService> relatedContacts = AppState.getContacts().stream() // Responsavel por pegar os
                    .filter(contact -> contactIds.contains(contact.getId())) // contatos correspondentes
                    .collect(Collectors.toList()); // ao id

            Stage oldStage = (Stage) Return.getScene().getWindow();
            oldStage.hide();

            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/example/TaskScreen/ViewTaskScreen.fxml"));
                Parent root = loader.load();

                ViewTasksController controller = loader.getController();
                TaskContactRelation relation = relations.get(0);
                controller.setTask(taskService, relation);

                Stage stage = new Stage();
                stage.setTitle("Seleção de contatos");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();

                stage.close();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println();
                System.out.println("Ocorre um erro ao tentar abrir a lista de selecao contatos");
            }
        }
    }

    @FXML
    void GoToHomeScreen(ActionEvent event) {
        Stage stage = (Stage) Return.getScene().getWindow();
        stage.hide();
        stage.close();
    }

}
