package com.example.TaskController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.example.TaskTable.TaskService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TextField search_ListTask;

    @FXML
    private LocalDate selectedDate;

    @FXML
    private ObservableList<TaskService> showList;

    @FXML
    public void initialize() {
        table_Name.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        table_Date.setCellValueFactory(new PropertyValueFactory<>("taskDate"));
        date_Hours.setCellValueFactory(new PropertyValueFactory<>("taskTime"));
    }

    public void loadTasksForDate(LocalDate selectedDate, ObservableList<TaskService> taskList) {
        this.showList = taskList;

        List<TaskService> filtered = showList.stream()
                .filter(task -> task.getTaskDate().equals(selectedDate.toString()))
                .collect(Collectors.toList());

        Table_ListTask.setItems(FXCollections.observableArrayList(filtered));
    }
}
