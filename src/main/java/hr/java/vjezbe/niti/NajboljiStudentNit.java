package hr.java.vjezbe.niti;

import com.example.madarcic_sostaric7.HelloApplication;
import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Entitet;
import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.entitet.Visokoskolska;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class NajboljiStudentNit implements Runnable{

    @Override
    public void run() {
        Map<Student, Double> prosjeciStudenata = new HashMap<>();
        Student najboljiStudent = null;
        Double maxProsjek;

        List<Student> listaStudenata;

        {
            try {
                listaStudenata = BazaPodataka.dohvatiStudenteIzBaze();
                List<Ispit> filtriraniIspiti = new ArrayList<>();

                for(Student student : listaStudenata){
                    filtriraniIspiti = BazaPodataka.dohvatiIspiteIzBaze();
                    if(filtriraniIspiti.size() > 0) {
                        prosjeciStudenata.put(student, filtriraniIspiti.stream()
                                .mapToDouble(ispit -> ispit.getOcjena().getVrijednostOcjene()).average().getAsDouble());
                    }
                }

                maxProsjek = prosjeciStudenata.values().stream().max(Comparator.naturalOrder()).get();
                if(maxProsjek > 0 && prosjeciStudenata.size() > 0) {
                    for(Student student : prosjeciStudenata.keySet()){
                        if(prosjeciStudenata.get(student) == maxProsjek){
                            najboljiStudent = student;
                        }
                    }
                    HelloApplication.setMainStageTitle(String.format("Najbolji student: " + najboljiStudent + " " + maxProsjek));
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
