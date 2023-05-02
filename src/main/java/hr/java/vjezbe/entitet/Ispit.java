package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Sluzi za implementiranje ispitnih entiteta i implementira sucelje Online.
 */
public final class Ispit extends Entitet implements Online {

    private Predmet predmet;
    private Student studenti;
    private Ocjena ocjena;
    private LocalDateTime datumIVrijeme;
    private Dvorana dvorana;


    /**
     * Kreira objekt Ispit koji sadrzi predmete, studente, ocjene, datum i vrijeme i dvoranu za ispit.
     *
     * @param predmet
     * @param studenti
     * @param ocjena
     * @param datumIVrijeme
     * @param dvorana
     */
    public Ispit(Long id, Predmet predmet, Student studenti, Ocjena ocjena, LocalDateTime datumIVrijeme, Dvorana dvorana) {
        super(id);
        this.predmet = predmet;
        this.studenti = studenti;
        this.ocjena = ocjena;
        this.datumIVrijeme = datumIVrijeme;
        this.dvorana = dvorana;
    }

    public Dvorana getDvorana() {
        return dvorana;
    }

    public void setDvorana(Dvorana dvorana) {
        this.dvorana = dvorana;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public Student getStudenti() {
        return studenti;
    }

    public void setStudenti(Student studenti) {
        this.studenti = studenti;
    }

    public Ocjena getOcjena() {
        return ocjena;
    }

    public void setOcjena(Ocjena ocjena) {
        this.ocjena = ocjena;
    }

    public LocalDateTime getDatumIVrijeme() {
        return datumIVrijeme;
    }

    public void setDatumIVrijeme(LocalDateTime datumIVrijeme) {
        this.datumIVrijeme = datumIVrijeme;
    }

    /**
     * Metoda za ispis online softvera.
     *
     * @param imeSoftvera predstavlja naziv softvera
     */
    @Override
    public void onlineIspit(String imeSoftvera) {
        System.out.println("Ispit se pise online pomocu softvera " + imeSoftvera);
    }

    @Override
    public String toString() {
        return "Ispit{" +
                "predmet=" + predmet +
                ", studenti=" + studenti +
                ", ocjena=" + ocjena +
                ", datumIVrijeme=" + datumIVrijeme +
                ", dvorana=" + dvorana +
                '}';
    }
}
