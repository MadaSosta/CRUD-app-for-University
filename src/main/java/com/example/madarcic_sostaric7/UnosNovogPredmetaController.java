package com.example.madarcic_sostaric7;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.util.Datoteke;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class UnosNovogPredmetaController {

    Set<Student> set = new HashSet<>(0);

    @FXML
    private TextField sifraPredmetaTextField;
    @FXML
    private TextField nazivPredmetaTextField;
    @FXML
    private TextField brojEctsBodovaPredmetaTextField;
    @FXML
    private ComboBox<Student> studentComboBox;
    @FXML
    private ComboBox<Profesor> nositeljPredmetaComboBox;
    @FXML
    ListView<Student> listView;


    public void initialize(){

        List<Student> popisStudenata = Datoteke.citanjeStudenata();

        ObservableList<Student> studentObservableList = FXCollections.observableList(popisStudenata);
        studentComboBox.setItems(studentObservableList);
        studentComboBox.getSelectionModel().select(0);

        ObservableList<Profesor> profesorObservableList = FXCollections.observableList(HelloApplication.getListaProfesora());
        nositeljPredmetaComboBox.setItems(profesorObservableList);
        nositeljPredmetaComboBox.getSelectionModel().select(0);

        set.clear();

    }

    @FXML
    protected void noviSetStudenata(){
        Student odabraniStudent = studentComboBox.getSelectionModel().getSelectedItem();
        set.add(odabraniStudent);
        List<Student> studentskaLista = new ArrayList<>(set);
        ObservableList<Student> dodaniStudenti = FXCollections.observableList(studentskaLista);
        listView.setItems(dodaniStudenti);
    }

    public void spremiPredmet() {

        String sifraPredmeta = sifraPredmetaTextField.getText();
        String nazivPredmeta = nazivPredmetaTextField.getText();
        String brojEctsBodovaPredmeta = brojEctsBodovaPredmetaTextField.getText();
        Profesor nositeljPredmeta = nositeljPredmetaComboBox.getValue();

        if(sifraPredmeta.isBlank() == true || nazivPredmeta.isBlank() == true ||
                brojEctsBodovaPredmeta.isBlank() == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Došlo je do pogreške!");
            alert.setContentText("Nijedno polje ne smije ostati prazno!");

            alert.showAndWait();
        }

        else if(sifraPredmeta.isBlank() == false && nazivPredmeta.isBlank() == false &&
                brojEctsBodovaPredmeta.isBlank() == false) {

            OptionalLong maksimalniID = IzbornikController.getListaPredmeta().stream()
                    .mapToLong(predmet -> predmet.getId()).max();

            Predmet noviPredmet = new Predmet(maksimalniID.getAsLong() + 1, sifraPredmeta, nazivPredmeta,
                    Integer.parseInt(brojEctsBodovaPredmeta), nositeljPredmeta, set);

            IzbornikController.getListaPredmeta().add(noviPredmet);

        //    IzbornikController.napraviNoviTekstFileZaPredmete();
            try {
                BazaPodataka.dodajNoviPredmetUBazu(noviPredmet);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Spremanje predmeta");
            alert.setHeaderText("Uspješno dodan novi predmet!");
            alert.setContentText("Predmet " + sifraPredmeta + " " + nazivPredmeta + " je uspješno dodan!");

            alert.showAndWait();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("predmeti.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 600, 600);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            HelloApplication.getMainStage().setTitle("Predmeti pretraga");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }

    }

}
