package com.example.racunarshopv1.controller;

import com.example.racunarshopv1.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MenuController {

    @FXML
    void Category(ActionEvent event) throws IOException {
        Main.showWindow(
                "category.fxml",
                "Administracija kategorija", 592, 400);
    }
    @FXML
    void importer(ActionEvent event) throws IOException {
        Main.showWindow(
                "importer.fxml",
                "Administracija kategorija", 645, 465);

    }
    @FXML
    void artikl(ActionEvent event) throws IOException {
        Main.showWindow(
                "article.fxml",
                "Administracija kategorija", 870, 555);
    }
}
