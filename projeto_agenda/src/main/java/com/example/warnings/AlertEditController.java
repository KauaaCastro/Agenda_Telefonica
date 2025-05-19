package com.example.warnings;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import com.example.ContactsTable.ContactService;
import com.example.ContactsTable.LocalStorageManager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertEditController {

    @FXML
    private Button HomeScreen;

    @FXML
    private Tab VisualizationPane;

    @FXML
    private TextField edit_Relation;

    @FXML
    private DatePicker edit_datePicker;

    @FXML
    private TextField edit_email;

    @FXML
    private TextField edit_endress;

    @FXML
    private ToggleGroup edit_gender;

    @FXML
    private TextField edit_name;

    @FXML
    private TextField edit_nickName;

    @FXML
    private TextField edit_numberTell;

    @FXML
    private TextField edit_work;

    @FXML
    private RadioButton gender_Indef;

    @FXML
    private RadioButton gender_homem;

    @FXML
    private RadioButton gender_mulher;

    @FXML
    private Button saveEdit;

    @FXML
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private ContactService contactToEdit;

    @FXML
    private ContactService oldContact;

    public void initialize() {
        if (contactToEdit != null) {
            setContactToEdit(contactToEdit); // Linha 80 aprox.
        }
    }

    // Puxar o contato selecionado para edição e exibi-lo nos textField
    public void setContactToEdit(ContactService contacts) {
        this.contactToEdit = contacts;

        // Cria a cópia do contato original para oldContact
        this.oldContact = new ContactService(
                contacts.getName(),
                contacts.getNickName(),
                contacts.getTellNumber(),
                contacts.getEmailContact(),
                contacts.getDateBirthday(),
                contacts.getGender(),
                contacts.getRelationContact(),
                contacts.getWorkContact(),
                contacts.getEndressContact(),
                null, null);

        edit_name.setText(contacts.getName());
        edit_nickName.setText(contacts.getNickName());
        edit_numberTell.setText(contacts.getTellNumber());
        edit_email.setText(contacts.getEmailContact());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateStr = contacts.getDateBirthday();

        if (dateStr != null && !dateStr.trim().isEmpty()) {
            try {
                edit_datePicker.setValue(LocalDate.parse(dateStr.trim(), formatter));

            } catch (DateTimeParseException e) {
                System.out.println("Data inválida: " + dateStr);
                edit_datePicker.setValue(null);

            }
        } else {
            edit_datePicker.setValue(null);
        }

        if (contacts.getGender() != null) {
            switch (contacts.getGender().toLowerCase()) {
                case "masculino":
                    gender_homem.setSelected(true);
                    break;

                case "feminino":
                    gender_mulher.setSelected(true);
                    break;

                case "indefinido":
                    gender_Indef.setSelected(true);
                    break;
                default:
                    break;
            }

            edit_work.setText(contacts.getWorkContact());
            edit_endress.setText(contacts.getEndressContact());
            edit_Relation.setText(contacts.getRelationContact());

        }

    }

    // função do botão de salvamento
    @FXML
    void SaveModify(ActionEvent event) throws IOException {

        String verifyName = edit_name.getText();
        String verifyNickName = edit_nickName.getText();
        String verifyNumber = edit_numberTell.getText();

        Stage stage = (Stage) HomeScreen.getScene().getWindow();
        if (verifyName.isEmpty() || verifyNickName.isEmpty() || verifyNumber.isEmpty()) {
            stage.hide();

            Platform.runLater(() -> {
                Alert warning = new Alert(Alert.AlertType.ERROR);
                warning.setTitle("Edição cancelada!");
                warning.setHeaderText("Ocorreu um erro ao editar o contato, há campos vazios");
                warning.setContentText("Por favor verifique os campos: Nome, Apelido, telefone. Tente novamente!");
                warning.initModality(Modality.APPLICATION_MODAL);
                warning.showAndWait();

                stage.show();
            });

        } else {

            if (contactToEdit == null) {
                return;
            }

            contactToEdit.setName(edit_name.getText());
            contactToEdit.setNickName(edit_nickName.getText());
            contactToEdit.setTellNumber(edit_numberTell.getText());
            contactToEdit.setemail(edit_email.getText());

            String bornDate = (edit_datePicker.getValue() != null) ? edit_datePicker.getValue().format(dateFormatter)
                    : "";

            contactToEdit.setDateBirthday(bornDate);

            String gender = null;

            if (gender_homem.isSelected()) {
                gender = "Masculino";
            } else if (gender_mulher.isSelected()) {
                gender = "Feminino";
            } else if (gender_Indef.isSelected()) {
                gender = "Indefinido";
            } else if (gender == null) {
                Alert warning = new Alert(AlertType.CONFIRMATION);

                warning.setTitle("Confirmar edição?");
                warning.setHeaderText("O contato está sem genêro definido!");
                warning.setContentText("Caso não seja inserido será colocado como: Indefinido");
                warning.initModality(Modality.APPLICATION_MODAL);

                warning.getButtonTypes().clear();
                ButtonType insert = new ButtonType("Retornar e inserir", ButtonBar.ButtonData.OK_DONE);
                ButtonType newCancel = new ButtonType("Continuar mesmo assim", ButtonBar.ButtonData.CANCEL_CLOSE);
                warning.getButtonTypes().addAll(insert, newCancel);

                Optional<ButtonType> result = warning.showAndWait();

                if (result.isPresent() && result.get() != newCancel) {
                    return;
                }
            }

            contactToEdit.setGender(gender);
            contactToEdit.setWork(edit_work.getText());
            contactToEdit.setRelation(edit_Relation.getText());
            contactToEdit.setEndress(edit_endress.getText());

            if (oldContact != null && contactToEdit != null) {
                LocalStorageManager storage = new LocalStorageManager();
                storage.LocalContactEdit(oldContact, contactToEdit);

                System.out.println("Informações transferidas com sucesso!");

                System.out.println(oldContact);
                System.out.println(contactToEdit);
            } else {
                System.out.println("Ocorreu um erro ao transferir as informações");

                System.out.println();
                System.out.println(oldContact);
                System.out.println(contactToEdit);
            }

            Alert warning = new Alert(AlertType.INFORMATION);
            warning.setTitle("Editando contato...");
            warning.setHeaderText("Contato editado com sucesso!");
            warning.setContentText("Atualizando dados do contato...");
            warning.initModality(Modality.APPLICATION_MODAL);
            warning.show();

            stage.close();
        }
    }

    @FXML
    void ReturnHomeScreen() {
        Stage stage = (Stage) HomeScreen.getScene().getWindow();
        stage.close();
    }
}