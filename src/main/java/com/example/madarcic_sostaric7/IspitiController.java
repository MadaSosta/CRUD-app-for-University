package com.example.madarcic_sostaric7;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class IspitiController {

    @FXML
    private TextField predmetIspitaTextField;
    @FXML
    private TextField studentIspitaTextField;
    @FXML
    private TextField ocjenaIspitaTextField;
    @FXML
    private TextField dvoranaIspitaTextField;
    @FXML
    private TextField datumIVrijemeIspitaTextField;

    @FXML
    private TableView<Ispit> ispitTableView;

    @FXML
    private TableColumn<Ispit, String> predmetIspitaTableColumn;

    @FXML
    private TableColumn<Ispit, String> studentIspitaTableColumn;

    @FXML
    private TableColumn<Ispit, String> ocjenaIspitaTableColumn;

    @FXML
    private TableColumn<Ispit, String> dvoranaIspitaTableColumn;

    @FXML
    private TableColumn<Ispit, String> datumIVrijemeIspitaTableColumn;

    public void initialize() {

        predmetIspitaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getPredmet().getNaziv()));

        studentIspitaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getStudenti().getIme() + " " +
                                cellData.getValue().getStudenti().getPrezime()));

        ocjenaIspitaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getOcjena().getOpisOcjene()));

        dvoranaIspitaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getDvorana().nazivZgrade() + " " +
                                cellData.getValue().getDvorana().nazivDvorane()));

        datumIVrijemeIspitaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getDatumIVrijeme().format
                                (DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm"))));

        ispitTableView.setItems(FXCollections.observableList(IzbornikController.getListaIspita()));

        dohvatiIspite();



    }

    public void dohvatiIspite() {
        String predmet = predmetIspitaTextField.getText();
        String student = studentIspitaTextField.getText();
        String ocjena = ocjenaIspitaTextField.getText();
        String dvorana = dvoranaIspitaTextField.getText();
        String datumIVrijeme = datumIVrijemeIspitaTextField.getText();

        List<Ispit> filtriraniIspiti = IzbornikController.getListaIspita().stream()
                .filter(ispit -> ispit.getPredmet().getNaziv().contains(predmet))
                .filter(ispit -> ispit.getStudenti().getIme().contains(student) ||
                        ispit.getStudenti().getPrezime().contains(student))
                .filter(ispit -> ispit.getOcjena().getOpisOcjene().contains(ocjena))
                .filter(ispit -> ispit.getDvorana().nazivZgrade().contains(dvorana) ||
                        ispit.getDvorana().nazivDvorane().contains(dvorana))
                .filter(ispit -> ispit.getDatumIVrijeme().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm"))
                        .contains(datumIVrijeme))
                .toList();

        ispitTableView.setItems(FXCollections.observableList(filtriraniIspiti));

    }

}
