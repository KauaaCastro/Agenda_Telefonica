package com.example.TaskController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import com.example.TaskStorageManager.TaskLSManager;
import com.example.TaskTable.TaskContactRelation;
import com.example.TaskTable.TaskContactState;
import com.example.TaskTable.TaskService;
import com.example.warnings.AlertExcludeTask;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListTaskController {

    @FXML
    private TableView<TaskService> Table_ListTask;

    @FXML
    private TableColumn<TaskService, String> date_Hours;

    @FXML
    private TableColumn<TaskService, String> table_Date;

    @FXML
    private TableColumn<TaskService, String> table_Name;

    @FXML
    private Button list_ViewTask;

    @FXML
    private Button Return;

    @FXML
    private LocalDate selectedDate;

    @FXML
    private ObservableList<TaskService> showList;

    @FXML
    private FilteredList<TaskService> filteredData;

    private ObservableList<TaskService> taskList;

    @FXML
    public void initialize() {

        table_Name.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        table_Date.setCellValueFactory(new PropertyValueFactory<>("taskDate"));
        date_Hours.setCellValueFactory(new PropertyValueFactory<>("taskTime"));

        Table_ListTask.setItems(taskList);

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

        // Clique duplo para selecionar
        Table_ListTask.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ShowTaskAction();
            }
        });

        // Clique com o botão esquerdo
        Table_ListTask.setRowFactory(tv -> {
            TableRow<TaskService> row = new TableRow<>();

            ContextMenu contextMenu = new ContextMenu();
            MenuItem shortcutView = new MenuItem("Visualizar tarefa");

            shortcutView.setOnAction(event -> {
                ShowTaskSelected(event);
            });

            MenuItem shortcutDelete = new MenuItem("Excluir tarefa");

            shortcutDelete.setOnAction(event -> {
                TaskService taskService = row.getItem();

                if (taskService != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/com/example/Alerts/ExcludeTaskConfirm.fxml"));
                        Parent root = loader.load();

                        AlertExcludeTask controller = loader.getController();
                        controller.setTasksToDelete(List.of(taskService));

                        Stage stage = new Stage();
                        stage.setTitle("Excluir contato selecionado");
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();

                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println();
                        System.out.println("Ocorre um erro ao tentar abrir a lista de selecao contatos");
                    }
                }
            });

            contextMenu.getItems().addAll(shortcutView, shortcutDelete);
            row.setContextMenu(contextMenu);
            return row;
        });

    }

    // Carregar as tarefas baseado nas datas
    public void loadTasksForDate(LocalDate selectedDate, ObservableList<TaskService> taskList) {
        this.selectedDate = selectedDate;

        List<TaskService> tasksFromJson = TaskLSManager.loadTasksFromJson();

        List<TaskService> filteredTasks = tasksFromJson.stream()
                .filter(task -> task.getTaskDate().equals(selectedDate.toString()))
                .collect(Collectors.toList());

        taskList.clear();
        taskList.addAll(filteredTasks);
        Table_ListTask.setItems(taskList);
    }

    // Mostrar tela de tarefa
    @FXML
    void ShowTaskSelected(ActionEvent event) {
        ShowTaskAction();
    }

    // Retornar para tela anterior
    @FXML
    void GoToHomeScreen(ActionEvent event) {
        Stage stage = (Stage) Return.getScene().getWindow();
        stage.hide();
        stage.close();
    }

    // Lógica para usar o showtask
    void ShowTaskAction() {
        TaskService taskService = Table_ListTask.getSelectionModel().getSelectedItem();

        if (taskService != null) {
            TaskLSManager.loadRelationsFromJson();
            String taskId = taskService.getTaskId();
            ObservableList<TaskContactRelation> relations = TaskContactState.getRelationsByTaskId(taskId);

            TaskContactRelation relation = null;

            if (!relations.isEmpty()) {
                relation = relations.get(0);

            } else {
                System.out.println("Nenhuma relação encontrada, seguindo sem relação.");

            }

            // continua normalmente
            Stage oldStage = (Stage) Return.getScene().getWindow();
            oldStage.hide();

            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/example/TaskScreen/ViewTaskScreen.fxml"));
                Parent root = loader.load();

                System.out.println("Relações carregadas: " + TaskContactState.getTaskContactRelations().size());

                ViewTasksController controller = loader.getController();
                controller.setTask(taskService, relation);

                Stage stage = new Stage();
                stage.setTitle("Visualizar Tarefa");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();

                stage.setOnHiding(e -> oldStage.close());

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erro ao abrir a tela de visualização da tarefa");
            }

        }
    }
}
