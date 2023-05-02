package com.example.madarcic_sostaric7;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.util.Datoteke;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.OptionalLong;

public class UnosNovogIspitaController {

    @FXML
    private ComboBox<Predmet> predmetIspitaComboBox;
    @FXML
    private ComboBox<Student> studentIspitaComboBox;
    @FXML
    private TextField ocjenaIspitaTextField;
    @FXML
    private TextField nazivZgradeIspitaTextField;
    @FXML
    private TextField nazivDvoraneIspitaTextField;
    @FXML
    private TextField datumIVrijemeIspitaTextField;

    public void initialize(){

        List<Student> popisStudenata = null;
        List<Predmet> popisPredmeta = null;
        try {
            popisStudenata = BazaPodataka.dohvatiStudenteIzBaze();
            popisPredmeta = BazaPodataka.dohvatiPredmeteIzBaze();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObservableList<Predmet> predmetObservableList = FXCollections.observableList(popisPredmeta);
        predmetIspitaComboBox.setItems(predmetObservableList);
        predmetIspitaComboBox.getSelectionModel().select(0);

        ObservableList<Student> studentObservableList = FXCollections.observableList(popisStudenata);
        studentIspitaComboBox.setItems(studentObservableList);
        studentIspitaComboBox.getSelectionModel().select(0);


    }

    public void spremiIspit() {

        Predmet predmetIspita = predmetIspitaComboBox.getValue();
        Student studentIspita = studentIspitaComboBox.getValue();
        String ocjenaIspita = ocjenaIspitaTextField.getText();
        String nazivZgrade = nazivZgradeIspitaTextField.getText();
        String nazivDvorane = nazivDvoraneIspitaTextField.getText();
        String datumIVrijeme = datumIVrijemeIspitaTextField.getText();


        if(ocjenaIspita.isBlank() == true || nazivZgrade.isBlank() == true ||
                nazivDvorane.isBlank() == true || datumIVrijeme.isBlank() == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Došlo je do pogreške!");
            alert.setContentText("Nijedno polje ne smije ostati prazno!");

            alert.showAndWait();
        }

        else if(ocjenaIspita.isBlank() == false && nazivZgrade.isBlank() == false &&
                nazivDvorane.isBlank() == false && datumIVrijeme.isBlank() == false) {

            OptionalLong maksimalniID = IzbornikController.getListaIspita().stream()
                    .mapToLong(ispit -> ispit.getId()).max();

            OptionalLong maksimalniIDZaDvorane = IzbornikController.getListaIspita().stream()
                    .mapToLong(ispit -> ispit.getDvorana().id()).max();

            Integer ocjena = Integer.parseInt(ocjenaIspita);
            Ocjena ocjenaZaIspit = Datoteke.dohvatiOcjenu(ocjena);
            Dvorana dvorana = new Dvorana(maksimalniIDZaDvorane.getAsLong() + 1, nazivZgrade, nazivDvorane);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm");
            LocalDateTime parsiraniDatumIVrijeme = LocalDateTime.parse(datumIVrijeme, dtf);

            Ispit noviIspit = new Ispit(maksimalniID.getAsLong() + 1, predmetIspita, studentIspita, ocjenaZaIspit,
                    parsiraniDatumIVrijeme, dvorana);

            IzbornikController.getListaIspita().add(noviIspit);

        //    IzbornikController.napraviNoviTekstFileZaIspite();
            try {
                BazaPodataka.dodajNoviIspitUBazu(noviIspit);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Spremanje ispita");
            alert.setHeaderText("Uspješno dodan novi ispit!");
            alert.setContentText("Ispit " + predmetIspita.getNaziv() + " je uspješno dodan!");

            alert.showAndWait();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ispiti.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 600, 600);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            HelloApplication.getMainStage().setTitle("Ispiti pretraga");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }

    }

}
