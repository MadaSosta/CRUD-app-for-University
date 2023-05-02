package hr.java.vjezbe.entitet;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Sluzi za implementiranje entiteta studenata i nasljeduje klasu Osoba.
 */
public class Student extends Osoba {
    private String ime;
    private String prezime;
    private String jmbag;
    private LocalDate datumRodjenja;


    /**
     * Kreira objekt Student koji sadrzi ime, prezime, jmbag i datum rodjenja studenta.
     *
     * @param ime
     * @param prezime
     * @param jmbag
     * @param datumRodjenja
     */
    public Student(Long id, String ime, String prezime, String jmbag, LocalDate datumRodjenja) {
        super(id, ime, prezime);
        this.ime = ime;
        this.prezime = prezime;
        this.jmbag = jmbag;
        this.datumRodjenja = datumRodjenja;
    }


    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getJmbag() {
        return jmbag;
    }

    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return jmbag.equals(student.jmbag) && datumRodjenja.equals(student.datumRodjenja);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag, datumRodjenja);
    }

    @Override
    public String toString() {
        return  ime + " " + prezime;
    }
}
