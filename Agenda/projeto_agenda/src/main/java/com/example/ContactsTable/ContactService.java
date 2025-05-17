package com.example.ContactsTable;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class ContactService {

    private SimpleStringProperty id = new SimpleStringProperty();
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
            String emailContact, String relationContact, String workContact, String endressContact, Boolean selected,
            String id) {
        this.id = new SimpleStringProperty(id != null ? id : UUID.randomUUID().toString());
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

    // classe para o Json ser atualizado de maneira correta
    public ContactService() {

    }

    // Getters and Setters

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public SimpleStringProperty idProperty() {
        return id;
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

    // Métodos auxiliares para executar o arquivoJson

    @JsonProperty("name")
    public String getJsonName() {
        return getName();
    }

    @JsonProperty("name")
    public void setJsonName(String name) {
        setName(name);
    }

    @JsonProperty("nickName")
    public String getJsonNickName() {
        return getNickName();
    }

    @JsonProperty("nickName")
    public void setJsonNickName(String nickName) {
        setNickName(nickName);
    }

    @JsonProperty("gender")
    public String getJsonGender() {
        return getGender();
    }

    @JsonProperty("gender")
    public void setJsonGender(String gender) {
        setGender(gender);
    }

    @JsonProperty("dateBirthday")
    public String getJsonDateBirthday() {
        return getDateBirthday();
    }

    @JsonProperty("dateBirthday")
    public void setJsonDateBirthday(String date) {
        setDateBirthday(date);
    }

    @JsonProperty("tellNumber")
    public String getJsonTellNumber() {
        return getTellNumber();
    }

    @JsonProperty("tellNumber")
    public void setJsonTellNumber(String tell) {
        setTellNumber(tell);
    }

    @JsonProperty("emailContact")
    public String getJsonEmail() {
        return getEmailContact();
    }

    @JsonProperty("emailContact")
    public void setJsonEmail(String email) {
        setemail(email);
    }

    @JsonProperty("relationContact")
    public String getJsonRelation() {
        return getRelationContact();
    }

    @JsonProperty("relationContact")
    public void setJsonRelation(String relation) {
        setRelation(relation);
    }

    @JsonProperty("workContact")
    public String getJsonWork() {
        return getWorkContact();
    }

    @JsonProperty("workContact")
    public void setJsonWork(String work) {
        setWork(work);
    }

    @JsonProperty("endressContact")
    public String getJsonEndress() {
        return getEndressContact();
    }

    @JsonProperty("endressContact")
    public void setJsonEndress(String endress) {
        setEndress(endress);
    }

    @JsonProperty("selected")
    public boolean isJsonSelected() {
        return getSelected();
    }

    @JsonProperty("selected")
    public void setJsonSelected(boolean selected) {
        setSelected(selected);
    }
}
