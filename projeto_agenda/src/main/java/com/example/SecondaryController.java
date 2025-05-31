package com.example;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import com.example.JavaStyle.ManualStyle;
import com.example.TaskController.ListTaskController;
import com.example.TaskTable.TaskAppState;
import com.example.TaskTable.TaskService;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SecondaryController {

    @FXML
    private ColumnConstraints sunday;

    @FXML
    private Button pro_showContacts;

    @FXML
    private ColumnConstraints monday;

    @FXML
    private ColumnConstraints Tuesday;

    @FXML
    private ColumnConstraints Wednesday;

    @FXML
    private ColumnConstraints thursday;

    @FXML
    private ColumnConstraints friday;

    @FXML
    private ColumnConstraints saturday;

    @FXML
    private GridPane pro_Calendar;

    @FXML
    private Button pro_RemoveTasks;

    @FXML
    private Label pro_YearDate;

    @FXML
    private Button pro_addTasks;

    @FXML
    private Button pro_next;

    @FXML
    private Button pro_previous;

    @FXML
    private Button pro_showTasks;

    private YearMonth currentYearMonth;

    @FXML
    void initialize() {
        currentYearMonth = YearMonth.now();
        FillCalendar();
    }

    // Preencher calendário
    @SuppressWarnings("static-access")
    @FXML
    void FillCalendar() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");
        String formatted = currentYearMonth.format(formatter);

        pro_YearDate.setText(formatted);

        // Criação dos botões com base nos dias
        LocalDate firstDay = currentYearMonth.atDay(1); // Responsavel por setar qual será o primeiro dia
        int weekDays = firstDay.getDayOfWeek().getValue() % 7; // será Responsavel por exibir os botões nos dias
        int monthDays = currentYearMonth.lengthOfMonth(); // Carregar a quantidade de dias que o mês possui
        int day = 1;

        pro_Calendar.getChildren().removeIf(node -> { // Remover as linhas exceto as do dia da semana
            Integer row = pro_Calendar.getRowIndex(node);
            return row != null && row > 0;
        });

        for (int i = 1; i <= 6; i++) {
            for (int x = 0; x < 7; x++) {
                if (i == 1 && x < weekDays) {
                    continue;
                }

                if (day > monthDays) {
                    break;
                }

                // Estilização e funcionamento dos botões
                Button bnt_Days = new Button(String.valueOf(day));
                ManualStyle.ButtonDateStyle(bnt_Days);
                pro_Calendar.add(bnt_Days, x, i);

                int finalDay = day;

                bnt_Days.setOnAction(e -> {
                    String fullDate = LocalDate.of(currentYearMonth.getYear(), currentYearMonth.getMonth(), finalDay)
                            .toString();
                    GoToListTask(fullDate);
                });

                day++;
            }
        }
    }

    // Lista de tarefas
    @FXML
    private void GoToListTask(String newDate) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/TaskScreen/ListTask.fxml"));
            Parent root = loader.load();

            ListTaskController controller = loader.getController();
            LocalDate date = LocalDate.parse(newDate);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateTitle = date.format(formatter);
            ObservableList<TaskService> allTasks = TaskAppState.getTasks();

            controller.loadTasksForDate(date, allTasks);

            Stage stage = new Stage();
            stage.setTitle(dateTitle);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao abrir ListTask.fxml");
        }
    }

    // Atualizar calendário baseado nas datas (já formatadas)
    @FXML
    void updateCalendar() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        pro_YearDate.setText(currentYearMonth.format(formatter));
        FillCalendar();
    }

    // Ir para o próximo mês
    @FXML
    void NextMonth(ActionEvent event) {
        currentYearMonth = currentYearMonth.plusMonths(1);
        updateCalendar();
    }

    // Ir para o mês anterior
    @FXML
    void PreviousMonth(ActionEvent event) {
        currentYearMonth = currentYearMonth.minusMonths(1);
        updateCalendar();
    }

    // Adicionar tarefas
    @FXML
    void AddTasks(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/TaskScreen/AddTaskScreen.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Nova tarefa");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao tentar abrir a tela de adição");
            e.printStackTrace();
        }

    }

    // Remover tarefas
    @FXML
    void RemoveTasks(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/TaskScreen/TaskDeleteList.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Tela de remoção");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            System.out.println();
            e.printStackTrace();
        }
    }

    // Visualizar Tarefas
    @FXML
    void ViewTasks(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/TaskScreen/ListAllTask.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Todas as tarefas");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            System.out.println("----------------------------------------------");
            e.printStackTrace();
            System.out.println("Ocorreu um erro ao iniciar a lista de tarefas (global)");

        }
    }

    // Ir para a tela de contatos
    @FXML
    void GoToContactScreen(ActionEvent event) {
        try {
            System.out.println("Abrindo tela de contatos!");
            Parent secondView = FXMLLoader
                    .load(getClass().getResource("/com/example/HomeScreen.fxml"));
            Scene secondScene = new Scene(secondView);

            // Para retornar a janela para a tela inicial
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(secondScene);
            currentStage.show();

        } catch (IOException e) {
            System.out.println("\033\143");
            e.printStackTrace();
            System.out.println("Ocorreu um erro ao abrir a tela de contatos");
        }
    }
}
