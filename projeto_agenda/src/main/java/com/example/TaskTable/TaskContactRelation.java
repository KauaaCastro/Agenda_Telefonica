package com.example.TaskTable;
//Ponte das tarefas e dos contatos!

import java.util.List;

public class TaskContactRelation {
    private String taskId;
    private List<String> contactId;

    public TaskContactRelation(String taskId, List<String> contactId) {
        this.taskId = taskId;
        this.contactId = contactId;
    }

    public String getTaskId() {
        return taskId;
    }

    public List<String> getContactId() {
        return contactId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setContactId(List<String> contactId) {
        this.contactId = contactId;
    }
}
