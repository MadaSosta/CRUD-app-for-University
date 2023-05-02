package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.sortiranje.StudentSorter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Glavna klasa s metodom main i pomocnim metodama.
 */

/*
public class Glavna {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    */
/**
     * Konstante
     * BROJ_PROFESORA
     * BROJ_STUDENTA
     * BROJ_ISPITNOGROKA
     * BROJ_ZA_VELEUCILISTE_JAVE
     * BROJ_ZA_FAKULTET_RACUNARSTVA
     * GODINA
     *//*

    public static final int BROJ_PROFESORA = 2;
    public static final int BROJ_PREDMETA = 3;
    public static final int BROJ_STUDENTA = 2;
    public static final int BROJ_ISPITNOGROKA = 2;
    public static final int BROJ_ZA_VELEUCILISTE_JAVE = 1;
    public static final int BROJ_ZA_FAKULTET_RACUNARSTVA = 2;
    public static final int GODINA = 2022;

    */
/**
     * Globalna polja
     * profesori
     * predmeti
     * studenti
     * ispiti
     *//*

    private static List<Profesor> profesori = new ArrayList<>();
    private static List<Predmet> predmeti = new ArrayList<>();
    private static List<Student> studenti = new ArrayList<>();
    private static List<Ispit> ispiti = new ArrayList<>();
    private static Sveuciliste<ObrazovnaUstanova> sveuciliste = new Sveuciliste<>();

    //private static List<ObrazovnaUstanova> obrazovnaUstanove = new ArrayList<>();


    */
/**
     * Pocetak programa.
     * @param args Argumenti iz komandne linije.
     *//*


    public static void main(String[] args) {

        logger.info("Program je pokrenut!");

        Scanner unos = new Scanner(System.in);

        Integer brojObrazovnihUstanova = null;

        boolean neispravanUnos = false;

        do {
            neispravanUnos = false;
            System.out.print("Unesite broj obrazovnih ustanova: ");

            try {
                brojObrazovnihUstanova = unos.nextInt();
                unos.nextLine();

            } catch (InputMismatchException ex) {
                logger.error("Nije unesen broj! ", ex);
                unos.nextLine();
                neispravanUnos = true;
                System.out.println("Neispravan unos, molimo pokušajte ponovno!");
            }

        } while(neispravanUnos);


        unosObrazovneUstanove(unos, brojObrazovnihUstanova);

        sveuciliste.getObrazovneUstanove().stream()
                .sorted(Comparator.comparing(ObrazovnaUstanova::getBrojStudenta)
                        .thenComparing(ObrazovnaUstanova::getNazivUstanove))
                .forEach(System.out::println);


    }

    */
/**
     * Metoda za unos obrazovnih ustanova.
     *
     * @param unos                   se dobije od scannera.
     * @param brojObrazovnihUstanova
     *//*

    private static void unosObrazovneUstanove(Scanner unos, Integer brojObrazovnihUstanova) {
    Boolean neispravanUnos = false;

        for(int i = 0; i < brojObrazovnihUstanova; i++){

            Map<Profesor, List<Predmet>> predmetiProfesora = new HashMap<>();

            System.out.println("Unesite podatke za " + (i + 1) + ". obrazovnu ustanovu: ");

            unosProfesora(unos);

            for(var p : profesori){
                List<Predmet> tmp = new ArrayList<>();
                predmetiProfesora.put(p, tmp);
            }

            unosPredmeta(unos, predmetiProfesora);

            ispisPodatakaPredmeta(predmetiProfesora);

            unosStudenta(unos);

            unosIspita(unos, predmeti, studenti);

            ispisStudentaNaPredmetu();

            ispisOdlicnogStudenta();

            System.out.print("Odaberite obrazovnu ustanovu za navedene podatke koje želite unijeti (" +
                    (BROJ_ZA_VELEUCILISTE_JAVE) + " - Veleučilište Jave, " + (BROJ_ZA_FAKULTET_RACUNARSTVA) +
                    " - Fakultet računarstva): ");

            Integer odabirUstanova = null;
            neispravanUnos = false;

            do {
                neispravanUnos = false;
                System.out.print("Odabir >> ");

                try {
                    odabirUstanova  = unos.nextInt();
                    if(odabirUstanova < BROJ_ZA_VELEUCILISTE_JAVE || odabirUstanova > BROJ_ZA_FAKULTET_RACUNARSTVA){
                        logger.error("Ne postoji takav odabir! ");
                        System.out.println("Nema takav odabir u ponudi, molimo pokušajte ponovo!");
                        neispravanUnos = true;
                    }

                } catch (InputMismatchException ex){
                    logger.error("Nije upisan broj! ", ex);
                    unos.nextLine();
                    System.out.println("Molimo ponovite odabir.");
                    neispravanUnos = true;
                }
            } while(neispravanUnos);

            unos.nextLine();

            ObrazovnaUstanova obrazovnaUstanova = null;

            if(odabirUstanova == BROJ_ZA_VELEUCILISTE_JAVE){
                System.out.print("Unesite naziv obrazovne ustanove: ");
                String nazivObrazovneUstanove = unos.nextLine();

            //    obrazovnaUstanove.add(i, new VeleucilisteJave(nazivObrazovneUstanove, predmeti, profesori, studenti, ispiti));
                obrazovnaUstanova = new VeleucilisteJave(nazivObrazovneUstanove,  predmeti, profesori, studenti, ispiti);

                sveuciliste.dodajObrazovnuUstanovu(new VeleucilisteJave(nazivObrazovneUstanove, predmeti, profesori, studenti, ispiti));

                logger.info("Naziv ustanove: " + sveuciliste.getObrazovneUstanove().get(i).getNazivUstanove());

                for(int j = 0; j < studenti.size(); j++){

                    boolean imaNegativnuOcjenu = false;

                    for(int k = 0; k < ispiti.size(); k++){
                        if(studenti.get(j).equals(ispiti.get(k).getStudenti()) && ispiti.get(k).getOcjena().equals(1)){
                            imaNegativnuOcjenu = true;
                        }
                    }

                    if(imaNegativnuOcjenu == true){
                        System.out.println("Ne moze izaci na zavrsni rad!");
                    }

                    else {
                        System.out.print("Unesite ocjenu završnog rada za studenta: " + (studenti.get(j).getIme()) +
                                (" ") + (studenti.get(j).getPrezime() + ": "));
                        Integer ocjenaZavrsnogRada = unos.nextInt();
                        Ocjena ocjenaZavrsniRad = dohvatiEnumOcjenu(ocjenaZavrsnogRada);

                        System.out.print("Unesite ocjenu obrane završnog rada za studenta: " + (studenti.get(j).getIme()) +
                                (" ") + (studenti.get(j).getPrezime() + ": "));
                        Integer ocjenaObraneZavrsnogRada = unos.nextInt();
                        Ocjena ocjenaObraneZavrsniRad = dohvatiEnumOcjenu(ocjenaObraneZavrsnogRada);

                        unos.nextLine();

                        List<Ispit> ispitStudenta = (((VeleucilisteJave) (obrazovnaUstanova)).
                                filtrirajIspitePoStudentu(ispiti, studenti.get(j)));
                        BigDecimal konacnaOcjena = ((VeleucilisteJave) (obrazovnaUstanova))
                                .izracunajKonacnuOcjenuStudijaZaStudenta(ispitStudenta, ocjenaZavrsniRad, ocjenaObraneZavrsniRad);
                        System.out.println("Konačna ocjena studija studenta " + studenti.get(j).getIme() + " " +
                                studenti.get(j).getPrezime() + ": " + konacnaOcjena);

                        logger.info("Podaci za " + sveuciliste.getObrazovneUstanove().get(i).getNazivUstanove() + ": " +
                                studenti.get(j).getIme() + " " + studenti.get(j).getPrezime() + " ocjena zavrsnog rada "
                                + ocjenaZavrsnogRada + ", ocjena obrane zavrsnog rada " + ocjenaObraneZavrsnogRada
                                + " konacna ocjena " + konacnaOcjena);
                    }
                }
                Student najboljiStudent = obrazovnaUstanova.odrediNajuspjesnijegStudentaNaGodini(GODINA);
                System.out.println("Najbolji student " + GODINA + ". godine je " + najboljiStudent.getIme() + " " +
                        najboljiStudent.getPrezime() + " JMBAG: " + najboljiStudent.getJmbag());

                logger.info("Najbolji student na godini je " + najboljiStudent.getIme() + " " +
                        najboljiStudent.getPrezime() + " " + najboljiStudent.getJmbag());

             //  return new VeleucilisteJave(nazivObrazovneUstanove, predmeti, profesori, studenti, ispiti);
            }

            else if (odabirUstanova == BROJ_ZA_FAKULTET_RACUNARSTVA){
                System.out.print("Unesite naziv obrazovne ustanove: ");
                String nazivObrazovneUstanove = unos.nextLine();


                //obrazovnaUstanova = new FakultetRacunarstva(nazivObrazovneUstanove,  predmeti, profesori, studenti, ispiti);
                //obrazovnaUstanove[i] = new FakultetRacunarstva(nazivObrazovneUstanove,  predmeti, profesori, studenti, ispiti);
               // obrazovnaUstanove.add(i, obrazovnaUstanova);

                sveuciliste.dodajObrazovnuUstanovu(new FakultetRacunarstva(nazivObrazovneUstanove,  predmeti, profesori,
                        studenti, ispiti));

                logger.info("Naziv ustanove: " + sveuciliste.getObrazovneUstanove().get(i).getNazivUstanove());

                for(int j = 0; j < studenti.size(); j++) {

                    boolean imaNegativnuOcjenu = false;

                    for (int k = 0; k < ispiti.size(); k++) {
                        if (studenti.get(j).equals(ispiti.get(k).getStudenti()) && ispiti.get(k).getOcjena().equals(1)) {
                            imaNegativnuOcjenu = true;
                        }
                    }

                    if (imaNegativnuOcjenu == true) {
                        System.out.println("Ne moze izaci na zavrsni rad!");

                    } else {
                        System.out.print("Unesite ocjenu završnog rada za studenta: " + (studenti.get(j).getIme()) +
                                (" ") + (studenti.get(j).getPrezime() + ": "));
                        Integer ocjenaZavrsnogRada = unos.nextInt();
                        Ocjena ocjenaZavrsniRad = dohvatiEnumOcjenu(ocjenaZavrsnogRada);

                        System.out.print("Unesite ocjenu obrane završnog rada za studenta: " + (studenti.get(j).getIme()) +
                                (" ") + (studenti.get(j).getPrezime() + ": "));
                        Integer ocjenaObraneZavrsnogRada = unos.nextInt();
                        Ocjena ocjenaObraneZavrsnirad = dohvatiEnumOcjenu(ocjenaObraneZavrsnogRada);

                        unos.nextLine();

                        List<Ispit> ispitStudentaFaks = (((FakultetRacunarstva) (obrazovnaUstanova)).
                                filtrirajIspitePoStudentu(ispiti, studenti.get(j)));
                        BigDecimal konacnaOcjena = ((FakultetRacunarstva) (obrazovnaUstanova))
                                .izracunajKonacnuOcjenuStudijaZaStudenta(ispitStudentaFaks, ocjenaZavrsniRad, ocjenaObraneZavrsnirad);

                        System.out.println("Konačna ocjena studija studenta " + studenti.get(j).getIme() + " " +
                                studenti.get(j).getPrezime() + ": " + konacnaOcjena);

                        logger.info("Podaci za " + sveuciliste.getObrazovneUstanove().get(i).getNazivUstanove() + ": " +
                                studenti.get(j).getIme() + " " + studenti.get(j).getPrezime() + " ocjena zavrsnog rada "
                                + ocjenaZavrsnogRada + ", ocjena obrane zavrsnog rada " + ocjenaObraneZavrsnogRada +
                                " konacna ocjena " + konacnaOcjena);

                    }
                }
                Student najboljiStudent = obrazovnaUstanova.odrediNajuspjesnijegStudentaNaGodini(GODINA);
                System.out.println("Najbolji student " + GODINA + ". godine je " + najboljiStudent.getIme() + " " +
                        najboljiStudent.getPrezime() + " JMBAG: " + najboljiStudent.getJmbag());

                Student rektorovaNagrada = ((FakultetRacunarstva)(obrazovnaUstanova)).odrediStudentaZaRektorovuNagradu();
                System.out.println("Student koji je osvojio rektorovu nagradu je: " + rektorovaNagrada.getIme() + " " +
                        rektorovaNagrada.getPrezime() + " JMBAG: " + rektorovaNagrada.getJmbag());

                logger.info("Najbolji student na godini je " + najboljiStudent.getIme() + " " +
                        najboljiStudent.getPrezime() + " " + najboljiStudent.getJmbag());

                logger.info("Student koji je osvojio rektorovu nagradu je: " + rektorovaNagrada.getIme() + " " +
                        rektorovaNagrada.getPrezime() + " JMBAG: " + rektorovaNagrada.getJmbag());

              //  return new FakultetRacunarstva(nazivObrazovneUstanove, predmeti, profesori, studenti, ispiti);
            }

        }
    }

    private static void ispisStudentaNaPredmetu() {
        for (var p : predmeti){
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

    */
/**
     * Metoda za ispis odlicnog studenta.
     *//*

    private static void ispisOdlicnogStudenta() {

        ispiti.stream()
                .filter(ispit -> ispit.getOcjena().equals(Ocjena.IZVRSTAN))
                .forEach(student -> System.out.println("Student " + (student.getStudenti().getIme()) + " " +
                (student.getStudenti().getPrezime()) + " je ostvario ocjenu 'izvrstan' na predmetu " +
                (student.getPredmet().getNaziv()) + "."));

    }

    */
/**
     * Metoda za unos ispita.
     *
     * @param unos se dobije od scannera.
     * @param predmeti
     * @param studenti
     *//*

    private static void unosIspita(Scanner unos, List<Predmet> predmeti, List<Student> studenti) {
        for(int i = 0; i < BROJ_ISPITNOGROKA; i++){
            System.out.println("Unesite " + (i + 1) + ". ispitni rok.");
            System.out.println("Odaberite predmet: ");

            for(int j = 0; j < Glavna.predmeti.size(); j++){
                System.out.println((j + 1) + ". " + Glavna.predmeti.get(j).getNaziv());
            }
            int odabraniIndeksPredmeta = 0;
            Predmet odabraniPredmet;

            Integer odabir = null;
            boolean neispravanUnos = false;

            do {
                neispravanUnos = false;
                System.out.print("Odabir >> ");

                try {
                    odabir  = unos.nextInt();
                    if(odabir < 1 || odabir > Glavna.predmeti.size()){
                        logger.error("Ne postoji takav odabir! ");
                        System.out.println("Nema takav odabir u ponudi, molimo pokušajte ponovo!");
                        neispravanUnos = true;
                    }

                } catch (InputMismatchException ex){
                    logger.error("Nije upisan broj! ", ex);
                    unos.nextLine();
                    System.out.println("Molimo ponovite odabir.");
                    neispravanUnos = true;
                }
            } while(neispravanUnos);

            odabraniPredmet = Glavna.predmeti.get(odabir - 1);

            String imeStudenta = studenti.get(i).getIme();
            String prezimeStudenta = studenti.get(i).getPrezime();
            String jmbagStudenta = studenti.get(i).getJmbag();
            LocalDate datumRodjenjaStudenta = studenti.get(i).getDatumRodjenja();

            predmeti.get(odabir-1).addStudent(new Student(imeStudenta, prezimeStudenta, jmbagStudenta, datumRodjenjaStudenta));

            unos.nextLine();

            System.out.print("Unesite naziv dvorane: ");
            String nazivDvorane = unos.nextLine();
            System.out.print("Unesite zgradu dvorane: ");
            String zgradaDvorane = unos.nextLine();

            Dvorana dvorana = new Dvorana(nazivDvorane, zgradaDvorane);

            System.out.println("Odaberite studenta: ");
            for(int k = 0; k < Glavna.studenti.size(); k++){
                System.out.println((k + 1) + ". " + Glavna.studenti.get(k).getIme() + " " + Glavna.studenti.get(k).getPrezime());
            }

            Student odabraniStudent = odabirStudentaZaIspitniRok(unos, Glavna.studenti);

            System.out.print("Unesite ocjenu na ispitu (1-5): ");

            Integer ocjena = null;

            neispravanUnos = false;

            do {
                neispravanUnos = false;

                try {
                    ocjena = unos.nextInt();

                    if(ocjena < 1 || ocjena > 5){
                        logger.error("Ne postoji takva ocjena! ");
                        System.out.println("Takva ocjena ne postoji, molimo pokušajte ponovno!");
                        neispravanUnos = true;
                    }

                } catch (InputMismatchException ex) {
                    logger.error("Nije unesen broj ili je unesena kriva ocjena! ", ex);
                    unos.nextLine();
                    neispravanUnos = true;
                    System.out.println("Neispravan unos, molimo pokušajte ponovno!");
                }

            } while(neispravanUnos);

            Ocjena ocjene = dohvatiEnumOcjenu(ocjena);


            System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm): ");

            unos.nextLine();

            String datumIVrijeme = null;
            //   LocalDateTime aLDT = LocalDateTime.parse(datumIVrijeme);
            DateTimeFormatter dtf = null;

            do {
                neispravanUnos = false;
                try {
                    datumIVrijeme = unos.nextLine();
                    dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
                    LocalDate.parse(datumIVrijeme, dtf);

                } catch (DateTimeParseException ex) {
                    logger.error("Korisnik nije napisao datum i vrijeme! ", ex);
                    System.out.println("Neispravan unos, molimo Vas da unesete datum i vrijeme! ");
                    neispravanUnos = true;
                }

            } while(neispravanUnos);

            // PARSANJE Datuma i vremena

            LocalDateTime parsiranoDatumIVrijeme = LocalDateTime.parse(datumIVrijeme, dtf);

            ispiti.add(i, new Ispit(odabraniPredmet, odabraniStudent, ocjene, parsiranoDatumIVrijeme, dvorana));
            System.out.print("Ime softvera: ");
            String imeSoftvera = unos.nextLine();
            ispiti.get(i).onlineIspit(imeSoftvera);

            logger.info("Unesen je " + (i + 1) + ". ispit u dvorani " +
                    ispiti.get(i).getDvorana().nazivDvorane() + ", zgradi " + ispiti.get(i).getDvorana().nazivZgrada() +
                    " s odabranim studentom " + odabraniStudent.getIme() + " " + odabraniStudent.getPrezime() +
                    " na datum " + ispiti.get(i).getDatumIVrijeme() + " pisan na online platformi " + imeSoftvera);

        }

    }

    private static Ocjena dohvatiEnumOcjenu(Integer ocjena) {

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


    */
/**
     * Metoda koja sluzi za odabir studenta za ispitni rok.
     *
     * @param unos se dobije od scannera.
     * @param studenti polje iz kojeg dohvacamo studenta.
     * @return
     *//*

    private static Student odabirStudentaZaIspitniRok(Scanner unos, List<Student> studenti) {
        Integer odabir = null;
        boolean neispravanUnos = false;

        do {
            neispravanUnos = false;
            System.out.print("Odabir >> ");

            try {
                odabir  = unos.nextInt();
                if(odabir < 1 || odabir > studenti.size()){
                    logger.error("Ne postoji takav odabir! ");
                    System.out.println("Nema takav odabir u ponudi, molimo pokušajte ponovo!");
                    neispravanUnos = true;
                }

            } catch (InputMismatchException ex){
                logger.error("Nije upisan broj! ", ex);
                unos.nextLine();
                System.out.println("Molimo ponovite odabir.");
                neispravanUnos = true;
            }
        } while(neispravanUnos);

        Student odabirStudenta = studenti.get(odabir - 1);
        return odabirStudenta;
    }

    */
/**
     * Metoda za unos predmeta.
     *
     * @param unos              se dobije od scannera.
     * @param predmetiProfesora
     *//*

    private static void unosPredmeta(Scanner unos, Map<Profesor, List<Predmet>> predmetiProfesora) {
        for(int i = 0; i < BROJ_PREDMETA; i++){
            System.out.println("Unesite " + (i + 1) + ". predmet. ");
            System.out.print("Unesite šifru " + (i + 1) + ". predmeta: ");
            String sifraPredmeta = unos.nextLine();
            System.out.print("Unesite naziv " + (i + 1) + ". predmeta: ");
            String nazivPredmeta = unos.nextLine();

            Integer brojEcts = null;

            boolean neispravanUnos = false;

            do {
                neispravanUnos = false;
                System.out.print("Unesite broj ECTS bodova za predmet '" + (nazivPredmeta) + "': ");

                try {
                    brojEcts = unos.nextInt();
                    unos.nextLine();

                } catch (InputMismatchException ex) {
                    logger.error("Nije unesen broj! ", ex);
                    unos.nextLine();
                    neispravanUnos = true;
                    System.out.println("Neispravan unos, molimo pokušajte ponovno!");
                }

            } while(neispravanUnos);

            System.out.println("Odaberite profesora:");
            for(int j = 0; j < profesori.size(); j++) {
                System.out.println((j + 1) + ". " + profesori.get(j).getIme() + (" ") + profesori.get(j).getPrezime());
            }
            Profesor odabirProfaZaPredmet = odabirProfesoraZaPredmet(unos, profesori);
            System.out.print("Unesite broj studenta za predmet '" + (nazivPredmeta) + "': ");
            Integer brojStudenata = unos.nextInt();
            Set<Student> popisStudentaNaPredmetu = new HashSet<>();

            unos.nextLine();

            predmeti.add(i, new Predmet(sifraPredmeta, nazivPredmeta, brojEcts, odabirProfaZaPredmet, popisStudentaNaPredmetu));
            predmetiProfesora.get(odabirProfaZaPredmet).add(new Predmet(sifraPredmeta, nazivPredmeta, brojEcts, odabirProfaZaPredmet, popisStudentaNaPredmetu));

            logger.info("Unesen je " + (i + 1) + ". predmet: " + predmeti.get(i).getSifra() + " " + predmeti.get(i).getNaziv()
                    + " " + predmeti.get(i).getBrojEctsBodova() + " ECTS sa odabranim prof. " + odabirProfaZaPredmet.getIme() + " "
                    + odabirProfaZaPredmet.getPrezime() + " prisustvuje " + brojStudenata + " studenta.");

        }
    }

    */
/**
     * Metoda za odabir profesora za predmet.
     *
     * @param unos se dobije od scannera.
     * @param profesori polje iz kojeg dohvacamo profesora.
     * @return
     *//*

    private static Profesor odabirProfesoraZaPredmet(Scanner unos, List<Profesor> profesori ) {
        Integer odabir = null;
        boolean neispravanUnos = false;

        do {
            neispravanUnos = false;
            System.out.print("Odabir >> ");

            try {
                odabir  = unos.nextInt();
                if(odabir < 1 || odabir > profesori.size()){
                    logger.error("Ne postoji takav odabir! ");
                    System.out.println("Nema takav odabir u ponudi, molimo pokušajte ponovo!");
                    neispravanUnos = true;
                }

            } catch (InputMismatchException ex){
                logger.error("Nije upisan broj! ", ex);
                unos.nextLine();
                System.out.println("Molimo ponovite odabir.");
                neispravanUnos = true;
            }
        } while(neispravanUnos);

        Profesor odabirProfesora = profesori.get(odabir - 1);
        return odabirProfesora;
    }

    */
/**
     * Metoda za unos studenata.
     *
     * @param unos se dobije od scannera.
     *//*

    private static void unosStudenta (Scanner unos){
        for(int i = 0; i < BROJ_STUDENTA; i++){
            System.out.println("Unesite " + (i + 1) + ". studenta: ");
            System.out.print("Unesite ime " + (i + 1) + ". studenta: ");
            String imeStudenta = unos.nextLine();
            System.out.print("Unesite prezime " + (i + 1) + ". studenta: ");
            String prezimeStudenta = unos.nextLine();
            System.out.print("Unesite datum rodjenja studenta " + (prezimeStudenta) + " " + (imeStudenta) +
                    " u formatu (dd.MM.yyyy.): ");
            LocalDate datumRodjenjaStudenta = addDate(unos);
            System.out.print("Unesite JMBAG studenta " + (prezimeStudenta) + " " + (imeStudenta) + ": ");
            String jmbagStudenta = unos.nextLine();

            studenti.add(i, new Student(imeStudenta, prezimeStudenta, jmbagStudenta, datumRodjenjaStudenta));

            logger.info("Unesen je " + (i + 1) + ". student: " + studenti.get(i).getJmbag() + " " + studenti.get(i).getIme()
                    + " " + studenti.get(i).getPrezime() + " " + studenti.get(i).getDatumRodjenja());

        }
    }


    */
/**
     * Metoda za formatiranje i parsiranje datuma.
     *
     * @param unos se dobije od scannera.
     * @return
     *//*

    public static LocalDate addDate(Scanner unos) {
        String str = null;
        DateTimeFormatter dtf = null;
        boolean neispravanUnos = false;
        do {
            neispravanUnos = false;
            try {
                str = unos.nextLine();
                dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                LocalDate.parse(str, dtf);

            } catch (DateTimeParseException ex) {
                logger.error("Korisnik nije napisao datum! ", ex);
                System.out.println("Neispravan unos, molimo Vas da unesete datum! ");
                neispravanUnos = true;
            }

        } while(neispravanUnos);

       // String str = unos.nextLine();
       // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        return LocalDate.parse(str, dtf);
    }

    */
/**
     * Metoda za unos profesora.
     *
     * @param unos se dobije od scannera.
     *//*

    private static void unosProfesora(Scanner unos) {
        for(int i = 0; i < BROJ_PROFESORA; i++) {
            System.out.println("Unesite " + (i + 1) + ". profesora ");
            System.out.print("Unesite šifru " + (i + 1) + ". profesora: ");
            String sifraProf = unos.nextLine();
            System.out.print("Unesite ime " + (i + 1) + ". profesora: ");
            String imeProf = unos.nextLine();
            System.out.print("Unesite prezime " + (i + 1) + ". profesora: ");
            String prezimeProf = unos.nextLine();
            System.out.print("Unesite titulu " + (i + 1) + ". profesora: ");
            String titulaProf = unos.nextLine();

            profesori.add(i, new Profesor.ProfesorBuilder()
                    .Sifra(sifraProf)
                    .Ime(imeProf)
                    .Prezime(prezimeProf)
                    .Titula(titulaProf)
                    .build());

            logger.info("Unesen je " + (i + 1) + ". profesor: " + profesori.get(i).getSifra() + " " + profesori.get(i).getIme()
                    + " " + profesori.get(i).getPrezime() + " " + profesori.get(i).getTitula());

        }
    }
}*/
