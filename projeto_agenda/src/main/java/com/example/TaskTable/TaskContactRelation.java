package com.example.TaskTable;
//Ponte das tarefas e dos contatos!

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

public class TaskContactRelation {
    private final SimpleStringProperty taskId = new SimpleStringProperty();
    private final ListProperty<String> contactId = new SimpleListProperty<>(FXCollections.observableArrayList());

    // Construtor principal
    public TaskContactRelation(String taskId, List<String> contactId) {
        this.taskId.set(taskId);
        if (contactId != null) {
            this.contactId.set(FXCollections.observableArrayList(contactId));
        }
    }

    // Construtor vazio para Jackson
    public TaskContactRelation() {
    }

    // Getters e setters com propriedades

    public String getTaskId() {
        return taskId.get();
    }

    public void setTaskId(String taskId) {
        this.taskId.set(taskId);
    }

    public SimpleStringProperty taskIdProperty() {
        return taskId;
    }

    public List<String> getContactId() {
        return contactId.get();
    }

    public void setContactId(List<String> contactId) {
        this.contactId.set(FXCollections.observableArrayList(contactId));
    }

    public ListProperty<String> contactIdProperty() {
        return contactId;
    }

    // Anotações para Jackson JSON

    @JsonProperty("taskId")
    public String getJsonTaskId() {
        return getTaskId();
    }

    @JsonProperty("taskId")
    public void setJsonTaskId(String taskId) {
        setTaskId(taskId);
    }

    @JsonProperty("contactId")
    public List<String> getJsonContactId() {
        return getContactId();
    }

    @JsonProperty("contactId")
    public void setJsonContactId(List<String> contactId) {
        setContactId(contactId);
    }
}
