module com.example.racunarshopv1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.racunarshopv1 to javafx.fxml;
    exports com.example.racunarshopv1;
    exports com.example.racunarshopv1.controller;
    opens com.example.racunarshopv1.controller to javafx.fxml;
    opens com.example.racunarshopv1.model to javafx.base;
}