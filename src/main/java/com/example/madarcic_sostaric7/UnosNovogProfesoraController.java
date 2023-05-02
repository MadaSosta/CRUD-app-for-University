package com.example.madarcic_sostaric7;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Profesor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public class UnosNovogProfesoraController {

    @FXML
    private TextField sifraProfesoraTextField;
    @FXML
    private TextField imeProfesoraTextField;
    @FXML
    private TextField prezimeProfesoraTextField;
    @FXML
    private TextField titulaProfesoraTextField;

    public void spremiProfesora() {

        String sifraProfesora = sifraProfesoraTextField.getText();
        String imeProfesora = imeProfesoraTextField.getText();
        String prezimeimeProfesora = prezimeProfesoraTextField.getText();
        String titulaProfesora = titulaProfesoraTextField.getText();


        if(sifraProfesora.isBlank() == true || imeProfesora.isBlank() == true ||
                prezimeimeProfesora.isBlank() == true || titulaProfesora.isBlank() == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Došlo je do pogreške!");
            alert.setContentText("Nijedno polje ne smije ostati prazno!");

            alert.showAndWait();
        }

        else if(sifraProfesora.isBlank() == false && imeProfesora.isBlank() == false &&
                prezimeimeProfesora.isBlank() == false && titulaProfesora.isBlank() == false) {

            OptionalLong maksimalniID = HelloApplication.getListaProfesora().stream()
                    .mapToLong(profesor -> profesor.getId()).max();

            Profesor noviProfesor = new Profesor.ProfesorBuilder()
                    .Id(maksimalniID.getAsLong()+1)
                    .Sifra(sifraProfesora)
                    .Ime(imeProfesora)
                    .Prezime(prezimeimeProfesora)
                    .Titula(titulaProfesora)
                    .build();

            HelloApplication.getListaProfesora().add(noviProfesor);

        //    HelloApplication.napraviNoviTekstFileZaProfesore();
            try {
                BazaPodataka.dodajNovogProfesoraUBazu(noviProfesor);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Spremanje profesora");
            alert.setHeaderText("Uspješno dodan novi profesor!");
            alert.setContentText("Profesor " + imeProfesora + " " + prezimeimeProfesora + " je uspješno dodan!");

            alert.showAndWait();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 600, 600);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            HelloApplication.getMainStage().setTitle("Profesori pretraga");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }

    }

}
