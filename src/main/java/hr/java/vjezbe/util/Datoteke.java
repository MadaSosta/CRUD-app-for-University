package hr.java.vjezbe.util;

import hr.java.vjezbe.entitet.*;
import javafx.collections.FXCollections;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Datoteke {

    private static final Integer BROJ_ZAPISA_PROFESORA = 5;
    private static final Integer BROJ_ZAPISA_PREDMETA = 6;
    private static final Integer BROJ_ZAPISA_STUDENTA = 5;
    private static final Integer BROJ_ZAPISA_ISPITA = 7;
    private static final Integer BROJ_ZAPISA_OBRAZOVNIH_USTANOVA = 7;
    private static final Integer BROJ_ZAPISA_OCJENA = 3;

    public static List<Ispit> citanjeIspita() {

        FileReader readerIspiti = null;
        try {
            readerIspiti = new FileReader(new File("dat/ispiti.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        BufferedReader bufReaderIspiti = new BufferedReader(readerIspiti);
        List<String> datotekaIspit = bufReaderIspiti.lines().collect(Collectors.toList());
        List<Predmet> listaPredmeta = citanjePredmeta();
        List<Student> listaStudenata = citanjeStudenata();

        List<Ispit> listaIspita = new ArrayList<>();
        for (int i = 0; i < datotekaIspit.size() / BROJ_ZAPISA_ISPITA; i++) {
            String idIspita = datotekaIspit.get(i * BROJ_ZAPISA_ISPITA);
            String predmetZaIspit = datotekaIspit.get(i * BROJ_ZAPISA_ISPITA + 1);
            String studentZaIspit = datotekaIspit.get(i * BROJ_ZAPISA_ISPITA + 2);
            String ocjenaNaIspitu = datotekaIspit.get(i * BROJ_ZAPISA_ISPITA + 3);
            String datumIVrijeme = datotekaIspit.get(i * BROJ_ZAPISA_ISPITA + 4);
            String nazivZgrade = datotekaIspit.get(i * BROJ_ZAPISA_ISPITA + 5);
            String nazivDvorane = datotekaIspit.get(i * BROJ_ZAPISA_ISPITA + 6);
            int brojZaPredmet = Integer.parseInt(predmetZaIspit);
            int brojZaIspit = Integer.parseInt(predmetZaIspit);
            int brojZaStudenta = Integer.parseInt(studentZaIspit);
            Predmet odabraniPredmet = listaPredmeta.get(brojZaIspit - 1);
            Long idPredmeta = Long.parseLong(predmetZaIspit);
            for(Predmet p:listaPredmeta){
                if(p.getId().equals(idPredmeta)){
                    odabraniPredmet = p;
                    break;
                }
            }
            Long idStudenta = Long.parseLong(studentZaIspit);
            Student odabraniStudent = listaStudenata.get(brojZaStudenta - 1);
            for(Student s:listaStudenata){
                if(s.getId().equals(idStudenta)){
                    odabraniStudent = s;
                    break;
                }
            }
            Long dvoraneId = Long.valueOf(1);
            Integer ocjena = Integer.parseInt(ocjenaNaIspitu);
            Ocjena ocjenaZaIspit = dohvatiOcjenu(ocjena);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm");
            Dvorana dvorana = new Dvorana(dvoraneId, nazivZgrade, nazivDvorane);

            listaIspita.add(new Ispit(Long.parseLong(idIspita), odabraniPredmet, odabraniStudent, ocjenaZaIspit,
                    LocalDateTime.parse(datumIVrijeme, dtf), dvorana));
            dvoraneId++;
        }
        return listaIspita;
    }

    public static Ocjena dohvatiOcjenu(Integer ocjena) {
        Ocjena ocjene = null;

        switch(ocjena){
            case 1 -> ocjene = Ocjena.NEDOVOLJAN;
            case 2 -> ocjene = Ocjena.DOVOLJAN;
            case 3 -> ocjene = Ocjena.DOBAR;
            case 4 -> ocjene = Ocjena.VRLO_DOBAR;
            case 5 -> ocjene = Ocjena.IZVRSTAN;
            default -> {
                System.out.println("Nije dobra ocjena!");
            }
        }
        return ocjene;
    }

    public static List<Predmet> citanjePredmeta() {
        FileReader readerPredmeti = null;
        try {
            readerPredmeti = new FileReader(new File("dat/predmeti.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        BufferedReader bufReaderPredmeti = new BufferedReader(readerPredmeti);
        List<String> datotekaPredmeti = bufReaderPredmeti.lines().collect(Collectors.toList());

        List<Student> listaStudenata = citanjeStudenata();
        List<Profesor> listaProfesora = citanjeProfesora();

        List<Predmet> listaPredmeta = new ArrayList<>();
        for (int i = 0; i < datotekaPredmeti.size() / BROJ_ZAPISA_PREDMETA; i++) {
            String idPredmeta = datotekaPredmeti.get(i * BROJ_ZAPISA_PREDMETA);
            String sifraPredmeta = datotekaPredmeti.get(i * BROJ_ZAPISA_PREDMETA + 1);
            String nazivPredmeta = datotekaPredmeti.get(i * BROJ_ZAPISA_PREDMETA + 2);
            String brojEctsBodova = datotekaPredmeti.get(i * BROJ_ZAPISA_PREDMETA + 3);
            String nositeljPredmeta = datotekaPredmeti.get(i * BROJ_ZAPISA_PREDMETA + 4);
            String setStudenata = datotekaPredmeti.get(i * BROJ_ZAPISA_PREDMETA + 5);
            int broj = Integer.parseInt(nositeljPredmeta);
            Set<Student> popisStudentaNaPredmetu = new HashSet<>();
            Integer IntegerNositeljPredmeta = Integer.valueOf(nositeljPredmeta);
            Long longId = Long.valueOf(IntegerNositeljPredmeta);
            Profesor profesorNaPredmetu = listaProfesora.get(broj - 1);

            setStudenata = setStudenata.replaceAll("\\s", "");
            for (int j = 0; j < setStudenata.length(); j++){
                char a = setStudenata.charAt(j);
                int idStudenta = a - ('0');
                for(Student student : listaStudenata){
                    if(idStudenta == student.getId()){
                        popisStudentaNaPredmetu.add(student);
                        break;
                    }
                }
            }
            listaPredmeta.add(new Predmet(Long.parseLong(idPredmeta), sifraPredmeta, nazivPredmeta,
                    Integer.parseInt(brojEctsBodova), profesorNaPredmetu, popisStudentaNaPredmetu));

        }
        return listaPredmeta;
    }

    public static List<Student> citanjeStudenata() {
        FileReader readerStudenti = null;
        try {
            readerStudenti = new FileReader(new File("dat/studenti.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        BufferedReader bufReaderStudenti = new BufferedReader(readerStudenti);
        List<String> datotekaStudenti = bufReaderStudenti.lines().collect(Collectors.toList());

        List<Student> listaStudenata = new ArrayList<>();
        for (int i = 0; i < datotekaStudenti.size() / BROJ_ZAPISA_STUDENTA; i++){
            String idStudenta = datotekaStudenti.get(i * BROJ_ZAPISA_STUDENTA);
            String imeStudenta = datotekaStudenti.get(i * BROJ_ZAPISA_STUDENTA + 1);
            String prezimeStudenta = datotekaStudenti.get(i * BROJ_ZAPISA_STUDENTA + 2);
            String jmbagStudenta = datotekaStudenti.get(i * BROJ_ZAPISA_STUDENTA + 3);
            String datumStudenta = datotekaStudenti.get(i * BROJ_ZAPISA_STUDENTA + 4);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
            listaStudenata.add(new Student(Long.parseLong(idStudenta), imeStudenta, prezimeStudenta, jmbagStudenta,
                    LocalDate.parse(datumStudenta, dtf)));

        }
        return listaStudenata;
    }

    public static List<Profesor> citanjeProfesora() {
        FileReader readerProfesori = null;
        try {
            readerProfesori = new FileReader(new File("dat/profesori.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        BufferedReader bufReaderProfesori = new BufferedReader(readerProfesori);
        List<String> datotekaProfesori = bufReaderProfesori.lines().collect(Collectors.toList());

        List<Profesor> listaProfesora = new ArrayList<>();
        for (int i = 0; i < datotekaProfesori.size() / BROJ_ZAPISA_PROFESORA; i++){
            String idProfesora = datotekaProfesori.get(i * BROJ_ZAPISA_PROFESORA);
            String sifraProfesora = datotekaProfesori.get(i * BROJ_ZAPISA_PROFESORA + 1);
            String imeProfesora = datotekaProfesori.get(i * BROJ_ZAPISA_PROFESORA + 2);
            String prezimeProfesora = datotekaProfesori.get(i * BROJ_ZAPISA_PROFESORA + 3);
            String titulaProfesora = datotekaProfesori.get(i * BROJ_ZAPISA_PROFESORA + 4);
            listaProfesora.add(i, new Profesor.ProfesorBuilder()
                    .Id(Long.parseLong(idProfesora))
                    .Sifra(sifraProfesora)
                    .Ime(imeProfesora)
                    .Prezime(prezimeProfesora)
                    .Titula(titulaProfesora)
                    .build());
        }
        return listaProfesora;
    }


}
