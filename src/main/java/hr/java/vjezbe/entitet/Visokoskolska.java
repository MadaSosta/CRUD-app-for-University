package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Visokoskolska {

    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta (List<Ispit> ispiti, Ocjena ocjenaPismenog, Ocjena ocjenaZavrsnog);

    public default BigDecimal odrediProsjekOcjenaNaIspitima(List<Ispit> ispitiArray) throws NemoguceOdreditiProsjekStudentaException {
        BigDecimal prosjekOcjena = BigDecimal.valueOf(0);
        BigDecimal sum = new BigDecimal(0);
        Integer br = 0;

        for(int i = 0; i < ispitiArray.size(); i++){
            if(!ispitiArray.get(i).getOcjena().equals(1)){
                sum = sum.add(ispitiArray.get(i).getOcjena().toBigDecimal());
               // sum = sum + ispitiArray[i].getOcjena();
                br++;
            }
            else {
                throw new NemoguceOdreditiProsjekStudentaException("Student " +
                        ispitiArray.get(i).getStudenti().getIme() + " " + ispitiArray.get(i).getStudenti().getPrezime() +
                        " je ocjenjen negativom ocjenom iz predmeta "+ ispitiArray.get(i).getPredmet().getNaziv() +
                        " (" + ispitiArray.get(i).getPredmet().getSifra() + ")!");
            }
        }

        prosjekOcjena = sum.divide(new BigDecimal(br));

        return prosjekOcjena;
    }

    private List<Ispit> filtrirajPolozeneIspite (List<Ispit> ispiti) {
        List<Ispit> filtriraniPolozeniIspit = new ArrayList<>();
        int brojac = 0;
        for(int i = 0; i < ispiti.size(); i++){
            if(!ispiti.get(i).getOcjena().equals(1)){
                filtriraniPolozeniIspit.add(brojac, ispiti.get(i));
                brojac++;
            }
        }
        return filtriraniPolozeniIspit;
    }

    public default List<Ispit> filtrirajIspitePoStudentu(List<Ispit> ispiti, Student studenti){
        return ispiti.stream()
                .filter(ispit -> ispit.getStudenti().equals(studenti))
                .toList();

    }

}
