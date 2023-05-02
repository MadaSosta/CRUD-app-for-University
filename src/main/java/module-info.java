module com.example.madarcic_sostaric7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.madarcic_sostaric7 to javafx.fxml;
    exports com.example.madarcic_sostaric7;
}