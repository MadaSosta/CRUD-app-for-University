package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.util.List;

public abstract class ObrazovnaUstanova extends Entitet implements Serializable {

    private String nazivUstanove;
    private List<Predmet> predmeti;
    private List<Profesor> profesori;
    private List<Student> studenti;
    private List<Ispit> ispiti;

    public ObrazovnaUstanova(Long id, String nazivUstanove, List<Predmet> predmeti, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti) {
        super(id);
        this.nazivUstanove = nazivUstanove;
        this.predmeti = predmeti;
        this.profesori = profesori;
        this.studenti = studenti;
        this.ispiti = ispiti;
    }

    public String getNazivUstanove() {
        return nazivUstanove;
    }

    public void setNazivUstanove(String nazivUstanove) {
        this.nazivUstanove = nazivUstanove;
    }

    public List<Predmet> getPredmeti() {
        return predmeti;
    }

    public void setPredmeti(List<Predmet> predmeti) {
        this.predmeti = predmeti;
    }

    public List<Profesor> getProfesori() {
        return profesori;
    }

    public void setProfesori(List<Profesor> profesori) {
        this.profesori = profesori;
    }

    public List<Student> getStudenti() {
        return studenti;
    }

    public void setStudenti( List<Student> studenti) {
        this.studenti = studenti;
    }

    public int getBrojStudenta() {
        return this.studenti.size();
    }

    public List<Ispit> getIspiti() {
        return ispiti;
    }

    public void setIspiti(List<Ispit> ispiti) {
        this.ispiti = ispiti;
    }

    @Override
    public String toString() {
        return "ObrazovnaUstanova{" +
                "nazivUstanove='" + nazivUstanove + '\'' +
                ", predmeti=" + predmeti +
                ", profesori=" + profesori +
                ", studenti=" + studenti +
                ", ispiti=" + ispiti +
                '}';
    }

    public abstract Student odrediNajuspjesnijegStudentaNaGodini(Integer brojGodine);


}
