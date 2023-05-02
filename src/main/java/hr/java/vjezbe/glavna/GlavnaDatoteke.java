/*
package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.sortiranje.StudentSorter;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class GlavnaDatoteke {

    private static final Integer BROJ_ZAPISA_PROFESORA = 5;
    private static final Integer BROJ_ZAPISA_PREDMETA = 6;
    private static final Integer BROJ_ZAPISA_STUDENTA = 5;
    private static final Integer BROJ_ZAPISA_ISPITA = 7;
    private static final Integer BROJ_ZAPISA_OBRAZOVNIH_USTANOVA = 7;
    private static final Integer BROJ_ZAPISA_OCJENA = 3;
    public static final int GODINA = 2022;

    public static void main(String[] args) {

        logger.info("Program je pokrenut.");

        List<Profesor> listaProfesora = new ArrayList<>();
        List<Student> listaStudenata = new ArrayList<>();
        List<Predmet> listaPredmeta = new ArrayList<>();
        List<Ispit> listaIspita = new ArrayList<>();
        List<ObrazovnaUstanova> listaObrazovnihUstanova = new ArrayList<>();

        Map<Profesor, List<Predmet>> predmetiProfesora = new HashMap<>();

        try(FileReader readerProfesori = new FileReader(new File("dat/profesori.txt"));
            FileReader readerStudenti = new FileReader(new File("dat/studenti.txt"));
            FileReader readerPredmeti = new FileReader(new File("dat/predmeti.txt"));
            FileReader readerIspiti = new FileReader(new File("dat/ispiti.txt"));
            FileReader readerObrazovnihUstanova = new FileReader(new File("dat/obrazovnaustanova.txt"));
            FileReader readerOcjena = new FileReader(new File("dat/ocjene.txt"))) {

            BufferedReader bufReaderProfesori = new BufferedReader(readerProfesori);
            BufferedReader bufReaderStudenti = new BufferedReader(readerStudenti);
            BufferedReader bufReaderPredmeti = new BufferedReader(readerPredmeti);
            BufferedReader bufReaderIspiti = new BufferedReader(readerIspiti);
            BufferedReader bufReaderObrazovnihUstanova = new BufferedReader(readerObrazovnihUstanova);
            BufferedReader bufReaderOcjena = new BufferedReader(readerOcjena);

            List<String> datotekaProfesori = bufReaderProfesori.lines().collect(Collectors.toList());
            List<String> datotekaStudenti = bufReaderStudenti.lines().collect(Collectors.toList());
            List<String> datotekaPredmeti = bufReaderPredmeti.lines().collect(Collectors.toList());
            List<String> datotekaIspit = bufReaderIspiti.lines().collect(Collectors.toList());
            List<String> datotekaObrazovneUstanove = bufReaderObrazovnihUstanova.lines().collect(Collectors.toList());
            List<String> datotekaOcjena = bufReaderOcjena.lines().collect(Collectors.toList());

            System.out.println("Učitavanje profesora...");
            System.out.println("Učitavanje studenata...");
            System.out.println("Učitavanje predmeta...");
            System.out.println("Učitavanje ispita i ocjena...");
            System.out.println("Učitavanje obrazovnih ustanova...");

            System.out.println("");

            listaProfesora = citanjeProfesora(datotekaProfesori);
            listaStudenata = citanjeStudenata(datotekaStudenti);

            for(Profesor p : listaProfesora){
                List<Predmet> tmp = new ArrayList<>();
                predmetiProfesora.put(p, tmp);
            }

            listaPredmeta = citanjePredmeta(datotekaPredmeti, listaProfesora, listaStudenata, predmetiProfesora);
            listaIspita = citanjeIspita(datotekaIspit, listaPredmeta, listaStudenata);


            listaObrazovnihUstanova = citanjeObrazovnihUstanova(datotekaObrazovneUstanove, listaProfesora,
                    listaStudenata, listaPredmeta, listaIspita);

            logger.info("Datoteka uspješno pročitana i pohranjena.");

            ispisPodatakaPredmeta(predmetiProfesora);
            ispisStudentaNaPredmetu(listaPredmeta);

            ispisOdlicnogStudenta(listaIspita);
            citanjeOcjena(datotekaOcjena, listaObrazovnihUstanova, listaIspita);

            serializableOU(listaObrazovnihUstanova);

        } catch (IOException ex) {
            logger.error("Neuspjesno otvaranje datoteke. ", ex);
            System.out.println("Došlo je do pogreške!");
        }

      //  listaProfesora.stream().forEach(System.out::println);
      //  listaStudenata.stream().forEach(System.out::println);
     //   listaPredmeta.stream().forEach(System.out::println);
     //   listaIspita.stream().forEach(System.out::println);
     //   listaObrazovnihUstanova.stream().forEach(System.out::println);




    }

    private static void serializableOU(List<ObrazovnaUstanova> lou){
        File file = new File("./dat/serijalizacija.txt");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            for (int i = 0; i < lou.size(); i++) {
                oos.writeObject(lou.get(i));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Provedena serijalizacija");

    }

    private static void citanjeOcjena(List<String> datotekaOcjena, List<ObrazovnaUstanova> listaObrazovnihUstanova,
                                      List<Ispit> listaIspita) {
        for (int i = 0; i < datotekaOcjena.size() / BROJ_ZAPISA_OCJENA; i++){
            String idStudenta = datotekaOcjena.get(i * BROJ_ZAPISA_OCJENA);
            String ocjenaZavrsnog = datotekaOcjena.get(i * BROJ_ZAPISA_OCJENA + 1);
            String ocjenaObraneZavrsnog = datotekaOcjena.get(i * BROJ_ZAPISA_OCJENA + 2);
            Integer ocjenaZ = Integer.parseInt(ocjenaZavrsnog);
            Integer ocjenaOZ = Integer.parseInt(ocjenaObraneZavrsnog);
            Ocjena ocjenaZavrsnogRada = dohvatiOcjenu(ocjenaZ);
            Ocjena ocjenaObraneZavrsnogRada = dohvatiOcjenu(ocjenaOZ);
            Long idStud = Long.parseLong(idStudenta);
            Student trazeniStudent = null;
            for(int j = 0; j < listaObrazovnihUstanova.size(); j++){
                ObrazovnaUstanova obrazovnaUstanova = listaObrazovnihUstanova.get(j);
                for(Student student : listaObrazovnihUstanova.get(j).getStudenti()){
                    if(student.getId().equals(idStud)){
                        trazeniStudent = student;
                        break;
                    }
                }
                if(obrazovnaUstanova instanceof VeleucilisteJave){
                    List<Ispit> ispiti = listaObrazovnihUstanova.get(j).getIspiti();
                    boolean imaStudenta = false;
                    for (Ispit e:ispiti) {
                        if(e.getStudenti().equals(trazeniStudent)){
                            imaStudenta = true;
                        }
                    }
                    if(imaStudenta){
                    List<Ispit> filtriraniIspita = ((VeleucilisteJave) obrazovnaUstanova).filtrirajIspitePoStudentu
                            (listaObrazovnihUstanova.get(j).getIspiti(), trazeniStudent);
                    BigDecimal konacnaOcjena = ((VeleucilisteJave) obrazovnaUstanova).izracunajKonacnuOcjenuStudijaZaStudenta
                            (filtriraniIspita, ocjenaZavrsnogRada, ocjenaObraneZavrsnogRada);
                    System.out.println("Konacna ocjena za studenta " + trazeniStudent.getIme() + " " +
                            trazeniStudent.getPrezime() + " " + konacnaOcjena);
                    Student najboljiStudentNaGodini = obrazovnaUstanova.odrediNajuspjesnijegStudentaNaGodini(GODINA);
                    System.out.println("Najbolji student u " + GODINA + " " +  trazeniStudent.getIme() + " " +
                            trazeniStudent.getPrezime() + najboljiStudentNaGodini);
                    break;
                    }
                }

                if (obrazovnaUstanova instanceof FakultetRacunarstva){
                    if(listaObrazovnihUstanova.get(j).getStudenti().contains(trazeniStudent)){
                    List<Ispit> filtriraniIspita = ((FakultetRacunarstva) obrazovnaUstanova).filtrirajIspitePoStudentu
                            (listaObrazovnihUstanova.get(j).getIspiti(), trazeniStudent);
                    BigDecimal konacnaOcjena = ((FakultetRacunarstva) obrazovnaUstanova).izracunajKonacnuOcjenuStudijaZaStudenta
                            (filtriraniIspita, ocjenaZavrsnogRada, ocjenaObraneZavrsnogRada);
                    System.out.println("Konacna ocjena za studenta " + trazeniStudent.getIme() + " " +
                            trazeniStudent.getPrezime() + " " + konacnaOcjena);
                    Student najboljiStudentNaGodini = obrazovnaUstanova.odrediNajuspjesnijegStudentaNaGodini(GODINA);
                    System.out.println("Najbolji student u " + GODINA + " " +  najboljiStudentNaGodini.getIme() + " " +
                            najboljiStudentNaGodini.getPrezime() + najboljiStudentNaGodini);
                    Student rektorStudent = ((FakultetRacunarstva) obrazovnaUstanova).odrediStudentaZaRektorovuNagradu();
                    System.out.println("Student koji je osvojio rektorovu nagradu je: " + rektorStudent.getIme() + " " +
                            rektorStudent.getPrezime() + " JMBAG: " + rektorStudent.getJmbag());
                    break;
                    }
                }
            }


        }
    }

    private static void ispisStudentaNaPredmetu(List<Predmet> listaPredmeta) {
        for (var p : listaPredmeta){
            if(p.getStudenti().isEmpty()){
                System.out.println("Nema studenata upisanih na predmet '" + p.getNaziv() + "'.");
            }
            else {
                System.out.println("Studenti upisani na predmet '" + p.getNaziv() + "' su: ");
                List<Student> studentiNaPredmetu = new ArrayList<>(p.getStudenti());
                studentiNaPredmetu.sort(new StudentSorter());

                for (var s : studentiNaPredmetu){
                    System.out.println(s.getPrezime() + " " + s.getIme());
                }
            }
        }
    }

    private static void ispisPodatakaPredmeta(Map<Profesor, List<Predmet>> predmetiProfesora) {
        for(Profesor p : predmetiProfesora.keySet()){
            System.out.println("Profesor " + p.getIme() + " " + p.getPrezime() + " predaje sljedece predmete: ");
            int br = 0;
            for(List<Predmet> listaPredmeta : predmetiProfesora.values()){
                for(Predmet predmet : listaPredmeta){
                    if(p.equals(predmet.getNositelj())){
                        System.out.println((br + 1) + ") " + predmet.getNaziv());
                        br++;
                    }
                }
            }
        }
    }

    private static void ispisOdlicnogStudenta(List<Ispit> listaIspita) {

        listaIspita.stream()
                .filter(ispit -> ispit.getOcjena().equals(Ocjena.IZVRSTAN))
                .forEach(student -> System.out.println("Student " + (student.getStudenti().getIme()) + " " +
                        (student.getStudenti().getPrezime()) + " je ostvario ocjenu 'izvrstan' na predmetu " +
                        (student.getPredmet().getNaziv()) + "."));

    }

    private static List<ObrazovnaUstanova> citanjeObrazovnihUstanova(List<String> datotekaObrazovneUstanove,
                                                                     List<Profesor> listaProfesora,
                                                                     List<Student> listaStudenata,
                                                                     List<Predmet> listaPredmeta,
                                                                     List<Ispit> listaIspita) {

        List<ObrazovnaUstanova> listaObrazovnihUstanova = new ArrayList<>();
        for (int i = 0; i < datotekaObrazovneUstanove.size() / BROJ_ZAPISA_OBRAZOVNIH_USTANOVA; i++) {
            String idObrazovneUstanove = datotekaObrazovneUstanove.get(i * BROJ_ZAPISA_OBRAZOVNIH_USTANOVA);
            String nazivObrazovneUstanove = datotekaObrazovneUstanove.get(i * BROJ_ZAPISA_OBRAZOVNIH_USTANOVA + 1);
            String predmetZaOU = datotekaObrazovneUstanove.get(i * BROJ_ZAPISA_OBRAZOVNIH_USTANOVA + 2);
            String profesorZaOU = datotekaObrazovneUstanove.get(i * BROJ_ZAPISA_OBRAZOVNIH_USTANOVA + 3);
            String studentZaOU = datotekaObrazovneUstanove.get(i * BROJ_ZAPISA_OBRAZOVNIH_USTANOVA + 4);
            String ispitZaOU = datotekaObrazovneUstanove.get(i * BROJ_ZAPISA_OBRAZOVNIH_USTANOVA + 5);
            String odabirFakulteta = datotekaObrazovneUstanove.get(i * BROJ_ZAPISA_OBRAZOVNIH_USTANOVA + 6);
            int brojZaOdabirFaksa = Integer.parseInt(odabirFakulteta);
            List<Predmet> odabraniPredmet = new ArrayList<>();
            predmetZaOU = predmetZaOU.replaceAll("\\s", "");
            for (int j = 0; j < predmetZaOU.length(); j++){
                char a = predmetZaOU.charAt(j);
                int idPredmeta = a - ('0');
                Long longIdPredmeta = Long.valueOf(idPredmeta);
                for(Predmet predmet : listaPredmeta){
                    if(longIdPredmeta == predmet.getId()){
                        odabraniPredmet.add(predmet);
                        break;
                    }
                }
            }
            List<Profesor> odabraniProfesor = new ArrayList<>();
            profesorZaOU = profesorZaOU.replaceAll("\\s", "");
            for (int j = 0; j < profesorZaOU.length(); j++){
                char a = profesorZaOU.charAt(j);
                int idProfesor = a - ('0');
                Long longIdProfesora = Long.valueOf(idProfesor);
                for(Profesor profesor : listaProfesora){
                    if(longIdProfesora == profesor.getId()){
                        odabraniProfesor.add(profesor);
                        break;
                    }
                }
            }
            List<Student> odabraniStudent = new ArrayList<>();
            studentZaOU = studentZaOU.replaceAll("\\s", "");
            for (int j = 0; j < studentZaOU.length(); j++){
                char a = studentZaOU.charAt(j);
                int idStudenta = a - ('0');
                Long longIdStudent = Long.valueOf(idStudenta);
                for(Student student : listaStudenata){
                    if(longIdStudent == student.getId()){
                        odabraniStudent.add(student);
                        break;
                    }
                }
            }

            List<Ispit> odabraniIspit = new ArrayList<>();
            studentZaOU = ispitZaOU.replaceAll("\\s", "");
            for (int j = 0; j < ispitZaOU.length(); j++){
                char a = ispitZaOU.charAt(j);
                int idIspita = a - ('0');
                Long longIdIspita = Long.valueOf(idIspita);
                for(Ispit ispit : listaIspita){
                    if(longIdIspita == ispit.getId()){
                        odabraniIspit.add(ispit);
                        break;
                    }
                }
            }

            ObrazovnaUstanova obrazovnaUstanova = switch (brojZaOdabirFaksa){
                case 1 -> new VeleucilisteJave(Long.parseLong(idObrazovneUstanove), nazivObrazovneUstanove,
                        odabraniPredmet, odabraniProfesor, odabraniStudent, odabraniIspit);
                case 2 -> new FakultetRacunarstva(Long.parseLong(idObrazovneUstanove), nazivObrazovneUstanove,
                        odabraniPredmet, odabraniProfesor, odabraniStudent, odabraniIspit);

                default -> throw new IllegalStateException("Unexpected value: " + brojZaOdabirFaksa);
            };
            listaObrazovnihUstanova.add(obrazovnaUstanova);

        }

        return listaObrazovnihUstanova;
    }

    private static List<Ispit> citanjeIspita(List<String> datotekaIspit, List<Predmet> listaPredmeta,
                                             List<Student> listaStudenata) {
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
            Integer ocjena = Integer.parseInt(ocjenaNaIspitu);
            Ocjena ocjenaZaIspit = dohvatiOcjenu(ocjena);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm");
            Dvorana dvorana = new Dvorana(nazivZgrade, nazivDvorane);
            String imeStudenta = listaStudenata.get(i).getIme();
            String prezimeStudenta = listaStudenata.get(i).getPrezime();
            String jmbagStudenta = listaStudenata.get(i).getJmbag();
            LocalDate datumRodjenjaStudenta = listaStudenata.get(i).getDatumRodjenja();
            listaPredmeta.get(brojZaPredmet - 1).addStudent(new Student(idStudenta, imeStudenta, prezimeStudenta,
                    jmbagStudenta, datumRodjenjaStudenta));
            listaIspita.add(new Ispit(Long.parseLong(idIspita), odabraniPredmet, odabraniStudent, ocjenaZaIspit,
                    LocalDateTime.parse(datumIVrijeme, dtf), dvorana));
        }
        return listaIspita;
    }

    private static Ocjena dohvatiOcjenu(Integer ocjena) {
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

    private static List<Predmet> citanjePredmeta(List<String> datotekaPredmeti, List<Profesor> listaProfesora, List<Student> listaStudenata, Map<Profesor, List<Predmet>> predmetiProfesora) {
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

            predmetiProfesora.get(profesorNaPredmetu).add(new Predmet(Long.parseLong(idPredmeta), sifraPredmeta, nazivPredmeta,
                Integer.parseInt(brojEctsBodova), profesorNaPredmetu, popisStudentaNaPredmetu));

        }
        return listaPredmeta;
    }

    private static List<Student> citanjeStudenata(List<String> datotekaStudenti) {
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

    private static List<Profesor> citanjeProfesora(List<String> datotekaProfesori) {
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
*/
