package com.example.madarcic_sostaric7;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.util.Datoteke;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IzbornikController {

    private static List<Student> listaStudenata;
    private static List<Predmet> listaPredmeta;
    private static List<Ispit> listaIspita;

    public void showProfesoriPretraga() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        HelloApplication.getMainStage().setTitle("Profesori pretraga");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void showProfesoriUnos() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unosNovogProfesora.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        HelloApplication.getMainStage().setTitle("Profesori unos");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void showStudentiPretraga() throws IOException {
        dodajStudenta();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("studenti.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        HelloApplication.getMainStage().setTitle("Studenti pretraga");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }
    public void showStudentiUnos() throws IOException {
        dodajStudenta();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unosNovogStudenta.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        HelloApplication.getMainStage().setTitle("Studenti unos");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public static void napraviNoviTekstFileZaStudente(){
        FileWriter fw = null;
        try {
            fw = new FileWriter("dat/studenti.txt");

            for (int i = 0; i < getListaStudenata().size(); i++){
                fw.write(String.valueOf(getListaStudenata().get(i).getId()));
                fw.write("\n");
                fw.write(getListaStudenata().get(i).getIme());
                fw.write("\n");
                fw.write(getListaStudenata().get(i).getPrezime());
                fw.write("\n");
                fw.write(getListaStudenata().get(i).getJmbag());
                fw.write("\n");
                fw.write(getListaStudenata().get(i).getDatumRodjenja()
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
                if(i < getListaStudenata().size() - 1){
                    fw.write("\n");
                }
            }

            System.out.println("Uspješno zapisano u file!");

            fw.close();

        } catch (IOException e) {
            System.out.println("Greška prilikom spremanja.");
        }
    }

    private void dodajStudenta() {
        //listaStudenata = Datoteke.citanjeStudenata();
        try {
            listaStudenata = BazaPodataka.dohvatiStudenteIzBaze();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Student> getListaStudenata() {
        return listaStudenata;
    }

    public void showPredmetiPretraga() throws IOException {
        dodajPredmet();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("predmeti.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        HelloApplication.getMainStage().setTitle("Predmeti");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void showPredmetiUnos() throws IOException {
        dodajPredmet();
        HelloApplication.dodajProfesore();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unosNovogPredmeta.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        HelloApplication.getMainStage().setTitle("Unos");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public static void napraviNoviTekstFileZaPredmete(){
        FileWriter fw = null;
        try {
            fw = new FileWriter("dat/predmeti.txt");

            for (int i = 0; i < getListaPredmeta().size(); i++){
                fw.write(String.valueOf(getListaPredmeta().get(i).getId()));
                fw.write("\n");
                fw.write(getListaPredmeta().get(i).getSifra());
                fw.write("\n");
                fw.write(getListaPredmeta().get(i).getNaziv());
                fw.write("\n");
                fw.write(String.valueOf(getListaPredmeta().get(i).getBrojEctsBodova()));
                fw.write("\n");
                fw.write(String.valueOf(getListaPredmeta().get(i).getNositelj().getId()));
                fw.write("\n");
                for(int j = 0; j < getListaPredmeta().get(i).getStudenti().size(); j++){
                    List<Student> studentiId = new ArrayList<>(getListaPredmeta().get(i).getStudenti());
                    fw.write(String.valueOf(studentiId.get(j).getId()));
                    fw.write(" ");
                }
                if(i < getListaPredmeta().size() - 1){
                    fw.write("\n");
                }
            }

            System.out.println("Uspješno zapisano u file!");

            fw.close();

        } catch (IOException e) {
            System.out.println("Greška prilikom spremanja.");
        }
    }

    public void dodajPredmet() {
    //    listaPredmeta = Datoteke.citanjePredmeta();
        try {
            listaPredmeta = BazaPodataka.dohvatiPredmeteIzBaze();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Predmet> getListaPredmeta() {
        return listaPredmeta;
    }

    public void showIspitiPretraga() throws IOException {
        dodajIspite();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ispiti.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        HelloApplication.getMainStage().setTitle("Ispiti");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void showIspitiUnos() throws IOException {
        dodajIspite();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unosNovogIspita.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        HelloApplication.getMainStage().setTitle("Ispiti");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public static void napraviNoviTekstFileZaIspite(){
        FileWriter fw = null;
        try {
            fw = new FileWriter("dat/ispiti.txt");

            for (int i = 0; i < getListaIspita().size(); i++){
                fw.write(String.valueOf(getListaIspita().get(i).getId()));
                fw.write("\n");
                fw.write(String.valueOf(getListaIspita().get(i).getPredmet().getId()));
                fw.write("\n");
                fw.write(String.valueOf(getListaIspita().get(i).getStudenti().getId()));
                fw.write("\n");
                fw.write(String.valueOf(getListaIspita().get(i).getOcjena().getVrijednostOcjene()));
                fw.write("\n");
                fw.write(getListaIspita().get(i).getDatumIVrijeme()
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm")));
                fw.write("\n");
                fw.write(getListaIspita().get(i).getDvorana().nazivZgrade());
                fw.write("\n");
                fw.write(getListaIspita().get(i).getDvorana().nazivDvorane());

                if(i < getListaIspita().size() - 1){
                    fw.write("\n");
                }
            }

            System.out.println("Uspješno zapisano u file!");

            fw.close();

        } catch (IOException e) {
            System.out.println("Greška prilikom spremanja.");
        }
    }

    private void dodajIspite() {

    //    listaIspita = Datoteke.citanjeIspita();
        try {
            listaIspita = BazaPodataka.dohvatiIspiteIzBaze();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Ispit> getListaIspita() {
        return listaIspita;
    }
}
