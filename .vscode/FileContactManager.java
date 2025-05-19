package com.example.ContactsTable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileContactManager {

    static final String localContact = "projeto_agenda/src/main/lib/LocalStorage.txt";

    // Carregamento dos contatos para sincronização
    public static List<ContactService> LoadContacts() {
        List<ContactService> contacts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(localContact))) {
            String lines;

            reader.readLine();

            while ((lines = reader.readLine()) != null) {
                String[] parts = lines.split("\\|");

                if (parts.length >= 9) {
                    ContactService contact = new ContactService(
                            // 0 nome, 1 apelido, 5 genero, 4 idade, 2 telefone, 3 email, 6 relação, 7
                            // trabalho, 8 endereço
                            parts[0].trim(), parts[1].trim(), parts[5].trim(), parts[4].trim(), parts[2].trim(),
                            parts[3].trim(), parts[6].trim(), parts[7].trim(), parts[8].trim(), false);

                    contacts.add(contact);
                }
            }
        } catch (IOException e) {
            System.out.println("\nAdicione um contato para criar o arquivo LocalStorage");
        }

        return contacts;
    }

    // Sincronização dos contatos
    public static void loadAndSyncAppState() {
        List<ContactService> contacts = LoadContacts();
        AppState.getContacts().clear();
        AppState.getContacts().addAll(contacts);
    }

    // Formatação dos contatos
    public static String FormatContact(ContactService ct) {
        return String.join(" | ", ct.getName(), ct.getNickName(), ct.getTellNumber(), ct.getEmailContact(),
                ct.getDateBirthday(), ct.getGender(), ct.getRelationContact(), ct.getWorkContact(),
                ct.getEndressContact(),

                String.valueOf(ct.selectedProperty()));
    }

    // Criar arquivos
    public static void CreateFile() {
        File archieve = new File(localContact);

        try {
            if (archieve.createNewFile()) {

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(archieve))) {
                    writer.write(
                            "Nome | Apelido | Telefone | Email | Data de Nascimento | Genero |  Relacao | Trabalho | Endereco");
                    writer.newLine();
                    System.out.println("Arquivo criado com cabeçalho: " + archieve.getAbsolutePath());
                }
            } else {
                System.out.println("Arquivo já existe: " + archieve.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Salvamento dos contatos
    public static void SaveLocalContact(ContactService contacts) {
        File verifyFile = new File(localContact);

        if (verifyFile.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(localContact, true))) {
                writer.write(FormatContact(contacts));
                writer.newLine();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println();
                System.out.println("Ocorreu um erro ao salvar o contato em LocalStorage.txt");
            }
        } else {
            CreateFile();
            SaveLocalContact(contacts);
        }
    }
}
