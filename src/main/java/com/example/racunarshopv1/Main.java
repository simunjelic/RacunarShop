package com.example.racunarshopv1;

import com.example.racunarshopv1.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    public static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        Main.primaryStage = stage;
        Main.showWindow(
                "login.fxml",
                "Prijavite se na sustav", 600, 340);
    }

    /* public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("category.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 190);
        stage.setTitle("Dodaj novu kategoriju!");
        stage.setScene(scene);
        stage.show();
    }

     */
    public static void showWindow(String viewName, String title, int w, int h) throws IOException {
        FXMLLoader root = new FXMLLoader(Main.class.getResource(viewName));
        primaryStage.setTitle(title);
        primaryStage.setScene(new Scene(root.load(), w, h));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {





        launch();
    }
}