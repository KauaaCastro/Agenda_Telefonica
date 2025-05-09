package com.example;

import com.example.ContactsTable.AppState;
import com.example.ContactsTable.ContactService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    private ToggleGroup pro_gender;

    @FXML
    private TextField pro_NickName;

    @FXML
    private TextField pro_Relation;

    @FXML
    private DatePicker pro_datePicker;

    @FXML
    private TextField pro_endress;

    @FXML
    private TextField pro_extra;

    @FXML
    private TextField pro_name;

    @FXML
    private TextField pro_numberTell;

    @FXML
    private TextField pro_work;

    @FXML
    private Button saveContact;

    @FXML
    void ReturnHomeScreen(ActionEvent event) {
        Stage stage = (Stage) HomeScreen.getScene().getWindow();
        stage.close();
    }

    @FXML
    void SaveContact(ActionEvent event) {
        String newName = pro_name.getText();
        String nick = pro_NickName.getText();
        String tell = pro_numberTell.getText();
        String birthday = (pro_datePicker.getValue() != null) ? pro_datePicker.getValue().toString() : "";

        String gender = "";
        RadioButton selectedRadio = (RadioButton) pro_gender.getSelectedToggle();

        if (selectedRadio != null) {
            gender = selectedRadio.getText();
        }

        ContactService contacts = new ContactService(newName, nick, gender, birthday, tell);

        AppState.getContacts().add(contacts);

        pro_name.clear();
        pro_NickName.clear();
        pro_numberTell.clear();
        pro_datePicker.setValue(null);
        pro_gender.selectToggle(null);

        Stage stage = (Stage) saveContact.getScene().getWindow();
        stage.close();
    }
}