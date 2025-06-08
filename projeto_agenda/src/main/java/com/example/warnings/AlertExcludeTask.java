package com.example.warnings;

import java.io.IOException;
import java.util.List;

import com.example.TaskStorageManager.TaskLSManager;
import com.example.TaskTable.TaskAppState;
import com.example.TaskTable.TaskService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertExcludeTask {

    @FXML
    private Label Alert_Menssage;

    @FXML
    private Button ExcludeConfirm;

    @FXML
    private Button cancel;

    private List<TaskService> tasksToDelete;

    TaskLSManager manager = new TaskLSManager();

    public void setTasksToDelete(List<TaskService> tasks) {
        this.tasksToDelete = tasks;
        Alert_Menssage.setText("Você tem certeza que deseja excluir " + tasks.size() + " tarefa(s)?");
    }

    @FXML
    void CancelRemove(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
        System.out.println("Cancelado");
    }

    @FXML
    void ConfirmationExclude(ActionEvent event) {

        if (tasksToDelete != null && !tasksToDelete.isEmpty()) {

            for (TaskService task : tasksToDelete) {
                task.setSelected(false);

                try {
                    manager.RemoveTaskById(task.getTaskId());

                } catch (IOException e) {
                    System.out.println("Ocorreu um erro ao excluir o contato! (base json)");
                    e.printStackTrace();

                }

                TaskAppState.RemoveTask(task);
            }
        }

        Stage oldStage = (Stage) cancel.getScene().getWindow();
        oldStage.hide();

        Alert warning = new Alert(AlertType.WARNING);
        warning.setTitle("Tarefa removida");
        warning.setHeaderText("A tarefa foi removida com sucesso!");
        warning.setContentText("A tarefa foi excluida e seu calendário foi atualizado com sucesso!");
        warning.initModality(Modality.APPLICATION_MODAL);
        warning.showAndWait();

    }
}
