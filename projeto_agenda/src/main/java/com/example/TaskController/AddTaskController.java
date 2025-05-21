package com.example.TaskController;

import java.time.format.DateTimeFormatter;

import com.example.TaskTable.TaskAppState;
import com.example.TaskTable.TaskService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddTaskController {

    @FXML
    private Button HomeScreen;

    @FXML
    private Button SaveTask;

    @FXML
    private Button ShowInfos;

    @FXML
    private TextArea pro_Description;

    @FXML
    private DatePicker pro_EventDate;

    @FXML
    private TextField pro_endress;

    @FXML
    private TextField pro_hoursTime;

    @FXML
    private TextField pro_name;

    @FXML
    private Button pro_pushContacts;

    @FXML
    public void initialize() {
        pro_hoursTime.setText("00:00");

        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            String oldText = change.getControlText();
            String newText = change.getControlNewText();

            String raw = newText.replaceAll("[^\\d]", "");

            if (raw.length() > 4) {
                raw = raw.substring(0, 4);
            }

            while (raw.length() < 4) {
                raw = raw + "0";
            }

            String formatted = raw.substring(0, 2) + ":" + raw.substring(2, 4);

            change.setText(formatted);
            change.setRange(0, oldText.length());
            return change;
        });

        pro_hoursTime.setTextFormatter(formatter);
    }

    @FXML
    void GoToSelectContact(ActionEvent event) {

    }

    @FXML
    void SaveTask(ActionEvent event) {
        String name = pro_name.getText();
        String endress = pro_endress.getText();
        String hoursTime = pro_hoursTime.getText();
        String description = pro_Description.getText();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String Eventdate = pro_EventDate.getValue() != null ? pro_EventDate.getValue().format(formatter) : "";

        if (name.isEmpty() || Eventdate.isEmpty()) {
            Stage stage = (Stage) pro_name.getScene().getWindow();
            stage.hide();

            Alert error = new Alert(AlertType.ERROR);
            error.setTitle("Ocorreu um erro");
            error.setHeaderText("Ocorreu um erro ao salvar a tarefa");
            error.setContentText("Campos obrigatórios não preenchidos! Nome e Data");
            error.initModality(Modality.APPLICATION_MODAL);
            error.showAndWait();

            stage.show();

        } else {
            String formattedDate = pro_EventDate.getValue().toString();
            TaskService newTask = new TaskService(null, name, formattedDate, hoursTime);
            newTask.setTaskTime(hoursTime);
            newTask.setTaskDate(formattedDate);
            newTask.setTaskName(name);

            TaskAppState.getTasks().add(newTask);

            pro_name.clear();
            pro_endress.clear();
            pro_hoursTime.clear();
            pro_Description.clear();
            pro_EventDate.setValue(null);
            Stage stage = (Stage) pro_name.getScene().getWindow();
            stage.hide();

            Alert information = new Alert(AlertType.INFORMATION);
            information.setTitle("Adicionando...");
            information.setHeaderText("Tarefa adicionada para o dia: " + Eventdate);
            information.setContentText("Tarefa adicionada com sucesso!");
            information.initModality(Modality.APPLICATION_MODAL);
            information.showAndWait();

            System.out.println(name);
            System.out.println(endress);
            System.out.println(hoursTime);
            System.out.println(description);
            System.out.println(Eventdate);

            stage.close();
        }

    }

    @FXML
    void VIewTaskInfo(ActionEvent event) {

    }

    @FXML
    void Return(ActionEvent event) {
        Stage stage = (Stage) pro_name.getScene().getWindow();
        stage.close();
    }
}
