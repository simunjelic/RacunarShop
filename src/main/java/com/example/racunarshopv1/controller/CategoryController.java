package com.example.racunarshopv1.controller;

import com.example.racunarshopv1.Main;
import com.example.racunarshopv1.model.Category;
import com.example.racunarshopv1.model.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {

    @FXML
    TextField categoryNameTxt;
    @FXML
    Label errorCat;
    @FXML
    TableView categoryTbl;

    @FXML
    TableColumn categoryIDCol;

    @FXML
    TableColumn categoryNameCol;

    @FXML
    Button addCategoryBtn;

    Category selectedCategory=null;



    @FXML

    protected void addCategory(){
        String name = this.categoryNameTxt.getText();
        if (!name.equals("")){
            if(this.selectedCategory==null){
                Category c = new Category();
                c.setName(name);

                try {
                    c.save();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } this.errorCat.setText("");
                this.categoryNameTxt.setText("");
                this.fillCategories();

            }else {
                this.selectedCategory.setName(name);
                try {
                    this.selectedCategory.update();
                    this.removeSelection();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

        } else this.errorCat.setText("Niste unijeli naziv kategorije");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.categoryIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.categoryNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.fillCategories();
    }
    private void fillCategories(){
        try {
            List<?> categoriesList = Table.list(Category.class);
            ObservableList<?> categoriesObservableList = FXCollections.observableList(categoriesList);
            this.categoryTbl.setItems(categoriesObservableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    @FXML protected void deleteCategory(){

        if(selectedCategory!=null){
            try {
                selectedCategory.delete();
                this.fillCategories();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }else this.errorCat.setText("Niste odabrali kategoriju");
    }
    @FXML void selectCategory() {
        this.selectedCategory = (Category)this.categoryTbl.getSelectionModel().getSelectedItem();
        this.addCategoryBtn.setText("Uredi kategoriju");
        this.categoryNameTxt.setText(this.selectedCategory.getName());
        this.errorCat.setText("");


    }

    @FXML
    protected void removeSelection() {
        this.fillCategories();
        this.selectedCategory=null;
        this.addCategoryBtn.setText("Dodaj kategoriju");
        this.categoryNameTxt.setText("");
        this.errorCat.setText("");
    }
    @FXML
    void back(ActionEvent event) throws IOException {
        Main.showWindow(
                "menu.fxml",
                "Administracija kategorija", 600, 400);
    }

}
