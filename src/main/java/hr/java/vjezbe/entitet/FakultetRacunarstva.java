package hr.java.vjezbe.entitet;

// import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;


public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski{

 //   private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    public FakultetRacunarstva(Long id, String nazivUstanove, List<Predmet> predmeti, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti) {
        super(id, nazivUstanove, predmeti, profesori, studenti, ispiti);
    }

    @Override
    public Student odrediStudentaZaRektorovuNagradu() {
        List<Ispit> ispiti = getIspiti();
        List<Student> studenti = getStudenti();
        List<BigDecimal> prosjekOcjena = new ArrayList<>();
        BigDecimal prosjek = new BigDecimal(0);
        BigDecimal brojac = new BigDecimal(0);

        BigDecimal najboljiProsjek = BigDecimal.valueOf(0);

        for (int i = 0; i < studenti.size(); i++) {
            boolean zastavica = false;
            for (int j = 0; j < ispiti.size(); j++) {
                if(studenti.get(i).equals(ispiti.get(j).getStudenti())){
                    prosjek = prosjek.add(ispiti.get(i).getOcjena().toBigDecimal());
                    brojac = brojac.add(BigDecimal.valueOf(1));
                    zastavica = true;
                }
            }

            if(zastavica == false){
                brojac = BigDecimal.valueOf(1);
            }

            MathContext mc = new MathContext(5);

            prosjek = prosjek.divide(brojac, mc);

            prosjekOcjena.add(i, prosjek);
            if(prosjek.compareTo(najboljiProsjek) > 0){
                najboljiProsjek = prosjek;
            }
        }

        int istaOcjena = 0;
        Student najmladiStudent = studenti.get(0);
        for (int i = 0; i < prosjekOcjena.size() - 1; i++) {
            if(prosjekOcjena.get(i).equals(najboljiProsjek)) {
                for (int j = 1; j < prosjekOcjena.size(); j++) {
                    if (prosjekOcjena.get(i).equals(prosjekOcjena.get(j))) {
                        istaOcjena++;
                        if (studenti.get(i).getDatumRodjenja().isBefore(studenti.get(j).getDatumRodjenja())) {
                            najmladiStudent = studenti.get(j);
                        }
                    }
                }
            }
        }

        return najmladiStudent;
    }

    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer brojGodine) {

        int br = 0;
        int max = 0;
        int indeks = 0;

        for(int i = 0; i < getIspiti().size(); i++) {
            for (int j = 0; j < getStudenti().size(); j++) {
                int godina = getIspiti().get(j).getDatumIVrijeme().getYear();
                if (brojGodine.equals(godina)) {
                    if (getStudenti().get(i).equals(getIspiti().get(j).getStudenti()) && getIspiti().get(j).getOcjena().equals(5)) {
                        br++;
                    }
                }
            }
            if(max < br){
                max = br;
                indeks = i;
            }
        }

        return getStudenti().get(indeks);
    }

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> ispiti, Ocjena ocjenaDiplomskogRada, Ocjena ocjenaObraneDiplomskogRada) {
        BigDecimal avgOcjena;
        try {
            avgOcjena = odrediProsjekOcjenaNaIspitima(ispiti);
            return (avgOcjena.multiply(BigDecimal.valueOf(3)).add(ocjenaDiplomskogRada.toBigDecimal()).
                    add(ocjenaObraneDiplomskogRada.toBigDecimal())).divide(BigDecimal.valueOf(5));

        } catch (NemoguceOdreditiProsjekStudentaException ex) {
            Student s = ispiti.get(0).getStudenti();
        //    logger.warn("Student " + s.getIme() + " " + s.getPrezime() + "  zbog negativne ocjene na jednom od ispita " +
        //            "ima prosjek 'nedovoljan (1)'!", ex);
            return BigDecimal.ONE;
        }

    }



    @Override
    public List<Ispit> filtrirajIspitePoStudentu(List<Ispit> ispiti, Student studenti) {
        return Diplomski.super.filtrirajIspitePoStudentu(ispiti, studenti);
    }
}
