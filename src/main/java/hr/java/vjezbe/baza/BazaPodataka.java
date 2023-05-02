package hr.java.vjezbe.baza;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.util.Datoteke;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class BazaPodataka {

    private static Connection connectToDatabase() throws SQLException, IOException {

        Properties configuration = new Properties();
        configuration.load(new FileReader("dat/database.properties"));

        String databaseURL = configuration.getProperty("databaseURL");
        String databaseUsername = configuration.getProperty("databaseUsername");
        String databasePassword = configuration.getProperty("databasePassword");

        Connection connection = DriverManager
                .getConnection(databaseURL, databaseUsername, databasePassword);

        return connection;

    }

    public static List<Profesor> dohvatiProfesoreIzBaze() throws SQLException, IOException {

        Connection connection = connectToDatabase();

        List<Profesor> listaProfesora = new ArrayList<>();

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM PROFESOR";
        ResultSet rs = statement.executeQuery(query);

        while(rs.next()){
            Long id = rs.getLong("ID");
            String sifra = rs.getString("SIFRA");
            String ime = rs.getString("IME");
            String prezime = rs.getString("PREZIME");
            String titula = rs.getString("TITULA");

            Profesor profesor = new Profesor(id, sifra, ime, prezime, titula);
            listaProfesora.add(profesor);
        }

        connection.close();

        return listaProfesora;
    }

    public static void dodajNovogProfesoraUBazu(Profesor profesor) throws SQLException, IOException {

        Connection connection = connectToDatabase();

        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO PROFESOR(IME, PREZIME, SIFRA, TITULA) VALUES  (?, ?, ?, ?)");
        ps.setString(1, profesor.getIme());
        ps.setString(2, profesor.getPrezime());
        ps.setString(3, profesor.getSifra());
        ps.setString(4, profesor.getTitula());
        ps.executeUpdate();

        connection.close();

    }

    public static List<Student> dohvatiStudenteIzBazeSRodendanom() throws SQLException, IOException {

        Connection connection = connectToDatabase();

        List<Student> listaStudenata = new ArrayList<>();

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM STUDENT WHERE DAY(DATUM_RODJENJA)=DAY(CURDATE())" +
                " AND MONTH(DATUM_RODJENJA)=MONTH(CURDATE())";
        ResultSet rs = statement.executeQuery(query);

        while(rs.next()){
            Long id = rs.getLong("ID");
            String ime = rs.getString("IME");
            String prezime = rs.getString("PREZIME");
            String jmbag = rs.getString("JMBAG");
            Date datumRodjenja = rs.getDate("DATUM_RODJENJA");

            Student student = new Student(id, ime, prezime, jmbag, datumRodjenja.toLocalDate());
            listaStudenata.add(student);
        }

        connection.close();

        return listaStudenata;

    }

    public static List<Student> dohvatiStudenteIzBaze() throws SQLException, IOException {

        Connection connection = connectToDatabase();

        List<Student> listaStudenata = new ArrayList<>();

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM STUDENT";
        ResultSet rs = statement.executeQuery(query);

        while(rs.next()){
            Long id = rs.getLong("ID");
            String ime = rs.getString("IME");
            String prezime = rs.getString("PREZIME");
            String jmbag = rs.getString("JMBAG");
            Date datumRodjenja = rs.getDate("DATUM_RODJENJA");

            Student student = new Student(id, ime, prezime, jmbag, datumRodjenja.toLocalDate());
            listaStudenata.add(student);
        }

        connection.close();

        return listaStudenata;

    }

    public static void dodajNovogStudentaUBazu(Student student) throws SQLException, IOException {

        Connection connection = connectToDatabase();

        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO STUDENT(IME, PREZIME, JMBAG, DATUM_RODJENJA) VALUES  (?, ?, ?, ?)");
        ps.setString(1, student.getIme());
        ps.setString(2, student.getPrezime());
        ps.setString(3, student.getJmbag());
        ps.setString(4, String.valueOf(student.getDatumRodjenja()));
        ps.executeUpdate();

        connection.close();

    }

    public static List<Predmet> dohvatiPredmeteIzBaze() throws SQLException, IOException {

        Connection connection = connectToDatabase();

        List<Predmet> listaPredmeta = new ArrayList<>();

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM PREDMET";
        ResultSet rs = statement.executeQuery(query);

        while(rs.next()){
            Long id = rs.getLong("ID");
            String sifra = rs.getString("SIFRA");
            String naziv = rs.getString("NAZIV");
            Integer brojEcts = rs.getInt("BROJ_ECTS_BODOVA");
            Long profesorId = rs.getLong("PROFESOR_ID");
            Set<Student> studentSet = dohvatiStudentePomocuIdIzBaze(id);
            Profesor profesor = dohvatiProfesoreIzBaze().stream()
                    .filter(profesor1 -> profesor1.getId().equals(profesorId)).collect(toSingleton());

            Predmet predmet = new Predmet(id, sifra, naziv, brojEcts, profesor, studentSet);
            listaPredmeta.add(predmet);
        }

        connection.close();

        return listaPredmeta;

    }

    public static void dodajNoviPredmetUBazu(Predmet predmet) throws SQLException, IOException {

        Connection connection = connectToDatabase();

        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO PREDMET(SIFRA, NAZIV, BROJ_ECTS_BODOVA, PROFESOR_ID) VALUES  (?, ?, ?, ?)");
        ps.setString(1, predmet.getSifra());
        ps.setString(2, predmet.getNaziv());
        ps.setInt(3, predmet.getBrojEctsBodova());
        ps.setLong(4, predmet.getNositelj().getId());

        List<Student> studenti = new ArrayList<>(predmet.getStudenti());

        ps.executeUpdate();

        for(int i = 0; i < studenti.size(); i++){
            dodajNoviPredmetStudentUBazu(predmet, studenti.get(i).getId());
        }

        connection.close();

    }

    public static void dodajNoviPredmetStudentUBazu(Predmet predmet, Long studentId) throws SQLException, IOException {

        Connection connection = connectToDatabase();

        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO PREDMET_STUDENT(PREDMET_ID, STUDENT_ID) VALUES  (?, ?)");
        ps.setLong(1, predmet.getId());
        ps.setLong(2, studentId);
        ps.executeUpdate();

        connection.close();

    }

    public static List<Ispit> dohvatiIspiteIzBaze() throws SQLException, IOException {

        Connection connection = connectToDatabase();

        List<Ispit> listaIspita = new ArrayList<>();

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM ISPIT";
        ResultSet rs = statement.executeQuery(query);

        while(rs.next()){
            Long id = rs.getLong("ID");
            Long predmet = rs.getLong("PREDMET_ID");
            Long student = rs.getLong("STUDENT_ID");
            Integer ocjena = rs.getInt("OCJENA");
            String datumIVrijeme = rs.getString("DATUM_I_VRIJEME");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime datumIVrijemeIspita = LocalDateTime.parse(datumIVrijeme, dtf);

            Predmet predmetNaIspitu = dohvatiPredmeteIzBaze().stream()
                    .filter(predmet1 -> predmet1.getId().equals(predmet)).collect(toSingleton());

            Student studentNaIspitu = dohvatiStudenteIzBaze().stream()
                    .filter(student1 -> student1.getId().equals(student)).collect(toSingleton());

            Ocjena ocjenaZaIspit = Datoteke.dohvatiOcjenu(ocjena);

            Dvorana dvorana = dohvatiDvoranePomocuIdIzBaze(id);

            Ispit ispit = new Ispit(id, predmetNaIspitu, studentNaIspitu, ocjenaZaIspit, datumIVrijemeIspita, dvorana);
            listaIspita.add(ispit);
        }

        connection.close();

        return listaIspita;

    }

    public static void dodajNoviIspitUBazu(Ispit ispit) throws SQLException, IOException {

        Connection connection = connectToDatabase();

        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO ISPIT(PREDMET_ID, STUDENT_ID, OCJENA, DATUM_I_VRIJEME) VALUES  (?, ?, ?, ?)");
        ps.setLong(1, ispit.getPredmet().getId());
        ps.setLong(2, ispit.getStudenti().getId());
        ps.setInt(3, ispit.getOcjena().ordinal()+1);
        ps.setString(4, String.valueOf(ispit.getDatumIVrijeme()));


        ps.executeUpdate();

        connection.close();

    }

    private static Dvorana dohvatiDvoranePomocuIdIzBaze(Long id) throws SQLException, IOException {

        List<Dvorana> dvorane = dohvatiDvoraneIzBaze();
        Dvorana dvorana = dvorane.get(0);
        for (Dvorana dv : dvorane){
            if(dv.id().equals(id)){
                dvorana = dv;
                break;
            }
        }
        return dvorana;
    }

    private static List<Dvorana> dohvatiDvoraneIzBaze() throws SQLException, IOException {

        Connection connection = connectToDatabase();

        List<Dvorana> listaDvorana = new ArrayList<>();

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM DVORANA";
        ResultSet rs = statement.executeQuery(query);

        while (rs.next()){
            Long id = rs.getLong("ID");
            String nazivDvorane = rs.getString("NAZIV_DVORANE");
            String nazivZgrade = rs.getString("NAZIV_ZGRADE");

            listaDvorana.add(new Dvorana(id, nazivDvorane,  nazivZgrade));

        }

        connection.close();

        return listaDvorana;
    }

    private static Set<Student> dohvatiStudentePomocuIdIzBaze(Long id) throws SQLException, IOException {

        Connection connection = connectToDatabase();

        Set<Student> setStudenata = new HashSet<>();

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM PREDMET_STUDENT";
        ResultSet rs = statement.executeQuery(query);

        while(rs.next()){
            Long predmetId = rs.getLong("PREDMET_ID");
            Long studentId = rs.getLong("STUDENT_ID");
            if(predmetId.equals(id)){
                Student student = dohvatiStudenteIzBaze().stream()
                        .filter(student1 -> student1.getId().equals(studentId)).collect(toSingleton());
                setStudenata.add(student);
            }

        }

        return setStudenata;
    }

    private static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if(list.size() != 1) {
                        System.out.println("Pronašo sam više objekta studenta s istim id.");
                    }
                    return list.get(0);
                }
        );
    }

}
