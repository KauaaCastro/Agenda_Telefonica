package com.example.ContactsController;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;
import com.example.ContactsTable.AppState;
import com.example.ContactsTable.ContactService;
import com.example.ContactsTable.LocalStorageManager;
import com.example.warnings.AlertViewController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddContactsController {

    @FXML
    private Button HomeScreen;

    @FXML
    private Button ShowInfos;

    @FXML
    private RadioButton gender_Indef;

    @FXML
    private RadioButton gender_homem;

    @FXML
    private RadioButton gender_mulher;

    @FXML
    private TextField pro_NickName;

    @FXML
    private TextField pro_Relation;

    @FXML
    private DatePicker pro_datePicker;

    @FXML
    private TextField pro_email;

    @FXML
    private TextField pro_endress;

    @FXML
    private ToggleGroup pro_gender;

    @FXML
    private TextField pro_name;

    @FXML
    private TextField pro_numberTell;

    @FXML
    private TextField pro_work;

    @FXML
    private Button saveContact;

    LocalStorageManager storage = new LocalStorageManager();

    @SuppressWarnings("unused")
    private boolean firstClick = false;

    @FXML
    void initialize() {

        @SuppressWarnings("unused")
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getText();

            if (newText.matches("[0-9]*")) {
                return change;

            }
            return null;

        };

        pro_numberTell.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                String raw = pro_numberTell.getText().replaceAll("[^\\d]", "");

                if (raw.length() == 11) {
                    String formatted = "(" + raw.substring(0, 2) + ") " +
                            raw.substring(2, 7) + "-" +
                            raw.substring(7, 11);
                    pro_numberTell.setText(formatted);

                } else if (raw.length() > 0 && raw.length() < 11) {
                    pro_numberTell.clear();

                    Alert error = new Alert(AlertType.ERROR);
                    error.setTitle("Inválido!");
                    error.setHeaderText("Você digitou um número de telefone inválido!");
                    error.setContentText("Digite novamente somente com números!");
                    error.initModality(Modality.APPLICATION_MODAL);
                    error.showAndWait();

                    System.out.println("Número inválido ou incompleto: " + raw);

                } else if (raw.length() == 0) {
                    pro_numberTell.clear();
                }
            }
        });
    }

    @FXML
    void SaveContact(ActionEvent event) {

        // Campos Obrigatórios
        String newName = pro_name.getText();
        String nick = pro_NickName.getText();
        String tell = pro_numberTell.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String birthday = (pro_datePicker.getValue() != null) ? pro_datePicker.getValue().format(formatter) : "";

        String gender = "";
        RadioButton selectedRadio = (RadioButton) pro_gender.getSelectedToggle();

        if (selectedRadio != null) {
            gender = selectedRadio.getText();
        }

        // Campos opcionais
        String email = pro_email.getText();
        String relation = pro_Relation.getText();
        String work = pro_work.getText();
        String endress = pro_endress.getText();

        if (newName.isEmpty() || nick.isEmpty() || tell.isEmpty()) {
            Stage stage = (Stage) HomeScreen.getScene().getWindow();

            stage.hide();

            Platform.runLater(() -> {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Erro de Validação");
                alerta.setHeaderText("Campos obrigatórios não preenchidos");
                alerta.setContentText("Preencha nome, telefone e apelido antes de adicionar.");
                alerta.initOwner(stage);
                alerta.initModality(Modality.APPLICATION_MODAL);

                alerta.showAndWait();

                stage.show();
            });

            return;
        }

        Stage stage = (Stage) HomeScreen.getScene().getWindow();

        stage.hide(); // Esconde a janela principal

        Platform.runLater(() -> {
            Alert warning = new Alert(Alert.AlertType.INFORMATION);
            warning.setTitle("Salvando contato...");
            warning.setHeaderText("Contato salvo!");
            warning.setContentText("O contato foi salvo na sua lista de contatos com os dados inseridos!");
            warning.showAndWait();

            stage.close(); // Mostrar a janela principal de volta depois que o alerta fechar, se quiser
        });

        ContactService contacts = new ContactService(newName, nick, gender, birthday, tell, email, relation, work,
                endress, false, null);

        storage.LocalAddContacts(contacts);
        AppState.getContacts().add(contacts);

        pro_name.clear();
        pro_NickName.clear();
        pro_numberTell.clear();
        pro_endress.clear();
        pro_email.clear();
        pro_Relation.clear();
        pro_work.clear();
        pro_datePicker.setValue(null);
        pro_gender.selectToggle(null);

        stage.close();
    }

    @FXML
    void ViewInformation(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/Alerts/AlertViewContacts(Add).fxml"));
            Parent root = loader.load();

            // visualização dos itens antes de finalizar o preenchimento dos campos
            AlertViewController controller = loader.getController();

            String newName = pro_name.getText();
            String nick = pro_NickName.getText();
            String tell = pro_numberTell.getText();
            String birthday = (pro_datePicker.getValue() != null) ? pro_datePicker.getValue().toString() : "";
            String email = pro_email.getText();
            String relation = pro_Relation.getText();
            String work = pro_work.getText();
            String endress = pro_endress.getText();
            String gender = "";
            RadioButton selectedRadio = (RadioButton) pro_gender.getSelectedToggle();

            if (selectedRadio != null) {
                gender = selectedRadio.getText();
            }

            controller.AddScreenShowInfo(
                    newName,
                    nick,
                    tell,
                    endress,
                    email,
                    relation,
                    work,
                    birthday,
                    gender);

            // Inicializar a janela criada anteriormente trocando entre si
            // (AlertsViewController)
            controller.setParentStage((Stage) ShowInfos.getScene().getWindow());

            Stage alertStage = new Stage();
            alertStage.setTitle("Confira os dados:");
            alertStage.setScene(new Scene(root));

            Stage stage = (Stage) ShowInfos.getScene().getWindow();
            stage.hide();

            alertStage.initModality(Modality.APPLICATION_MODAL);
            alertStage.show();

            stage.close();

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao tentar exibir o alerta!");
            System.out.println("Código do erro: ");
            System.out.println();
            e.printStackTrace();
        }
    }

    @FXML
    void ReturnHomeScreen(ActionEvent event) {
        Stage stage = (Stage) HomeScreen.getScene().getWindow();
        stage.close();
    }
}