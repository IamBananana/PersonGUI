module com.example.grafiskpersonapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.grafiskpersonapp to javafx.fxml;
    exports com.example.grafiskpersonapp;
}