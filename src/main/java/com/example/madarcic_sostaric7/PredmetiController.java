package com.example.madarcic_sostaric7;

import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class PredmetiController {

    @FXML
    private TextField sifraPredmetaTextField;
    @FXML
    private TextField nazivPredmetaTextField;
    @FXML
    private TextField brojEctsBodovaPredmetaTextField;
    @FXML
    private TextField nositeljPredmetaTextField;
    @FXML
    private TextField studentPredmetaTextField;

    @FXML
    private TableView<Predmet> predmetTableView;

    @FXML
    private TableColumn<Predmet, String> sifraPredmetaTableColumn;

    @FXML
    private TableColumn<Predmet, String> nazivPredmetaTableColumn;

    @FXML
    private TableColumn<Predmet, String> brojEctsBodovaPredmetaTableColumn;

    @FXML
    private TableColumn<Predmet, String> nositeljPredmetaTableColumn;

    @FXML
    private TableColumn<Predmet, String> studentPredmetaTableColumn;

    public void initialize() {

        sifraPredmetaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getSifra()));

        nazivPredmetaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getNaziv()));

        brojEctsBodovaPredmetaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getBrojEctsBodova().toString()));

       nositeljPredmetaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getNositelj().getIme() + " " +
                                cellData.getValue().getNositelj().getPrezime()));

       studentPredmetaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getStudenti().toString()));

    //    predmetTableView.setItems(FXCollections.observableList(IzbornikController.getListaPredmeta()));

    /*    Thread thread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while(true){
                            System.out.println("Vrtim se dugo!");
                        }
                    }
                }
        );

        AtomicBoolean aab = new AtomicBoolean();
        AtomicInteger aii = new AtomicInteger(1);
        aii.getAndIncrement();
        thread.start(); */


        dohvatiPredmete();

    }

    public void dohvatiPredmete() {
        String sifra = sifraPredmetaTextField.getText();
        String naziv = nazivPredmetaTextField.getText();
        String brojEctsBodova = brojEctsBodovaPredmetaTextField.getText();
        String nositelj = nositeljPredmetaTextField.getText();
        String student = studentPredmetaTextField.getText();



        List<Predmet> filtriraniPredmeti = IzbornikController.getListaPredmeta().stream()
                .filter(predmet -> predmet.getSifra().contains(sifra))
                .filter(predmet -> predmet.getNaziv().contains(naziv))
                .filter(predmet -> predmet.getBrojEctsBodova().toString().contains(brojEctsBodova))
                .filter(predmet -> predmet.getNositelj().getIme().contains(nositelj) ||
                       predmet.getNositelj().getPrezime().contains(nositelj))
                .filter(predmet -> predmet.getStudenti().toString().contains(student))
                .toList();


        predmetTableView.setItems(FXCollections.observableList(filtriraniPredmeti));


    }

}
