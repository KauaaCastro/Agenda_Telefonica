package com.example.TaskTable;
//Armazena as relações entre contatos e tarefas

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskContactState {

    private static final ObservableList<TaskContactRelation> TaskContactRelations = FXCollections.observableArrayList();

    public static ObservableList<TaskContactRelation> getTaskContactRelations() {
        return TaskContactRelations;
    }

    // adicionar relações e tarefas
    public static void addRelation(TaskContactRelation relation) {
        TaskContactRelations.add(relation);
    }

    // Remover tarefas e relações
    public static void removeRelation(TaskContactRelation relation) {
        TaskContactRelations.remove(relation);
    }

    public static void clearRelations() {
        TaskContactRelations.clear();
    }

    // editar informações entre contatos e tarefas
    public static void updateRelation(TaskContactRelation updatedRelation) {
        for (int i = 0; i < TaskContactRelations.size(); i++) {
            if (TaskContactRelations.get(i).getTaskId().equals(updatedRelation.getTaskId())) {
                TaskContactRelations.set(i, updatedRelation);
                return;
            }
        }
        TaskContactRelations.add(updatedRelation);

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

    public static void setRelations(ObservableList<TaskContactRelation> newRelations) {
        TaskContactRelations.setAll(newRelations);
    }
}
