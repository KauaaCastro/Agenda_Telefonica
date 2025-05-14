package com.example.ContactsTable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class ContactService {

    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty nickName = new SimpleStringProperty();
    private final SimpleStringProperty gender = new SimpleStringProperty();
    private final SimpleStringProperty dateBirthday = new SimpleStringProperty();
    private final SimpleStringProperty tellNumber = new SimpleStringProperty();
    private final SimpleStringProperty emailContact = new SimpleStringProperty();
    private final SimpleStringProperty relationContact = new SimpleStringProperty();
    private final SimpleStringProperty workContact = new SimpleStringProperty();
    private final SimpleStringProperty endressContact = new SimpleStringProperty();

    private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);

    public ContactService(String name, String nickName, String gender, String dateBirthday, String tellNumber,
            String emailContact, String relationContact, String workContact, String endressContact, Boolean selected) {
        this.name.set(name);
        this.nickName.set(nickName);
        this.gender.set(gender);
        this.dateBirthday.set(dateBirthday);
        this.tellNumber.set(tellNumber);
        this.emailContact.set(emailContact);
        this.relationContact.set(relationContact);
        this.workContact.set(workContact);
        this.endressContact.set(endressContact);

        this.selected.set(false);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getNickName() {
        return nickName.get();
    }

    public void setNickName(String nickName) {
        this.nickName.set(nickName);
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getDateBirthday() {
        return dateBirthday.get();
    }

    public void setDateBirthday(String dateBirthday) {
        this.dateBirthday.set(dateBirthday);
    }

    public String getTellNumber() {
        return tellNumber.get();
    }

    public void setTellNumber(String tellNumber) {
        this.tellNumber.set(tellNumber);
    }

    public String getEmailContact() {
        return emailContact.get();
    }

    public void setemail(String emailContact) {
        this.emailContact.set(emailContact);
    }

    public String getRelationContact() {
        return relationContact.get();
    }

    public void setRelation(String relationContact) {
        this.relationContact.set(relationContact);
    }

    public String getWorkContact() {
        return workContact.get();
    }

    public void setWork(String workContact) {
        this.workContact.set(workContact);
    }

    public String getEndressContact() {
        return endressContact.get();
    }

    public void setEndress(String endressContact) {
        this.endressContact.set(endressContact);
    }

    public boolean getSelected() {
        return selected.get();
    }

    public void setSelected(Boolean selected) {
        this.selected.set(selected);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }
}
