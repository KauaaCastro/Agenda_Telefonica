package com.example.TaskTable;
//Armazena as tarefas criadas

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskAppState {

    private static final ObservableList<TaskService> tasks = FXCollections.observableArrayList();

    public static ObservableList<TaskService> getTasks() {
        return tasks;
    }

    public static void setTasks(ObservableList<TaskService> newTasks) {
        tasks.setAll(newTasks);
    }

    public static void addTask(TaskService task) {
        tasks.add(task);
    }

    public static void RemoveTask(TaskService taskId) {
        tasks.removeIf(task -> task.getTaskId().equals(taskId.getTaskId()));

        TaskContactState.getTaskContactRelations()
                .removeIf(relation -> relation.getTaskId().equals(taskId.getTaskId()));
    }
}
