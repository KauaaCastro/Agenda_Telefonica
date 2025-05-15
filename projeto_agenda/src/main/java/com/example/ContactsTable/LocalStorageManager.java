package com.example.ContactsTable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LocalStorageManager {
    private static final String localStorage = "projeto_agenda/src/main/lib/localStorage.json";

    private final ObjectMapper mapper;
    private final ObservableList<ContactService> contacts;

    public ObservableList<ContactService> getContacts() {
        return contacts;
    }

    public LocalStorageManager() {
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        contacts = FXCollections.observableArrayList();
        contacts.addAll(LoadContact());
    }

    public List<ContactService> LoadContact() {
        try {
            File file = new File(localStorage);

            if (!file.exists()) {
                ObservableList<ContactService> emptyList = FXCollections.observableArrayList();
                SaveContact(emptyList);
                return emptyList;
            }

            List<ContactService> loadedList = mapper.readValue(file, new TypeReference<List<ContactService>>() {
            });

            return FXCollections.observableArrayList(loadedList);

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void SaveContact(List<ContactService> contacts) {
        try {
            mapper.writeValue(new File(localStorage), contacts);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ocorreu um erro ao tentar utilizar a classe Save Contact em LocalStorageManager");

        }
    }

    public void LocalAddContacts(ContactService newContact) {
        contacts.add(newContact);
        SaveContact(contacts);
    }

    public void LocalRemoveContacts(ContactService contactRemoved) {
        contacts.remove(contactRemoved);
        SaveContact(contacts);
    }

    public void LocalContactEdit(ContactService oldContact, ContactService update) {
        oldContact.setName(update.getName());
        oldContact.setNickName(update.getNickName());
        oldContact.setTellNumber(update.getTellNumber());
        oldContact.setemail(update.getEmailContact());
        oldContact.setDateBirthday(update.getDateBirthday());
        oldContact.setGender(update.getGender());
        oldContact.setRelation(update.getRelationContact());
        oldContact.setWork(update.getWorkContact());
        oldContact.setEndress(update.getEndressContact());
    }
}
