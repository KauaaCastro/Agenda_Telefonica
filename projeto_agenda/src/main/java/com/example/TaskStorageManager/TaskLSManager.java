package com.example.TaskStorageManager;

import com.example.TaskTable.TaskContactRelation;
import com.example.TaskTable.TaskContactState;
import com.example.TaskTable.TaskService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TaskLSManager {
    private static final String TASKS_FILE = "projeto_agenda/src/main/java/com/example/Storage/localTaskStorage.json";
    private static final String RELATIONS_FILE = "projeto_agenda/src/main/java/com/example/Storage/localRelationStorage.json";

    // Remover supressWarnings para casos de teste e erros
    @SuppressWarnings("unused")
    private final ObservableList<TaskService> tasks = FXCollections.observableArrayList();
    @SuppressWarnings("unused")
    private final ObservableList<TaskContactRelation> relations = FXCollections.observableArrayList();

    private final ObjectMapper objectMapper;

    public TaskLSManager() {
        objectMapper = new ObjectMapper();

    }

    // Salvar ou atualizar uma lista de tarefas no arquivo JSON
    public void saveTasks(List<TaskService> newTasks) throws IOException {
        List<TaskService> existingTasks = loadTasks();

        for (TaskService newTask : newTasks) {
            boolean updated = false;

            for (int i = 0; i < existingTasks.size(); i++) {
                if (existingTasks.get(i).getTaskId().equals(newTask.getTaskId())) {
                    existingTasks.set(i, newTask); // Atualiza
                    updated = true;
                    break;
                }
            }

            if (!updated) {
                existingTasks.add(newTask); // Adiciona nova
            }
        }

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(TASKS_FILE), existingTasks);
    }

    // Carregar lista de tarefas
    public static List<TaskService> loadTasksFromJson() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            File file = new File(TASKS_FILE);

            if (file.exists()) {
                return Arrays.asList(mapper.readValue(file, TaskService[].class));
            }

        } catch (IOException e) {
            e.printStackTrace();

        }

        return new ArrayList<>();
    }

    // Salvar ou atualizar uma relação na lista e no arquivo JSON
    public void saveOrUpdateRelation(List<TaskContactRelation> newRelations) throws IOException {
        List<TaskContactRelation> existingRelations = loadRelations();

        for (TaskContactRelation newRelation : newRelations) {
            boolean updated = false;

            for (int i = 0; i < existingRelations.size(); i++) {
                if (existingRelations.get(i).getTaskId().equals(newRelation.getTaskId())) {
                    existingRelations.set(i, newRelation);
                    updated = true;

                    break;

                }
            }

            if (!updated) {
                existingRelations.add(newRelation);

            }
        }

        saveRelations(existingRelations);
    }

    // Método para deletar Tarefas e suas Relações
    public void RemoveTaskById(String taskId) throws IOException {

        // Remove a lista de tarefas selecionadas
        List<TaskService> selectedTask = loadTasks();
        boolean taskRemoved = selectedTask.removeIf(tasks -> tasks.getTaskId().equals(taskId));

        System.out.println("\033\143");
        System.out.println("Tamanho da lista de tarefa: " + selectedTask.size());
        System.out.println("Lista de tarefas: " + selectedTask);

        if (taskRemoved) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(TASKS_FILE), selectedTask);

        }

        // Remove a lista de relações
        List<TaskContactRelation> selectedRelations = loadRelations();
        boolean relationRemove = selectedRelations.removeIf(relations -> relations.getTaskId().equals(taskId));

        System.out.println("Tamanho da lista de relações: " + selectedRelations.size());
        System.out.println("Lista de relações: " + selectedRelations);

        if (relationRemove) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(RELATIONS_FILE), selectedRelations);

        }
    }

    // Edita tarefas:
    public void EditTask(TaskService updatedTask) throws IOException {
        List<TaskService> allTasks = loadTasks();
        boolean confirmation = false;

        for (int i = 0; i < allTasks.size(); i++) {

            if (allTasks.get(i).getTaskId().equals(updatedTask.getTaskId())) {
                System.out.println("\033\143");
                allTasks.set(i, updatedTask);
                confirmation = true;
                System.out.println("Tarefa encontrada!");
                System.out.println(updatedTask.getJsonId());
                break;

            }

        }

        if (!confirmation) {
            System.out.println("Tarefa não encontrada");
        }

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(TASKS_FILE), allTasks);
    }

    // Métodos de carregamento (carregar tarefas e relações)
    public static List<TaskService> loadAllTasks(File file) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return List.of(mapper.readValue(file, TaskService[].class));

        } catch (IOException e) {
            e.printStackTrace();
            return List.of();

        }
    }

    public static List<TaskContactRelation> loadAllRelations(File file) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return List.of(mapper.readValue(file, TaskContactRelation[].class));

        } catch (IOException e) {
            e.printStackTrace();
            return List.of();

        }
    }

    public static List<TaskContactRelation> loadRelationsFromJson() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            File file = new File(RELATIONS_FILE);

            if (file.exists()) {
                CollectionType listType = mapper.getTypeFactory()
                        .constructCollectionType(List.class, TaskContactRelation.class);

                List<TaskContactRelation> relations = mapper.readValue(file, listType);

                TaskContactState.clearRelations();
                TaskContactState.getTaskContactRelations().addAll(relations);

                System.out.println("Relações carregadas do JSON com sucesso: " + relations.size());

                return relations;

            } else {
                System.out.println("Arquivo de relações não encontrado: " + RELATIONS_FILE);

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar relações do JSON");

        }

        return Collections.emptyList();
    }

    // Filtro de identificação de itens (contatos e tarefas)
    public static Optional<TaskService> findTaskById(List<TaskService> tasks, String taskId) {
        return tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst();

    }

    public static Optional<TaskContactRelation> findRelationByTaskId(List<TaskContactRelation> relations,
            String taskId) {
        return relations.stream()
                .filter(r -> r.getTaskId().equals(taskId))
                .findFirst();

    }

    // Método auxiliar --> Salvar nova task
    public void saveRelations(List<TaskContactRelation> relations) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(RELATIONS_FILE), relations);

    }

    // Método auxiliar --> saveTasks
    public List<TaskService> loadTasks() throws IOException {
        File file = new File(TASKS_FILE);

        if (!file.exists()) {
            return new ArrayList<>();

        }

        return objectMapper.readValue(file, new TypeReference<List<TaskService>>() {

        });
    }

    // Método auxiliar --> loadRelations
    public List<TaskContactRelation> loadRelations() throws IOException {
        File file = new File(RELATIONS_FILE);

        if (!file.exists()) {
            return new ArrayList<>();

        }
        return objectMapper.readValue(file, new TypeReference<List<TaskContactRelation>>() {

        });
    }
}
