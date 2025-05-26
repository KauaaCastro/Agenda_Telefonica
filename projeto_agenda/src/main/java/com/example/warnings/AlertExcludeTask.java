package com.example.warnings;

import java.util.List;

import com.example.TaskTable.TaskAppState;
import com.example.TaskTable.TaskService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AlertExcludeTask {

    @FXML
    private Label Alert_Menssage;

    @FXML
    private Button ExcludeConfirm;

    @FXML
    private Button cancel;

    private List<TaskService> tasksToDelete;

    public void setTasksToDelete(List<TaskService> tasks) {
        this.tasksToDelete = tasks;
        Alert_Menssage.setText("Você tem certeza que deseja excluir " + tasks.size() + " tarefa(s)?");
    }

    @FXML
    void CancelRemove(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void ConfirmationExclude(ActionEvent event) {
        if (tasksToDelete != null && !tasksToDelete.isEmpty()) {
            for (TaskService task : tasksToDelete) {
                task.setSelected(false);
                TaskAppState.RemoveTask(task);
            }
        }

        // Fecha a janela após a exclusão
        Stage stage = (Stage) ExcludeConfirm.getScene().getWindow();
        stage.close();
    }
}
