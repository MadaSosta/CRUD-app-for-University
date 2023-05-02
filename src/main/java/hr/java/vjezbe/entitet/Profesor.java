package hr.java.vjezbe.entitet;

/**
 * Sluzi za implementiranje entiteta profesora i nasljeduje klasu Osoba.
 */

public class Profesor extends Osoba {
    private String sifra;
    private String titula;

    public Profesor(Long id, String sifra, String ime, String prezime, String titula) {
        super(id, ime, prezime);
        this.sifra = sifra;
        this.titula = titula;
    }
    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getTitula() {
        return titula;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }

    @Override
    public String toString() {
        return titula + " " + getIme() + " " + getPrezime();
    }
    public static class ProfesorBuilder {
        private Long id;
        private String sifra;
        private String ime;
        private String prezime;
        private String titula;

        public ProfesorBuilder Id(Long id) {
            this.id = id;
            return this;
        }

        public ProfesorBuilder Sifra(String sifra) {
            this.sifra = sifra;
            return this;
        }

        public ProfesorBuilder Ime(String ime) {
            this.ime = ime;
            return this;
        }

        public ProfesorBuilder Prezime(String prezime) {
            this.prezime = prezime;
            return this;
        }

        public ProfesorBuilder Titula(String titula) {
            this.titula = titula;
            return this;
        }

        /**
         *  Builder kreira objekt Profesor koji sadrzi sifru, ime, prezime te titulu profesora.
         *
         * @return
         */
        public Profesor build(){
            Profesor profesor = new Profesor(id, sifra, ime, prezime, titula);
            return profesor;
        }

    }
}
