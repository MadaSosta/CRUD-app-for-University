package com.example.madarcic_sostaric7;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.niti.DatumRodjenjaNit;
import hr.java.vjezbe.niti.NajboljiStudentNit;
import hr.java.vjezbe.util.Datoteke;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    private static Stage mainStage;

    private static List<Profesor> listaProfesora;

    @Override
    public void start(Stage stage) throws IOException {
        listaProfesora = new ArrayList<>();
        dodajProfesore();
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pocetni-zaslon.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Fakultet");
        stage.setScene(scene);
        stage.show();
    }

    public static void dodajProfesore() {
    //    listaProfesora = Datoteke.citanjeProfesora();
            try {
                listaProfesora = BazaPodataka.dohvatiProfesoreIzBaze();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    public static void napraviNoviTekstFileZaProfesore(){
        FileWriter fw = null;
        try {
            fw = new FileWriter("dat/profesori.txt");

            for (int i = 0; i < getListaProfesora().size(); i++){
                fw.write(String.valueOf(getListaProfesora().get(i).getId()));
                fw.write("\n");
                fw.write(getListaProfesora().get(i).getSifra());
                fw.write("\n");
                fw.write(getListaProfesora().get(i).getIme());
                fw.write("\n");
                fw.write(getListaProfesora().get(i).getPrezime());
                fw.write("\n");
                fw.write(getListaProfesora().get(i).getTitula());
                if(i < getListaProfesora().size() - 1){
                    fw.write("\n");
                }
            }

            System.out.println("Uspješno zapisano u file!");

            fw.close();

        } catch (IOException e) {
            System.out.println("Greška prilikom spremanja.");
        }
    }

    public static void setMainStageTitle(String newTitle){
        mainStage.setTitle(newTitle);
        mainStage.show();
    }

    public static void main(String[] args) {
        Timeline threadovi = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.runLater(new DatumRodjenjaNit());
            }
        }));

        threadovi.getKeyFrames().add(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.runLater(new NajboljiStudentNit());
            }
        }));
        threadovi.setCycleCount(Timeline.INDEFINITE);
        threadovi.play();
        launch();
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static List<Profesor> getListaProfesora() {
        return listaProfesora;
    }
}