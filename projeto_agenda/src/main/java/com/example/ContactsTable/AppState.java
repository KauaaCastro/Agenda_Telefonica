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
                contacts.addAll(new ContactService(
                                "Mariana Oliveira", "Mari", "Feminino", "", "11911223344",
                                "mariana.o@email.com", "Amiga", "Arquiteta", "Rua P", false),
                                new ContactService(
                                                "Gustavo Pereira", "Guga", "Masculino", "15/09/1990", "11955667788",
                                                "", "Colega", "", "Avenida Q", false),
                                new ContactService(
                                                "Fernanda Souza", "Fer", "Feminino", "02/06/1993", "11998877665",
                                                "fernanda.s@email.com", "", "Designer de Interiores", "", false),
                                new ContactService(
                                                "Lucas Costa", "Luquinhas", "Masculino", "", "11922334455",
                                                "", "Família", "", "Travessa R", false),
                                new ContactService(
                                                "Juliana Lima", "Ju", "Feminino", "27/11/1986", "11966554433",
                                                "juliana.l@email.com", "Trabalho", "", "Alameda S", false),
                                new ContactService(
                                                "Bruno Rocha", "Bru", "Masculino", "10/04/1995", "11933445566",
                                                "", "", "Analista de Marketing", "Praça T", false),
                                new ContactService(
                                                "Camila Fernandes", "Cami", "Feminino", "18/01/1999", "11977889900",
                                                "camila.f@email.com", "Vizinha", "", "", false),
                                new ContactService(
                                                "Daniel Carvalho", "Dani", "Masculino", "", "11944556677",
                                                "", "Amigo", "Desenvolvedor Front-end", "Rua U", false),
                                new ContactService(
                                                "Patrícia Almeida", "Paty", "Feminino", "09/08/1983", "11988990011",
                                                "patricia.a@email.com", "", "", "Avenida V", false),
                                new ContactService(
                                                "Eduardo Souza", "Dudu", "Masculino", "22/03/1991", "11912121212",
                                                "", "Colega", "Engenheiro Civil", "", false),
                                new ContactService(
                                                "Aline Costa", "Ali", "Feminino", "", "11956565656",
                                                "aline.c@email.com", "Família", "", "Travessa W", false),
                                new ContactService(
                                                "Marcelo Lima", "Celinho", "Masculino", "05/10/1988", "11997979797",
                                                "", "Trabalho", "Gerente Comercial", "", false),
                                new ContactService(
                                                "Renata Rocha", "Re", "Feminino", "14/07/1997", "11923232323",
                                                "renata.r@email.com", "", "Assistente Administrativo", "Alameda X",
                                                false),
                                new ContactService(
                                                "Fábio Fernandes", "Fabinho", "Masculino", "", "11968686868",
                                                "", "", "Técnico de Informática", "Praça Y", false),
                                new ContactService(
                                                "Letícia Carvalho", "Lê", "Feminino", "30/05/1994", "11934343434",
                                                "leticia.c@email.com", "Vizinha", "", "", false),
                                new ContactService(
                                                "Sérgio Almeida", "Serjão", "Masculino", "", "11971717171",
                                                "", "Amigo", "Analista de RH", "Rua Z", false),
                                new ContactService(
                                                "Cláudia Souza", "Cacau", "Feminino", "19/02/1985", "11945454545",
                                                "claudia.s@email.com", "", "", "Avenida AA", false),
                                new ContactService(
                                                "Roberto Costa", "Beto", "Masculino", "08/12/1992", "11989898989",
                                                "", "Colega", "Consultor", "", false),
                                new ContactService(
                                                "Isabel Lima", "Isa", "Feminino", "", "11910101010",
                                                "isabel.l@email.com", "Família", "", "Travessa BB", false),
                                new ContactService(
                                                "Vinícius Rocha", "Vini", "Masculino", "25/09/1989", "11950505050",
                                                "", "Trabalho", "Publicitário", "", false),

                                new ContactService(
                                                "Sofia Almeida", "Sofi", "Feminino", "28/07/1991", "11987654321",
                                                "sofia.a@email.com", "Amiga", "Analista de Sistemas", "Rua F", false),
                                new ContactService(
                                                "Pedro Fernandes", "Pedrinho", "Masculino", "10/02/1985", "11912345678",
                                                "pedro.f@email.com", "Colega", "Vendedor", "Avenida G", false),
                                new ContactService(
                                                "Laura Carvalho", "Lala", "Feminino", "05/12/1998", "11955551111",
                                                "laura.c@email.com", "Família", "Estudante", "Travessa H", false),
                                new ContactService(
                                                "Ricardo Oliveira", "Ric", "Masculino", "18/06/1982", "11922228888",
                                                "ricardo.o@email.com", "Trabalho", "Gerente de Projetos", "Alameda I",
                                                false),
                                new ContactService(
                                                "Beatriz Costa", "Bia", "Feminino", "23/04/1997", "11933337777",
                                                "beatriz.c@email.com", "Vizinha", "Marketing", "Praça J", false),
                                new ContactService(
                                                "Gabriel Rodrigues", "Gabi", "Masculino", "01/11/1999", "11944446666",
                                                "gabriel.r@email.com", "Amigo", "Músico", "Rua K", false),
                                new ContactService(
                                                "Isabela Pereira", "Isa", "Feminino", "14/08/1987", "11966663333",
                                                "isabela.p@email.com", "Colega", "Advogada", "Rua L", false),
                                new ContactService(
                                                "Rafael Souza", "Rafa", "Masculino", "29/01/1994", "11977772222",
                                                "rafael.s@email.com", "Família", "Professor", "Avenida M", false),
                                new ContactService(
                                                "Amanda Lima", "Amandinha", "Feminino", "07/03/1989", "11991919191",
                                                "amanda.l@email.com", "Trabalho", "Analista Financeiro", "Travessa N",
                                                false),
                                new ContactService(
                                                "Thiago Castro", "Thi", "Masculino", "12/07/1996", "11982828282",
                                                "thiago.c@email.com", "Vizinho", "Estudante de TI", "Alameda O", false),
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
