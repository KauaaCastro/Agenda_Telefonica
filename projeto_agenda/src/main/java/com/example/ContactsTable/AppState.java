package com.example.ContactsTable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppState {
    private static final ObservableList<ContactService> contacts = FXCollections.observableArrayList();

    public static ObservableList<ContactService> getContacts() {
        return contacts;
    }

    public static void AddContacts(ContactService contact) {
        contacts.add(contact);
    }
}
