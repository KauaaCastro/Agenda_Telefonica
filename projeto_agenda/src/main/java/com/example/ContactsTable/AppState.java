package com.example.ContactsTable;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppState {
        private static final ObservableList<ContactService> contacts = FXCollections.observableArrayList();
        private static final LocalStorageManager localStorageManager = new LocalStorageManager();

        static {
                List<ContactService> loaded = localStorageManager.LoadContact();
                contacts.addAll(loaded);
        }

        public static ObservableList<ContactService> getContacts() {
                return contacts;
        }

        public static void save() {
                localStorageManager.SaveContact(contacts);
        }

}
