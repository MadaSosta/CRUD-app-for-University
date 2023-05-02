package hr.java.vjezbe.entitet;

/**
 * Sluzi za implementiranje entiteta osobe.
 */

abstract class Osoba extends Entitet {

    private String ime;
    private String prezime;

    /**
     * Kreira objekt Osoba koji sadrzi ime i prezime osobe.
     *
     * @param ime
     * @param prezime
     */
    public Osoba(Long id, String ime, String prezime) {
        super(id);
        this.ime = ime;
        this.prezime = prezime;
    }

    public Osoba() {
        super();
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
}
