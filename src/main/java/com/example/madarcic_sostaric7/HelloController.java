package com.example.madarcic_sostaric7;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HelloController {

    @FXML
    private TextField sifraProfesoraTextField;
    @FXML
    private TextField imeProfesoraTextField;
    @FXML
    private TextField prezimeProfesoraTextField;
    @FXML
    private TextField titulaProfesoraTextField;

    @FXML
    private TableView<Profesor> profesorTableView;

    @FXML
    private TableColumn<Profesor, String> sifraProfesoraTableColumn;

    @FXML
    private TableColumn<Profesor, String> imeProfesoraTableColumn;

    @FXML
    private TableColumn<Profesor, String> prezimeProfesoraTableColumn;

    @FXML
    private TableColumn<Profesor, String> titulaProfesoraTableColumn;


    public void initialize() {

        sifraProfesoraTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getSifra()));

        imeProfesoraTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getIme()));

        prezimeProfesoraTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getPrezime()));

        titulaProfesoraTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getTitula()));

        profesorTableView.setItems(FXCollections.observableList(HelloApplication.getListaProfesora()));

        dohvatiProfesore();

    }

    public void dohvatiProfesore() {
        String sifra = sifraProfesoraTextField.getText();
        String ime = imeProfesoraTextField.getText();
        String prezime = prezimeProfesoraTextField.getText();
        String titula = titulaProfesoraTextField.getText();

        List<Profesor> filtriraniProfesori = HelloApplication.getListaProfesora().stream()
                .filter(profesor -> profesor.getSifra().contains(sifra))
                .filter(profesor -> profesor.getIme().contains(ime))
                .filter(profesor -> profesor.getPrezime().contains(prezime))
                .filter(profesor -> profesor.getTitula().contains(titula))
                .toList();

        profesorTableView.setItems(FXCollections.observableList(filtriraniProfesori));

    }

}