package hr.java.vjezbe.entitet;

import java.io.Serializable;

public class Entitet implements Serializable {

    private Long id;

    public Entitet(Long id) {
        this.id = id;
    }

    public Entitet() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
