package com.example.warnings;

import com.example.ContactsTable.ContactService;
import com.example.ContactsTable.LocalStorageManager;
import java.util.List;
import com.example.ContactsTable.AppState;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

//Controlador dos Alertas
public class AlertController {

    @FXML
    private Stage currentStage;

    @FXML
    private boolean returnToView;

    @FXML
    private Button Alert_Ok; // Alert Comum

    @FXML
    private Label Alert_Menssage; // Alert Comum

    @FXML
    private Button ExcludeConfirm; // (Remove Contact)

    @FXML
    private Button cancel; // (Remove Contact)

    private List<ContactService> contactsToDelete; // Lista em vez de um só
    // (Remove Contact)

    @FXML
    void CancelRemove(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();

        stage.close();

        Platform.runLater(() -> {
            Alert warning = new Alert(Alert.AlertType.INFORMATION);
            warning.setTitle("Aviso!");
            warning.setHeaderText("Nenhum contato foi removido!");
            warning.setContentText("Cancelando operação de exclusão de contato! Nenhum contato removido.");
            warning.showAndWait();
        });

    }

    public void viewToExclude(ContactService contacts) {
        this.contactsToDelete = List.of(contacts);
    }

    @FXML
    void ConfirmationExclude(ActionEvent event) {
        if (contactsToDelete != null) {
            AppState.getContacts().removeAll(contactsToDelete);

            LocalStorageManager storage = new LocalStorageManager();
            storage.LocalDeleteContacts(contactsToDelete);

            try {
                Stage stage = (Stage) ExcludeConfirm.getScene().getWindow();

                stage.hide();

                Platform.runLater(() -> {
                    Alert infos = new Alert(Alert.AlertType.INFORMATION);
                    infos.setTitle("Aviso!");
                    infos.setHeaderText("Exclusão de contato");
                    infos.setContentText("Contato removido com sucesso! Retornando à tela inicial");
                    infos.initModality(Modality.APPLICATION_MODAL);

                    infos.showAndWait();

                    stage.close(); // Fecha a janela após o alerta ser fechado
                });

            } catch (NullPointerException e) {
                System.out.println("Ocorreu um erro ao exibir a confirmação da remoção do contato");
            }
        }
    }

    public void setDeleteContact(List<ContactService> contacts) {
        this.contactsToDelete = contacts;
    }

    // Confirmar pop up
    @FXML
    void Confirmation(ActionEvent event) {
        Stage stage = (Stage) Alert_Ok.getScene().getWindow();
        stage.close();
    }

}
