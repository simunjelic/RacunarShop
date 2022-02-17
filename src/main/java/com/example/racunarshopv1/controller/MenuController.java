package com.example.racunarshopv1.controller;

import com.example.racunarshopv1.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.racunarshopv1.Main.primaryStage;

public class MenuController {

    @FXML
        protected Label menuTxt;
    @FXML
        protected Label userTxt;
        String user;

    @FXML
    void Category(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("category.fxml"));
        root = fxmlLoader.load();
        CategoryController menuCtrl = fxmlLoader.getController();
        menuCtrl.pass(user);
        primaryStage.setScene(new Scene(root, 592, 400));
        primaryStage.setTitle("Kategorije");
        primaryStage.show();
        /*
        Main.showWindow(
                "category.fxml",
                "Administracija kategorija", 592, 400);

         */
    }
    @FXML
    void importer(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("importer.fxml"));
        root = fxmlLoader.load();
        ImporterController menuCtrl = fxmlLoader.getController();
        menuCtrl.pass(user);
        primaryStage.setScene(new Scene(root, 645, 465));
        primaryStage.setTitle("Uvoznik");
        primaryStage.show();

        /*
        Main.showWindow(
                "importer.fxml",
                "Administracija uvoznika", 645, 465);

         */

    }
    @FXML
    void artikl(ActionEvent event) throws IOException {

        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("article.fxml"));
        root = fxmlLoader.load();
        ArticleController menuCtrl = fxmlLoader.getController();
        menuCtrl.pass(user);
        primaryStage.setScene(new Scene(root, 870, 555));
        primaryStage.setTitle("Proizvodi");
        primaryStage.show();

        /*
        Main.showWindow(
                "article.fxml",
                "Administracija proizvoda", 870, 555);

         */
    }
    @FXML
    void bill(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("bill.fxml"));
        root = fxmlLoader.load();
        BillController menuCtrl = fxmlLoader.getController();
        menuCtrl.pass(user);
        primaryStage.setScene(new Scene(root, 1089, 556));
        primaryStage.setTitle("Izdavanje racuna");
        primaryStage.show();

        /*
        Main.showWindow(
                "bill.fxml",
                "Izdavanje računa", 1089, 556);

         */
    }
    public void pass(String usr) {
        this.user = usr;
        this.userTxt.setText(user);

    }


}
