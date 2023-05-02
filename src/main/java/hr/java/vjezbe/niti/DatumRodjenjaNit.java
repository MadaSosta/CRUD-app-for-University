package hr.java.vjezbe.niti;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Student;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DatumRodjenjaNit implements Runnable {

    @Override
    public void run() {
        try {
            List<Student> rodendanStudenata = BazaPodataka.dohvatiStudenteIzBazeSRodendanom();
            if(rodendanStudenata.size() != 0){
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Čestitat rođendan studentima.");
                info.setHeaderText("Studenti koji danas imaju rođendan: ");
                info.setContentText(rodendanStudenata.toString());
                info.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
