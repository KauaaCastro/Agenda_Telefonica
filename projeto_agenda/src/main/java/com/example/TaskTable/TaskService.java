package com.example.TaskTable;

import java.util.UUID;
import javafx.beans.property.SimpleStringProperty;

public class TaskService {
    private final SimpleStringProperty id = new SimpleStringProperty();
    private final SimpleStringProperty taskName = new SimpleStringProperty();
    private final SimpleStringProperty taskDate = new SimpleStringProperty();
    private final SimpleStringProperty taskTime = new SimpleStringProperty();

    public TaskService(String id, String taskName, String taskDate, String taskTime) {
        this.id.set(id != null ? id : UUID.randomUUID().toString());
        this.taskName.set(taskName);
        this.taskDate.set(taskDate);
        this.taskTime.set(taskTime);
    }

    // Getter and Setters
    public String getTaskId() {
        return id.get();
    }

    public void setTaskId(String id) {
        this.id.set(id);
    }

    public String getTaskName() {
        return taskName.get();
    }

    public void setTaskName(String taskName) {
        this.taskName.set(taskName);
    }

    public String getTaskDate() {
        return taskDate.get();
    }

    public void setTaskDate(String date) {
        this.taskDate.set(date);
    }

    public String getTaskTime() {
        return taskTime.get();
    }

    public void setTaskTime(String time) {
        this.taskTime.set(time);
    }
}
