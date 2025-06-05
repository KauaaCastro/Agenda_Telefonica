package com.example.TaskTable;
//Apenas armazena e representa as tarefas!

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class TaskService {
    private final SimpleStringProperty id = new SimpleStringProperty();
    private final SimpleStringProperty taskName = new SimpleStringProperty();
    private final SimpleStringProperty taskDate = new SimpleStringProperty();
    private final SimpleStringProperty taskTime = new SimpleStringProperty();
    private final SimpleStringProperty taskEndress = new SimpleStringProperty();
    private final SimpleStringProperty taskDescription = new SimpleStringProperty();
    private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);

    public TaskService(String id, String taskName, String taskDate, String taskTime, String taskEndress,
            String taskDescription, boolean selected) {
        this.id.set(id != null ? id : UUID.randomUUID().toString());
        this.taskName.set(taskName);
        this.taskDate.set(taskDate);
        this.taskTime.set(taskTime);
        this.taskEndress.set(taskEndress);
        this.taskDescription.set(taskDescription);
        this.selected.set(false);
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

    public String getTaskEndress() {
        return taskEndress.get();
    }

    public void setEndress(String taskEndress) {
        this.taskEndress.set(taskEndress);
    }

    public String getTaskDescription() {
        return taskDescription.get();
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription.set(taskDescription);
    }

    public boolean getSelected() {
        return selected.get();
    }

    public void setSelected(Boolean selected) {
        this.selected.set(selected);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    // Construtores para armazenamento Json
    public TaskService() {

    }

    @JsonProperty("id")
    public String getJsonId() {
        return getTaskId();
    }

    @JsonProperty("id")
    public void setJsonId(String id) {
        setTaskId(id);
    }

    @JsonProperty("taskName")
    public String getJsonTaskName() {
        return getTaskName();
    }

    @JsonProperty("taskName")
    public void setJsonTaskName(String taskName) {
        setTaskName(taskName);
    }

    @JsonProperty("taskDate")
    public String getJsonTaskDate() {
        return getTaskDate();
    }

    @JsonProperty("taskDate")
    public void setJsonTaskDate(String date) {
        setTaskDate(date);
    }

    @JsonProperty("taskTime")
    public String getJsonTaskTime() {
        return getTaskTime();
    }

    @JsonProperty("taskTime")
    public void setJsonTaskTime(String time) {
        setTaskTime(time);
    }

    @JsonProperty("taskEndress")
    public String getJsonTaskEndress() {
        return getTaskEndress();
    }

    @JsonProperty("taskEndress")
    public void setJsonTaskEndress(String endress) {
        setEndress(endress);
    }

    @JsonProperty("taskDescription")
    public String getJsonTaskDescription() {
        return getTaskDescription();
    }

    @JsonProperty("taskDescription")
    public void setJsonTaskDescription(String description) {
        setTaskDescription(description);
    }

    @JsonProperty("selected")
    public boolean isJsonSelected() {
        return getSelected();
    }

    @JsonProperty("selected")
    public void setJsonSelected(boolean selected) {
        setSelected(selected);
    }
}
