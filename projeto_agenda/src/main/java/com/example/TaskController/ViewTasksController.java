package com.example.TaskController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.example.ContactsTable.AppState;
import com.example.ContactsTable.ContactService;
import com.example.TaskTable.TaskContactRelation;
import com.example.TaskTable.TaskService;
import com.example.warnings.TaskDescriptionAlert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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

    @FXML
    void initialize() {
        table_ShowName.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_ShowTell.setCellValueFactory(new PropertyValueFactory<>("tellNumber"));
        table_showNick.setCellValueFactory(new PropertyValueFactory<>("nickName"));
    }

    void setTask(TaskService task, TaskContactRelation relation) {
        List<String> relatedContactIds = relation.getContactId();

        List<ContactService> contactsFiltered = AppState.getContacts().stream()
                .filter(c -> relatedContactIds.contains(c.getId()))
                .collect(Collectors.toList());

        ObservableList<ContactService> observableFiltered = FXCollections.observableArrayList(contactsFiltered);

        showContactTable.setItems(observableFiltered);

        show_eventName.setText(task.getTaskName());
        show_eventEndress.setText(task.getTaskEndress());
        show_Hoursevent.setText(task.getTaskTime());
        show_EventDate.setText(task.getTaskDate());

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

    }

    @FXML
    void GoToExcludeTask(ActionEvent event) {

    }

    @FXML
    void ReturnScreen(ActionEvent event) {
        Stage stage = (Stage) view_return.getScene().getWindow();
        stage.close();
    }

    @FXML
    void descriptionReturn(ActionEvent event) {
        Stage stage = (Stage) description_Return.getScene().getWindow();
        stage.close();
    }
}