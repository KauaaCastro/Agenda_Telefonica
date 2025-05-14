package com.example.warnings;

import java.io.IOException;
import com.example.ContactsTable.ContactService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertViewController {

    @FXML
    private Button HomeScreen;

    @FXML
    private Button editContacts;

    @FXML
    private Button newExcludeContact;

    @FXML
    private Label show_BornDate;

    @FXML
    private Label show_Email;

    @FXML
    private Label show_Endress;

    @FXML
    private Label show_Gender;

    @FXML
    private Label show_Name;

    @FXML
    private Label show_NickName;

    @FXML
    private Label show_Relation;

    @FXML
    private Label show_TellNumber;

    @FXML
    private Label show_Work;

    private ContactService selectedContact;

    private Stage parentStage;

    public void setParentStage(Stage stage) {
        this.parentStage = stage;
    }

    @FXML
    public void ShowInformations(ContactService contacts) {
        show_Name.setText(contacts.getName());
        show_NickName.setText(contacts.getNickName());
        show_TellNumber.setText(contacts.getTellNumber());
        show_Email.setText(contacts.getEmailContact());
        show_BornDate.setText(contacts.getDateBirthday());
        show_Gender.setText(contacts.getGender());
        show_Work.setText(contacts.getWorkContact());
        show_Endress.setText(contacts.getEndressContact());
        show_Relation.setText(contacts.getRelationContact());

        this.selectedContact = contacts;

    }

    @FXML
    void GoToEditContacts(ActionEvent event) throws IOException {
        Stage oldStage = (Stage) HomeScreen.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Alerts/AlertEditScreen.fxml"));
        Parent root = loader.load();

        AlertEditController controller = loader.getController();
        controller.setContactToEdit(selectedContact);

        Stage stage = new Stage();
        stage.setTitle("Editar contato");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        oldStage.close();
    }

    @FXML
    public void GoToExcludeContact(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Alerts/AlertExclude.fxml"));
        Parent root = loader.load();

        AlertController controller = loader.getController();
        controller.viewToExclude(selectedContact);

        Stage alertStage = new Stage();
        alertStage.setTitle("Alerta!");
        alertStage.setScene(new Scene(root));
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.show();

        Stage currentStage = (Stage) newExcludeContact.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void ReturnViewScreen(ActionEvent event) {
        if (parentStage != null) {
            parentStage.show();
        }

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    // Controlador da tela AlertViewContacts(Add) tela chamada pelo AddContacts
    public void AddScreenShowInfo(String newName, String nick, String tell, String endress, String email,
            String relation, String work, String birthday, String gender) {

        show_Name.setText(newName);
        show_NickName.setText(nick);
        show_TellNumber.setText(tell);
        show_Endress.setText(endress);
        show_Email.setText(email);
        show_Relation.setText(relation);
        show_Work.setText(work);
        show_BornDate.setText(birthday);
        show_Gender.setText(gender);

    }
}
