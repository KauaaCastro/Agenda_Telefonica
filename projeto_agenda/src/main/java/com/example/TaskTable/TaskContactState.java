package com.example.TaskTable;
//Armazena as relações entre contatos e tarefas

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskContactState {

    private static final ObservableList<TaskContactRelation> TaskContactRelations = FXCollections.observableArrayList();

    public static ObservableList<TaskContactRelation> getTaskContactRelations() {
        return TaskContactRelations;
    }

    public static void addRelation(TaskContactRelation relation) {
        TaskContactRelations.add(relation);
    }

    public static void removeRelation(TaskContactRelation relation) {
        TaskContactRelations.remove(relation);
    }

    public static void clearRelations() {
        TaskContactRelations.clear();
    }

    // Busca as relações através dos ids!
    public static ObservableList<TaskContactRelation> getRelationsByTaskId(String taskId) {

        ObservableList<TaskContactRelation> filtered = FXCollections.observableArrayList();

        for (TaskContactRelation relation : TaskContactRelations) {
            if (relation.getTaskId().equals(taskId)) {
                filtered.add(relation);
            }
        }

        return filtered;
    }

    public static ObservableList<TaskContactRelation> getRelationsByContactId(String contactId) {

        ObservableList<TaskContactRelation> filtered = FXCollections.observableArrayList();

        for (TaskContactRelation relation : TaskContactRelations) {
            if (relation.getContactId().contains(contactId)) {
                filtered.add(relation);
            }
        }

        return filtered;
    }
}
