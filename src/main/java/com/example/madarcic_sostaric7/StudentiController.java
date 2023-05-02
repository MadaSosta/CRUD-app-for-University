package com.example.madarcic_sostaric7;

import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class StudentiController {

    @FXML
    private TextField jmbagStudentaTextField;
    @FXML
    private TextField imeStudentaTextField;
    @FXML
    private TextField prezimeStudentaTextField;
    @FXML
    private TextField datumRodjenjaStudentaTextField;

    @FXML
    private TableView<Student> studentTableView;

    @FXML
    private TableColumn<Student, String> jmbagStudentaTableColumn;

    @FXML
    private TableColumn<Student, String> imeStudentaTableColumn;

    @FXML
    private TableColumn<Student, String> prezimeStudentaTableColumn;

    @FXML
    private TableColumn<Student, String> datumRodenjaStudentaTableColumn;

    public void initialize() {

        jmbagStudentaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getJmbag()));

        imeStudentaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getIme()));

        prezimeStudentaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getPrezime()));

        datumRodenjaStudentaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getDatumRodjenja()
                                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))));

        List<Student> ls = IzbornikController.getListaStudenata();
        ObservableList<Student> ol = FXCollections.observableList(IzbornikController.getListaStudenata());
        studentTableView.setItems(ol);




        dohvatiStudente();

    }

    public void dohvatiStudente() {
        String jmbag = jmbagStudentaTextField.getText();
        String ime = imeStudentaTextField.getText();
        String prezime = prezimeStudentaTextField.getText();
        String datumRodjenja = datumRodjenjaStudentaTextField.getText();

        List<Student> filtriraniStudenti = IzbornikController.getListaStudenata().stream()
                .filter(student -> student.getJmbag().contains(jmbag))
                .filter(student -> student.getIme().contains(ime))
                .filter(student -> student.getPrezime().contains(prezime))
                .filter(student -> student.getDatumRodjenja().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))
                        .contains(datumRodjenja))
                .toList();

        studentTableView.setItems(FXCollections.observableList(filtriraniStudenti));

    }

}
