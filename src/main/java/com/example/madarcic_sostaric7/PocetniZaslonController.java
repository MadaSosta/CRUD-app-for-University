package com.example.madarcic_sostaric7;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class PocetniZaslonController {

    public void showPocetniZaslon() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pocetni-zaslon.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        HelloApplication.getMainStage().setTitle("Fakultet");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

}
