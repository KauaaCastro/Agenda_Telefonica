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

        static {
                contacts.addAll(
                                new ContactService(
                                                "João Silva", "João", "Masculino", "01/01/1990", "11999999999",
                                                "joao@email.com", "Amigo", "Engenheiro", "Rua A", false),
                                new ContactService(
                                                "Maria Souza", "Maria", "Feminino", "15/03/1992", "11888888888",
                                                "maria@email.com", "Família", "Professora", "Rua B", false),
                                new ContactService(
                                                "Carlos Lima", "Carlos", "Masculino", "22/05/1988", "11777777777",
                                                "carlos@email.com", "Trabalho", "Designer", "Rua C", false),
                                new ContactService(
                                                "Ana Paula", "Ana", "Feminino", "09/09/1995", "11666666666",
                                                "ana@email.com", "Vizinha", "Dentista", "Rua D", false),
                                new ContactService(
                                                "Lucas Rocha", "Lucas", "Masculino", "30/10/1993", "11555555555",
                                                "lucas@email.com", "Amigo", "Desenvolvedor", "Rua E", false));
        }
}
