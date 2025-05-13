package com.example.warnings;

import com.example.ContactsTable.ContactService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AlertEditController {

    @FXML
    private Tab EditionPane;

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
    private ContactService contactToEdit;

    public void initialize() {
        if (contactToEdit != null) {
            setContactToEdit(contactToEdit);
        }
    }

    // Puxar o contato selecionado para edição

    public void setContactToEdit(ContactService contacts) {
        this.contactToEdit = contacts;

        edit_name.setText(contacts.getName());
        edit_nickName.setText(contacts.getNickName());
        edit_numberTell.setText(contacts.getTellNumber());
        edit_email.setText(contacts.getEmailContact());
        // atenção
        // Date picker vai aqui, coloca-lo manualmente posteriormente
        // atenção
        edit_work.setText(contacts.getWorkContact());
        edit_endress.setText(contacts.getEndressContact());
        edit_Relation.setText(contacts.getRelationContact());

        if (contacts.getGender() != null) {
            switch (contacts.getGender().toLowerCase()) {
                case "homem":
                    gender_homem.setSelected(true);
                    break;

                case "mulher":
                    gender_mulher.setSelected(true);
                    break;

                case "indefinido":
                    gender_Indef.setSelected(true);
                    break;
                default:
                    break;
            }
        }

    }

    // adquirir o conteúdo escrito dentro das text field (Após preenchida pelo user)
    @FXML
    void SaveEditContacts(ActionEvent event) {
        String editedName = edit_name.getText();
        String editedNick = edit_nickName.getText();
        String editedNumber = edit_numberTell.getText();

        String editedBirthday = (edit_datePicker.getValue() != null) ? edit_datePicker.getValue().toString() : "";
        String editedGender = "";
        RadioButton selectedRadio = (RadioButton) edit_gender.getSelectedToggle();

        if (selectedRadio != null) {
            editedGender = selectedRadio.getText();
        }

        String editedEmail = edit_email.getText();
        String editedRelation = edit_Relation.getText();
        String editedWork = edit_work.getText();
        String editedEndress = edit_endress.getText();

    }

    // função do botão de salvamento
    @FXML
    void SaveModify(ActionEvent event) {
        System.out.println(nameField);
        System.out.println(nickNameField);
        System.out.println(tellNumberField);
    }

    @FXML
    void ReturnHomeScreen(ActionEvent event) {
        Stage stage = (Stage) HomeScreen.getScene().getWindow();
        stage.close();
    }
}