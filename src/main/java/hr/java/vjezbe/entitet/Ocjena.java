package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public enum Ocjena {

    NEDOVOLJAN(1, "nedovoljan"),
    DOVOLJAN(2, "dovoljan"),
    DOBAR(3, "dobar"),
    VRLO_DOBAR(4, "vrlo dobar"),
    IZVRSTAN(5, "izvrstan");

    private int vrijednostOcjene;
    private String opisOcjene;

    Ocjena(int vrijednostOcjene, String opisOcjene) {
        this.vrijednostOcjene = vrijednostOcjene;
        this.opisOcjene = opisOcjene;
    }

    public int getVrijednostOcjene() {
        return vrijednostOcjene;
    }

    public String getOpisOcjene() {
        return opisOcjene;
    }

    public BigDecimal toBigDecimal(){
        return new BigDecimal(vrijednostOcjene);
    }

}
