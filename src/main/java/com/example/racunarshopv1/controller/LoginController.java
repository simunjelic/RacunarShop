package com.example.racunarshopv1.controller;


import com.example.racunarshopv1.Main;
import com.example.racunarshopv1.model.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.racunarshopv1.Main.primaryStage;


public class LoginController {


    Parent root;
    @FXML
    private Label errorMsgName;

    @FXML
    private Label errorMsgPass;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private Label pr;

    @FXML
    private TextField usernameTxt;

    @FXML
    void onLogin() throws IOException {
        String username = this.usernameTxt.getText().toString();
        String password = this.passwordTxt.getText().toString();

        if (username.equals("") || password.equals("")){
            this.errorMsgName.setText("Morate unijeti sva polja!");
        }
        else {
            this.errorMsgName.setText("");
            this.errorMsgPass.setText("");

            try {
                String SQL = "SELECT * FROM user WHERE name = '" + username +"' and password = '"+password+"'";
                Statement stmt = Database.CONNECTION.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);

                if(rs.next()){

                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menu.fxml"));
                    root = fxmlLoader.load();
                    MenuController menuCtrl = fxmlLoader.getController();
                    menuCtrl.pass(username);
                    primaryStage.setScene(new Scene(root, 600, 400));
                    primaryStage.setTitle("Izbornik");
                    primaryStage.show();
                    /*
                    Main.showWindow(
                            "menu.fxml",
                            "Glavni izbornik", 600, 400);

                     */


                }
                else {
                    this.errorMsgName.setText("Pogrešni korisnički podatci");
                    this.passwordTxt.setText("");
                    this.errorMsgPass.setText("");
                }



            } catch (SQLException e) {
                System.out.println(e.getMessage());

            }




        }
    }
}