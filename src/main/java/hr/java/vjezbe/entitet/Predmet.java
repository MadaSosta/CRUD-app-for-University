package hr.java.vjezbe.entitet;

import java.util.List;
import java.util.Set;

/**
 * Sluzi za implementiranje entiteta predmeta.
 */

public class Predmet extends Entitet {

    private String sifra;
    private String naziv;
    private Integer brojEctsBodova;
    private Profesor nositelj;
    private Set<Student> studenti;

    /**
     * Kreira objekt Predmet koji sadrzi sifru, naziv, broj ECTS bodova, nositelje i studente na predmetu.
     *
     * @param sifra
     * @param naziv
     * @param brojEctsBodova
     * @param nositelj
     * @param studenti
     */
    public Predmet(Long id, String sifra, String naziv, Integer brojEctsBodova, Profesor nositelj, Set<Student> studenti) {
        super(id);
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
        this.studenti = studenti;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getBrojEctsBodova() {
        return brojEctsBodova;
    }

    public void setBrojEctsBodova(Integer brojEctsBodova) {
        this.brojEctsBodova = brojEctsBodova;
    }

    public Profesor getNositelj() {
        return nositelj;
    }

    public void setNositelj(Profesor nositelj) {
        this.nositelj = nositelj;
    }

    public Set<Student> getStudenti() {
        return studenti;
    }

    public void setStudenti(Set<Student> studenti) {
        this.studenti = studenti;
    }

    public void addStudent(Student student){ this.studenti.add(student);}

    @Override
    public String toString() {
        return sifra + " " + naziv;
    }
}
