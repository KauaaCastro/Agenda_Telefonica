package com.example.ContactsTable;

import javafx.beans.property.SimpleStringProperty;

public class ContactService {

    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty nickName = new SimpleStringProperty();
    private final SimpleStringProperty gender = new SimpleStringProperty();
    private final SimpleStringProperty dateBirthday = new SimpleStringProperty(); // Transformar em 3 ints diferentes
    private final SimpleStringProperty tellNumber = new SimpleStringProperty();

    public ContactService(String name, String nickName, String gender, String dateBirthday, String tellNumber) {
        this.name.set(name);
        this.nickName.set(nickName);
        this.gender.set(gender);
        this.dateBirthday.set(dateBirthday);
        this.tellNumber.set(tellNumber);
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
}
