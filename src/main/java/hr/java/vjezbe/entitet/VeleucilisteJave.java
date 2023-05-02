package hr.java.vjezbe.entitet;

// import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska{

  //  private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    public VeleucilisteJave(Long id, String nazivUstanove, List<Predmet> predmeti, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti) {
        super(id, nazivUstanove, predmeti, profesori, studenti, ispiti);
    }

    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer brojGodine) {
        List<Ispit> ispiti = getIspiti();
        List<Student> studenti = getStudenti();

        int brojIspita = 0;
        for (int i = 0; i < ispiti.size(); i++) {
            if(ispiti.get(i).getDatumIVrijeme().getYear() == brojGodine){
                brojIspita++;
            }
        }
        List<Ispit> ispitiTeGodine = new ArrayList<>();
        int br = 0;
        for (int i = 0; i < ispiti.size(); i++) {
            if(ispiti.get(i).getDatumIVrijeme().getYear() == brojGodine){
                ispitiTeGodine.add(br, ispiti.get(i));
                br++;
            }
        }

        Student najuspjesnijiStudent = getStudenti().get(0);


        for (int i = 0; i < getStudenti().size() - 1; i++) {
            if (brojGodine.equals(getStudenti().get(i).getDatumRodjenja().getYear())) {

                List<Ispit> filtriraniIspitiPoStudentu = (filtrirajIspitePoStudentu(ispitiTeGodine, studenti.get(i)));

                for (int j = 1; j < getStudenti().size(); j++) {
                    List<Ispit> filtriraniIspitiPoStudentuUpdate = (filtrirajIspitePoStudentu(ispitiTeGodine, studenti.get(j)));
                    BigDecimal prosjekOcjenaIspita = BigDecimal.valueOf(0);

                    try {
                        prosjekOcjenaIspita = odrediProsjekOcjenaNaIspitima((filtriraniIspitiPoStudentuUpdate));

                    } catch (NemoguceOdreditiProsjekStudentaException ex) {
                //        logger.warn("Student " + getStudenti().get(i).getIme() + " " + getStudenti().get(i).getPrezime() +
                //                " zbog negativne ocjene na jednom od ispita " + "ima prosjek 'nedovoljan (1)'!", ex);
                    }

                    if (prosjekOcjenaIspita.compareTo(prosjekOcjenaIspita) > 0) {
                        najuspjesnijiStudent = getStudenti().get(i);
                    }
                    if (prosjekOcjenaIspita.equals(prosjekOcjenaIspita)) {
                        najuspjesnijiStudent = getStudenti().get(j);
                    }
                }

            }
        }

        return najuspjesnijiStudent;
    }

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> ispitiPolja, Ocjena ocjenaZavrsnogRada, Ocjena ocjenaObraneZavrsnogRada) {
        BigDecimal prosjekOcjena;

        try {
            prosjekOcjena = odrediProsjekOcjenaNaIspitima(ispitiPolja);
            return (prosjekOcjena.multiply(BigDecimal.valueOf(2)).add(ocjenaZavrsnogRada.toBigDecimal()).
                    add(ocjenaObraneZavrsnogRada.toBigDecimal())).divide(BigDecimal.valueOf(4));

        } catch (NemoguceOdreditiProsjekStudentaException ex) {
            Student s = ispitiPolja.get(0).getStudenti();
        //    logger.warn("Student " + s.getIme() + " " + s.getPrezime() + "  zbog negativne ocjene na jednom od ispita " +
        //            "ima prosjek 'nedovoljan (1)'!", ex);
            return BigDecimal.ONE;
        }

    }

    @Override
    public List<Ispit> filtrirajIspitePoStudentu(List<Ispit> ispiti, Student studenti) {
        List<Ispit> filtriraniIspit = new ArrayList<>();
        int brojac = 0;
        for(int i = 0; i < ispiti.size(); i++){
            if(ispiti.get(i).getStudenti().equals(studenti)){
                filtriraniIspit.add(brojac, ispiti.get(i));
                brojac++;
            }
        }
      // Pogle  filtriraniIspit = Arrays.copyOf(filtriraniIspit, brojac);
        return filtriraniIspit;
    }
}
