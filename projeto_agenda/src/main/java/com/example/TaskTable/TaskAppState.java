package com.example.TaskTable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskAppState {

    private static final ObservableList<TaskService> tasks = FXCollections.observableArrayList();

    public static ObservableList<TaskService> getTasks() {
        return tasks;
    }

    public static void addTask(TaskService task) {
        tasks.add(task);
    }
}
