package com.example.grafiskpersonapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;

public class PersonApp extends Application {
    String[] listeValg = {"navn", "postnummer", "kundenummer"};
    ComboBox<String> listeSortering;
    Button knappNyPerson, knappBlankUt, knappSlettPerson;
    TextArea tekstMedlem;
    TextField tekstInnData;
    ArrayList<Person> list = new ArrayList<Person>();

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage vindu) throws Exception {
        FlowPane panel = new FlowPane();
        panel.setAlignment(Pos.CENTER);

        //Lager listen av valg og legger dem til
        Label lblSortering = new Label("Velg sortering:");
        listeSortering = new ComboBox<>();
        listeSortering.getItems().addAll(listeValg);
        listeSortering.getSelectionModel().selectFirst();
        listeSortering.setOnAction(this::behandleKlikk);
        panel.getChildren().addAll(lblSortering, listeSortering);

        // Tekstområde
        tekstMedlem = new TextArea();
        tekstMedlem.setPrefColumnCount(30);
        tekstMedlem.setPrefRowCount(10);
        panel.getChildren().add(tekstMedlem);

        // Input felt og knapper
        tekstInnData = new TextField();
        tekstInnData.setPrefColumnCount(30);
        tekstInnData.setText("Nytt medlem format: 'fornavn etternavn', 'adresse', 'kundeNr'");

        knappNyPerson = new Button("Nytt medlem");
        knappNyPerson.setOnAction(this::behandleKlikk);

        knappBlankUt = new Button("Blank Ut");
        knappBlankUt.setOnAction(this::behandleKlikk);

        knappSlettPerson = new Button("Slett person");
        knappSlettPerson.setOnAction(this::behandleKlikk);

        panel.getChildren().addAll(tekstInnData, knappNyPerson, knappBlankUt, knappSlettPerson);

        list.add(new Person("Richard Gjelland", "Spondalen", 3));
        list.add(new Person("Tor Håkon", "Serendal", 2));
        list.add(new Person("Teo Meland", "Birkenstokk", 6));
        list.add(new Person("Arild Henrik", "Alverud", 5));

        Scene scene = new Scene(panel, 400, 300);
        vindu.setScene(scene);
        vindu.setTitle("Medlemsregister");
        vindu.setResizable(false);
        vindu.show();
    }

    public void behandleKlikk(ActionEvent e) {
        if (e.getSource() == listeSortering) {
            int nrSort = listeSortering.getSelectionModel().getSelectedIndex();
            switch (nrSort) {
                case 0 -> sorteringNavn(list);
                case 1 -> sorteringPostNr(list);
                case 2 -> sorteringKundenr(list);
            }
        } else if (e.getSource() == knappNyPerson) {
            nyPerson();
        } else if (e.getSource() == knappBlankUt) {
            blankUt();
        } else if (e.getSource() == knappSlettPerson) {
            slettPerson();
        }
    }

    private void blankUt() {
        tekstMedlem.setText("");
        tekstInnData.setText("");
    }

    private void nyPerson() {
        String input = tekstInnData.getText();
        String[] parts = input.split(",");
        if (parts.length == 3) {
            String navn = parts[0].trim();
            String adresse = parts[1].trim();
            try {
                int kundeNr = Integer.parseInt(parts[2].trim());
                list.add(new Person(navn, adresse, kundeNr));
                tekstInnData.setText("");
                tekstMedlem.setText("Nytt medlem lagt til: " + navn);
            } catch (NumberFormatException e) {
                tekstMedlem.setText("Ugyldig kundeNr format. Vennligst bruk et gyldig tall.");
            }
        } else {
            tekstMedlem.setText("Feil format. Vennligst bruk formatet: 'fornavn + etternavn', 'adresse', 'kundeNr'");
        }
    }

    private void slettPerson() {
        String input = tekstInnData.getText().trim();
        String[] parts = input.split("[,;\\s]+");

        if (parts.length == 3) {
            String navn = (parts[0] + " " + parts[1]).toLowerCase().trim();
            try {
                int kundeNr = Integer.parseInt(parts[2].trim());
                boolean personExists = list.removeIf(person ->
                        person.getNavn().toLowerCase().equals(navn) && person.getKundenummer() == kundeNr);
                if (personExists) {
                    tekstMedlem.setText("Medlem slettet: " + navn);
                    tekstInnData.setText("");
                } else {
                    tekstMedlem.setText("Fant ikke medlem: " + navn);
                }
            } catch (NumberFormatException e) {
                tekstMedlem.setText("Ugyldig kundeNr format. Vennligst bruk et gyldig tall.");
            }
        } else {
            tekstMedlem.setText("Feil format. Vennligst bruk formatet: 'fornavn etternavn kundenr'");
        }
    }

    private void sorteringKundenr(ArrayList<Person> list) {
        if (tekstMedlem.getText() != null) {
            tekstMedlem.setText("");
        }
        Collections.sort(list);
        StringBuilder ut = new StringBuilder();
        for (Person person : list) {
            ut.append(person.getNavn()).append(" ").append(person.getAdresse()).append(" ").append(person.getKundenummer()).append("\n");
        }
        tekstMedlem.setText(ut.toString());
    }

    private void sorteringPostNr(ArrayList<Person> list) {
        StringBuilder ut = new StringBuilder();
        SorterAdresse sorterAdresse = new SorterAdresse();
        Collections.sort(list, sorterAdresse);
        for (Person person : list) {
            ut.append(person.getNavn()).append(" ").append(person.getAdresse()).append(" ").append(person.getKundenummer()).append("\n");
        }
        tekstMedlem.setText(ut.toString());
    }

    private void sorteringNavn(ArrayList<Person> list) {
        StringBuilder ut = new StringBuilder();
        SorterNavn sorterNavn = new SorterNavn();
        Collections.sort(list, sorterNavn);
        for (Person person : list) {
            ut.append(person.getNavn()).append(" ").append(person.getAdresse()).append(" ").append(person.getKundenummer()).append("\n");
        }
        tekstMedlem.setText(ut.toString());
    }
}