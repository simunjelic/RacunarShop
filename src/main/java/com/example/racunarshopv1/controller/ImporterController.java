package com.example.racunarshopv1.controller;

import com.example.racunarshopv1.Main;
import com.example.racunarshopv1.model.Importer;
import com.example.racunarshopv1.model.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.racunarshopv1.Main.primaryStage;

public class ImporterController implements Initializable {

    @FXML
    private Button addImporterBtn;

    @FXML
    private Button deleteImporterBtn;

    @FXML
    private TextField importerAdressTxt;

    @FXML
    private TextField importerNameTxt;

    @FXML
    private TextField importerPhoneTxtx;

    @FXML
    Label errorMsg;


    @FXML
    private TableView importerTbl;

    @FXML
    private TableColumn ImporterAddressCol;

    @FXML
    private TableColumn importerIDcol;

    @FXML
    private TableColumn importerNameCol;

    @FXML
    private TableColumn importerPhoneCol;

    Importer selectedImporter=null;

    @FXML

    protected void addImporter (){
        String name = this.importerNameTxt.getText();
        String address = this.importerAdressTxt.getText();
        String phone = this.importerPhoneTxtx.getText();

        if(!(name.equals("")||address.equals(""))){

            if(this.selectedImporter==null) {
                Importer i = new Importer();
                i.setName(name);
                i.setAddress(address);
                i.setPhone(phone);

                try {
                    i.save();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                this.errorMsg.setText("");
                this.importerNameTxt.setText("");
                this.importerAdressTxt.setText("");
                this.importerPhoneTxtx.setText("");
                this.fillImporter();
            } else {
                this.selectedImporter.setName(name);
                this.selectedImporter.setAddress(address);
                this.selectedImporter.setPhone(phone);
                try {
                    this.selectedImporter.update();
                    this.removeSelection();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

        } else {
            this.errorMsg.setText("Niste unijeli sva polja");
        }
    }
    String user;
    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menu.fxml"));
        root = fxmlLoader.load();
        MenuController menuCtrl = fxmlLoader.getController();
        menuCtrl.pass(user);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setTitle("Menu");
        primaryStage.show();
        /*
        Main.showWindow(
                "menu.fxml",
                "Administracija kategorija", 600, 400);

         */
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.importerIDcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.importerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.ImporterAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.importerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        this.fillImporter();
    }
    private void fillImporter () {
        try {
            List<?> importerList = Table.list(Importer.class);
            ObservableList importerObsList = FXCollections.observableList(importerList);
            this.importerTbl.setItems(importerObsList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    protected void deleteImporter() {
        if(this.selectedImporter!=null){
            try {
                this.selectedImporter.delete();
                this.fillImporter();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else this.errorMsg.setText("Niste unijeli uvoznika");

    }
    @FXML
    protected void selectImporter() {
        this.selectedImporter = (Importer)this.importerTbl.getSelectionModel().getSelectedItem();
        this.addImporterBtn.setText("Uredi uvoznika");
        this.importerNameTxt.setText(selectedImporter.getName());
        this.importerAdressTxt.setText(selectedImporter.getAddress());
        this.importerPhoneTxtx.setText(selectedImporter.getPhone());
        this.errorMsg.setText("");
    }
    @FXML
    protected void removeSelection() {
        this.fillImporter();
        this.selectedImporter=null;
        this.addImporterBtn.setText("Dodaj uvoznika");
        this.importerNameTxt.setText("");
        this.importerAdressTxt.setText("");
        this.importerPhoneTxtx.setText("");
        this.errorMsg.setText("");
    }
    public void pass (String s){
        this.user=s;

    }
}
