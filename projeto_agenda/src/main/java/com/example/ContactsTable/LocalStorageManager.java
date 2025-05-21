package com.example.ContactsTable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LocalStorageManager {
    private static final String localStorage = "projeto_agenda/src/main/java/com/example/Storage/localStorage.json";

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

            // Carrega os dados do JSON
            List<ContactService> loadedList = mapper.readValue(file, new TypeReference<List<ContactService>>() {
            });

            boolean updated = false;

            // Garante que todos os contatos tenham um ID
            for (ContactService contact : loadedList) {
                if (contact.getId() == null || contact.getId().isEmpty()) {
                    contact.setId(UUID.randomUUID().toString());
                    updated = true;
                }
            }

            // Se algum ID foi adicionado, salva de volta no JSON
            if (updated) {
                SaveContact(FXCollections.observableArrayList(loadedList));
            }

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

    public void LocalDeleteContacts(List<ContactService> contactsToDelete) {
        if (contactsToDelete == null || contactsToDelete.isEmpty())
            return;

        ObservableList<ContactService> contactList = AppState.getContacts();

        for (ContactService toDelete : contactsToDelete) {
            contactList.removeIf(existing -> existing.getId().equals(toDelete.getId()));
        }

        SaveContact(contactList);
    }

    public void LocalContactEdit(ContactService oldContact, ContactService update) {
        // Encontra o contato antigo pelo id
        Optional<ContactService> oldContactOpt = contacts.stream()
                .filter(c -> c.getId().equals(update.getId()))
                .findFirst();

        if (oldContactOpt.isPresent()) {
            ContactService editContact = oldContactOpt.get();

            // Atualiza os campos do contato antigo com os dados do contato atualizado
            editContact.setName(update.getName());
            editContact.setNickName(update.getNickName());
            editContact.setTellNumber(update.getTellNumber());
            editContact.setemail(update.getEmailContact());
            editContact.setDateBirthday(update.getDateBirthday());
            editContact.setGender(update.getGender());
            editContact.setRelation(update.getRelationContact());
            editContact.setWork(update.getWorkContact());
            editContact.setEndress(update.getEndressContact());

            SaveContact(contacts);
        } else {
            System.out.println("Contato com id " + update.getId() + " não encontrado.");
        }
    }
}
