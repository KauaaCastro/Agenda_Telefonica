package com.example.TaskController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.ContactsTable.AppState;
import com.example.ContactsTable.ContactService;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TaskDeleteListController {

    @FXML
    private Button Return;

    @FXML
    private Button exclude_Delete;

    @FXML
    private TextField search_ListTask;

    @FXML
    private Button Exclude_view;

    @FXML
    private FilteredList<TaskService> filteredData;

    @FXML
    private TableColumn<TaskService, Boolean> select_exclude;

    @FXML
    private TableColumn<TaskService, String> date_Hours;

    @FXML
    private TableColumn<TaskService, String> table_Date;

    @FXML
    private TableColumn<TaskService, String> table_Name;

    @FXML
    private TableView<TaskService> Table_ListTask;

    @FXML
    void initialize() {
        table_Name.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        table_Date.setCellValueFactory(new PropertyValueFactory<>("taskDate"));
        date_Hours.setCellValueFactory(new PropertyValueFactory<>("taskTime"));

        select_exclude.setCellValueFactory(new PropertyValueFactory<>("selected"));
        select_exclude.setCellFactory(CheckBoxTableCell.forTableColumn(select_exclude));

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
                TaskService task = Table_ListTask.getSelectionModel().getSelectedItem();

                if (task != null) {
                    task.setSelected(!task.getSelected());
                    Table_ListTask.refresh();
                }
            }
        });

        Table_ListTask.setRowFactory(tv -> {

            TableRow<TaskService> row = new TableRow<>() {

                // Aplicar cor ao item selecionado
                @Override
                protected void updateItem(TaskService item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setStyle("");
                    } else if (item.getSelected()) {
                        setStyle("-fx-background-color:rgb(204, 10, 10)");
                    } else {
                        setStyle("");
                    }
                }
            };
            return row;
        });

    }

    @FXML
    void ExcludeTask(ActionEvent event) {

        Alert warning = new Alert(AlertType.CONFIRMATION);
        warning.setTitle("Excluindo tarefa");
        warning.setHeaderText("Tem certeza que deseja excluir esta tarefa?");
        warning.setContentText("Se excluir a tarefa, ela não poderá ser encontrada novamente...");

        warning.getButtonTypes().clear();
        ButtonType insert = new ButtonType("Confirmar e excluir", ButtonBar.ButtonData.OK_DONE);
        ButtonType newCancel = new ButtonType("Cancelar exclusão", ButtonBar.ButtonData.CANCEL_CLOSE);
        warning.getButtonTypes().addAll(insert, newCancel);

        Optional<ButtonType> result = warning.showAndWait();

        if (result.isPresent() && result.get() != newCancel) {
            List<TaskService> selectedTasks = TaskAppState.getTasks()
                    .stream()
                    .filter(TaskService::getSelected)
                    .collect(Collectors.toList());

            for (TaskService task : selectedTasks) {
                task.setSelected(false);
                TaskAppState.RemoveTask(task);
            }

            Table_ListTask.getSelectionModel().clearSelection();
            Table_ListTask.refresh();
        } else {
            return;
        }

    }

    // Exibir a tarefa ao clicar em visualizar!
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
                stage.show();

                stage.setOnHiding(e -> oldStage.show());

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
        stage.close();
    }
}
