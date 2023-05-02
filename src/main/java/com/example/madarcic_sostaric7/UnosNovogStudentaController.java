package com.example.madarcic_sostaric7;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.OptionalLong;

public class UnosNovogStudentaController {

    @FXML
    private TextField jmbagStudentaTextField;
    @FXML
    private TextField imeStudentaTextField;
    @FXML
    private TextField prezimeStudentaTextField;
    @FXML
    private TextField datumRodjenjaStudentaTextField;

    public void spremiStudenta() {

        String jmbagStudenta = jmbagStudentaTextField.getText();
        String imeStudenta = imeStudentaTextField.getText();
        String prezimeimeStudenta = prezimeStudentaTextField.getText();
        String datumRodjenjaStudenta = datumRodjenjaStudentaTextField.getText();

        if(jmbagStudenta.isBlank() == true || imeStudenta.isBlank() == true ||
                prezimeimeStudenta.isBlank() == true || datumRodjenjaStudenta.isBlank() == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Došlo je do pogreške!");
            alert.setContentText("Nijedno polje ne smije ostati prazno!");

            alert.showAndWait();
        }

        else if(jmbagStudenta.isBlank() == false && imeStudenta.isBlank() == false &&
                prezimeimeStudenta.isBlank() == false && datumRodjenjaStudenta.isBlank() == false) {

            OptionalLong maksimalniID = IzbornikController.getListaStudenata().stream()
                    .mapToLong(student -> student.getId()).max();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
            LocalDate parsiraniDatumRodjenjaStudenta = LocalDate.parse(datumRodjenjaStudenta, dtf);


            Student noviStudent = new Student(maksimalniID.getAsLong() + 1, imeStudenta, prezimeimeStudenta, jmbagStudenta,
                    parsiraniDatumRodjenjaStudenta);

            IzbornikController.getListaStudenata().add(noviStudent);

        //    IzbornikController.napraviNoviTekstFileZaStudente();
            try {
                BazaPodataka.dodajNovogStudentaUBazu(noviStudent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Spremanje studenta");
            alert.setHeaderText("Uspješno dodan novi student!");
            alert.setContentText("Student " + imeStudenta + " " + prezimeimeStudenta + " je uspješno dodan!");

            alert.showAndWait();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("studenti.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 600, 600);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            HelloApplication.getMainStage().setTitle("Studenti pretraga");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }

    }

}
