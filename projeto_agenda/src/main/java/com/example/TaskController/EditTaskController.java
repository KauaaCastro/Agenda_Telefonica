package com.example.TaskController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javafx.util.StringConverter;

import com.example.TaskTable.TaskService;
import com.example.TaskTable.TaskContactRelation;
import com.example.TaskTable.TaskContactState;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditTaskController {

    @FXML
    private Button HomeScreen;

    @FXML
    private Button SaveEdit;

    @FXML
    private Button ShowInfos;

    @FXML
    private TextArea edit_Description;

    @FXML
    private DatePicker edit_EventDate;

    @FXML
    private TextField edit_endress;

    @FXML
    private TextField edit_hoursTime;

    @FXML
    private TextField edit_name;

    @FXML
    private Button edit_pushContacts;

    private TaskService task;

    private List<String> selectedContactIds = new ArrayList<>();

    @FXML
    void initialize() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        edit_EventDate.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                } else {
                    return null;
                }
            }
        });
    }

    // Tela de seleção de contatos
    @FXML
    void GoToSelectContact(ActionEvent event) {
        Stage oldStage = (Stage) edit_name.getScene().getWindow();
        oldStage.hide();

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/TaskScreen/SelectContactTask.fxml"));
            Parent root = loader.load();

            SelectCnttListController controller = loader.getController();
            controller.setEditTaskConroller(this);

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

    // Exibir informações antigas
    public void setTaskShow(TaskService task) {
        this.task = task;
        loadTask(task);

    }

    void loadTask(TaskService task) {
        edit_name.setText(task.getTaskName());
        edit_endress.setText(task.getTaskEndress());
        edit_hoursTime.setText(task.getTaskTime());

        // Conversão de ISO para BR
        try {
            LocalDate date = LocalDate.parse(task.getTaskDate()); // Conversão direta do ISO para LocalDate
            edit_EventDate.setValue(date);

        } catch (DateTimeParseException e) {
            e.printStackTrace();
            System.out.println("Ocorreu um erro ao tentar exibir a data");
        }
    }

    // Salvar edições
    @FXML
    void SaveEdit(ActionEvent event) {
        String eventName = edit_name.getText();
        String eventEndress = edit_endress.getText();
        String eventTime = edit_hoursTime.getText();
        String description = edit_Description.getText();
        LocalDate eventDate = edit_EventDate.getValue();
        String formattedDate = (eventDate != null) ? eventDate.toString() : "";

        if (eventName.isEmpty() || formattedDate.isEmpty()) {
            Stage stage = (Stage) edit_name.getScene().getWindow();
            stage.hide();

            Platform.runLater(() -> {
                Alert error = new Alert(AlertType.ERROR);
                error.setTitle("Erro ao salvar");
                error.setHeaderText("Campos obrigatórios não preenchidos");
                error.setContentText("Por favor, preencha o nome e a data.");
                error.initModality(Modality.APPLICATION_MODAL);
                error.showAndWait();
            });

            stage.show();
            return;
        }

        task.setTaskName(eventName);
        task.setEndress(eventEndress);
        task.setTaskDescription(description);
        task.setTaskTime(eventTime);
        task.setTaskDate(formattedDate);

        TaskContactRelation relation = new TaskContactRelation(task.getTaskId(), selectedContactIds);
        TaskContactState.updateRelation(relation);

        System.out.println("\033\143");
        System.out.println("Tarefa atualizada:");
        System.out.println("Nome: " + task.getTaskName());
        System.out.println("Endereço: " + task.getTaskEndress());
        System.out.println("Hora: " + task.getTaskTime());
        System.out.println("Descrição: " + task.getTaskDescription());
        System.out.println("Data: " + task.getTaskDate());
        System.out.println("Contatos: " + relation);

        Stage oldStage = (Stage) edit_name.getScene().getWindow();
        oldStage.hide();

        Platform.runLater(() -> {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Edição concluída...");
            info.setHeaderText("Tarefa atualizada com sucesso");
            info.setContentText("As alterações foram salvas.");
            info.initModality(Modality.APPLICATION_MODAL);
            info.showAndWait();

            oldStage.close();
        });

    }

    // Visualizar informações
    @FXML
    void VIewTaskInfo(ActionEvent event) {

    }

    // Retornar para a tela anterior
    @FXML
    void Return(ActionEvent event) {
        Stage stage = (Stage) edit_name.getScene().getWindow();
        stage.close();

    }

    // Seleção de contatos:
    public void setSelectedContactIDs(List<String> selectedContactIds) {
        this.selectedContactIds = selectedContactIds;

        System.out.println("Contatos selecionados: " + selectedContactIds);

    }

}
