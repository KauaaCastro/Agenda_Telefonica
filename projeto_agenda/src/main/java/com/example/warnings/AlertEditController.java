package com.example.warnings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.example.ContactsTable.ContactService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

    public void initialize() {
        if (contactToEdit != null) {
            setContactToEdit(contactToEdit);
        }
    }

    // Puxar o contato selecionado para edição e exibi-lo nos textField
    public void setContactToEdit(ContactService contacts) {
        this.contactToEdit = contacts;

        edit_name.setText(contacts.getName());
        edit_nickName.setText(contacts.getNickName());
        edit_numberTell.setText(contacts.getTellNumber());
        edit_email.setText(contacts.getEmailContact());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateStr = contacts.getDateBirthday();

        if (dateStr != null && !dateStr.isEmpty()) {
            edit_datePicker.setValue(LocalDate.parse(dateStr, formatter));
        } else {
            edit_datePicker.setValue(null);
        }

        edit_work.setText(contacts.getWorkContact());
        edit_endress.setText(contacts.getEndressContact());
        edit_Relation.setText(contacts.getRelationContact());

        if (contacts.getGender() != null) {
            switch (contacts.getGender().toLowerCase()) {
                case "homem":
                    gender_homem.setSelected(false);
                    break;

                case "mulher":
                    gender_mulher.setSelected(false);
                    break;

                case "indefinido":
                    gender_Indef.setSelected(false);
                    break;
                default:
                    break;
            }
        }

    }

    // função do botão de salvamento
    @FXML
    void SaveModify(ActionEvent event) {
        Stage stage = (Stage) HomeScreen.getScene().getWindow();
        String verifyName = edit_name.getText();
        String verifyNickName = edit_nickName.getText();
        String verifyNumber = edit_numberTell.getText();

        if (verifyName.isEmpty() || verifyNickName.isEmpty() || verifyNumber.isEmpty()) {
            stage.hide();

            Alert warning = new Alert(AlertType.ERROR);
            warning.setTitle("Edição cancelada!");
            warning.setHeaderText("Ocorreu um erro ao editar o contato, há campos vazios");
            warning.setContentText("Por favor verifique os campos: Nome, Apelido, telefone. Tente novamente!");
            warning.initModality(Modality.APPLICATION_MODAL);
            warning.showAndWait();

            stage.show();
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

            RadioButton selecRadioButton = (RadioButton) edit_gender.getSelectedToggle();
            if (selecRadioButton != null) {
                contactToEdit.setGender(selecRadioButton.getText());

            }

            contactToEdit.setWork(edit_work.getText());
            contactToEdit.setRelation(edit_Relation.getText());

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