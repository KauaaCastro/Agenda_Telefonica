package com.example.TaskController;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.example.TaskTable.TaskAppState;
import com.example.TaskTable.TaskContactRelation;
import com.example.TaskTable.TaskContactState;
import com.example.TaskTable.TaskService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    private List<String> selectedContactIds = new ArrayList<>();

    private boolean firstClick = false;

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
            int oldMP = change.getCaretPosition();

            change.setText(formatted);
            change.setRange(0, oldText.length());

            // Pular os dois pontos
            javafx.application.Platform.runLater(() -> {
                if (oldMP == 2) {
                    pro_hoursTime.positionCaret(3);
                }
            });

            return change;
        });

        pro_hoursTime.setTextFormatter(formatter);

        pro_hoursTime.setOnMouseClicked(event -> {
            if (!firstClick) {
                pro_hoursTime.positionCaret(0);
                this.firstClick = true;

            }
        });
    }

    // Salvar tarefas
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
            TaskService newTask = new TaskService(null, name, formattedDate, hoursTime, endress, description, false);

            newTask.setTaskName(name);
            newTask.setEndress(endress);
            newTask.setTaskTime(hoursTime);
            newTask.setTaskDescription(description);
            newTask.setTaskDate(formattedDate);

            TaskAppState.addTask(newTask);
            TaskContactRelation relation = new TaskContactRelation(newTask.getTaskId(), selectedContactIds);
            TaskContactState.addRelation(relation);

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

            System.out.println();
            System.out.println(newTask.getTaskName());
            System.out.println(newTask.getTaskDate());
            System.out.println(newTask.getTaskDescription());
            System.out.println(newTask.getTaskTime());
            System.out.println(newTask.getTaskEndress());
            System.out.println(newTask.getTaskId());
            System.out.println();
            System.out.println("Contatos selecionados IDs:");
            for (String contactId : selectedContactIds) {
                System.out.println(contactId);
            }

            System.out.println();
            System.out.println(Eventdate);

            stage.close();
        }
    }

    // Ir para a lista de contatos selecionados
    @FXML
    void GoToSelectContact(ActionEvent event) {
        Stage oldStage = (Stage) pro_name.getScene().getWindow();
        oldStage.hide();

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/TaskScreen/SelectContactTask.fxml"));
            Parent root = loader.load();

            SelectCnttListController controller = loader.getController();
            controller.setAddTaskController(this);

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

    // Retornar
    @FXML
    void Return(ActionEvent event) {
        Stage stage = (Stage) pro_name.getScene().getWindow();
        stage.close();
    }

    public void setSelectedContactIDs(List<String> selectedContactIds) {
        this.selectedContactIds = selectedContactIds;

        System.out.println("Contatos selecionados: " + selectedContactIds);
    }
}
