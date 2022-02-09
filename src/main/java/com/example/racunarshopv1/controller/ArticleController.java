package com.example.racunarshopv1.controller;

import com.example.racunarshopv1.Main;
import com.example.racunarshopv1.model.Article;
import com.example.racunarshopv1.model.Category;
import com.example.racunarshopv1.model.Importer;
import com.example.racunarshopv1.model.Table;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class ArticleController implements Initializable {


    @FXML
    private TextField articleCodeTxt;

    @FXML
    private TextField articleNameTxt;

    @FXML
    private TextArea articleOpisTxt;

    @FXML
    private TextField articlePriceTxt;

    @FXML
    private ComboBox categorySelect;

    @FXML
    private ComboBox importerSelect;

    @FXML
    private TableView articleTbl;

    @FXML
    private TableColumn colAricleCategory;

    @FXML
    private TableColumn colArticleCode;

    @FXML
    private TableColumn colArticleID;

    @FXML
    private TableColumn colArticleImporter;

    @FXML
    private TableColumn colArticleName;

    @FXML
    private TableColumn colArticlePrice;
    @FXML
            Button addArticleBtn;

    Article selectedArticle = null;

    @FXML
    void back(ActionEvent event) throws IOException {
        Main.showWindow(
                "menu.fxml",
                "Administracija kategorija", 600, 400);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // pretvaranje objekta da prikaze samo name value
        this.categorySelect.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category usr) {
                return usr == null ? "" : usr.getName();
            }


            @Override
            public Category fromString(String s) {
                Category usr = new Category();
                return usr;
            }
        });
        // da prikaze samo name value importera
        this.importerSelect.setConverter(new StringConverter<Importer>() {
            @Override
            public String toString(Importer usr) {
                return usr == null ? "" : usr.getName();
            }


            @Override
            public Importer fromString(String s) {
                Importer usr = new Importer();
                return usr;
            }
        });

        this.setTableView();
        this.addCategories();
        this.fillImporter();
        this.fillArticle();

    }
    public void setTableView () {
        this.colArticleID.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.colArticleName.setCellValueFactory(new  PropertyValueFactory<>("name"));
        this.colArticleCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        this.colArticlePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        // da prikaze ime starnog kljuca
        this.colAricleCategory.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Article, String>, ObservableValue<String>>) param -> {
            SimpleStringProperty a=null;
            try {
                a= new SimpleStringProperty(param.getValue().getCategory().getName());
            } catch (Exception e) {
                e.getMessage();
            }
            return a;
        });
        // da prikaze ime starnog kljuca
        this.colArticleImporter.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Article, String>, ObservableValue<String>>) param -> {
            SimpleStringProperty a=null;
            try {
                a= new SimpleStringProperty(param.getValue().getImporter().getName());
            } catch (Exception e) {
                e.getMessage();
            }
            return a;
        });
    }
    public void addCategories() {
        List<?> categoriesList = null;
        try {
            categoriesList = Table.list(Category.class);
            ObservableList<?> categoriesObservableList = FXCollections.observableList(categoriesList);
            this.categorySelect.setItems(categoriesObservableList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    private void fillImporter () {
        try {
            List<?> importerList = Table.list(Importer.class);
            ObservableList importerObsList = FXCollections.observableList(importerList);
            this.importerSelect.setItems(importerObsList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void fillArticle() {
        try {
            List <?> articleList = Table.list(Article.class);
            ObservableList articleObsList = FXCollections.observableList(articleList);
            this.articleTbl.setItems(articleObsList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    protected void addArticle(){


        String name = this.articleNameTxt.getText();
        float price=0;
        try {
            price = parseFloat(this.articlePriceTxt.getText());
        } catch (NumberFormatException e){
            System.out.println("Niste unijeli broj");

        }

        String code = this.articleCodeTxt.getText();
        String desc = this.articleOpisTxt.getText();

        Category cat = null;
        cat = (Category) this.categorySelect.getSelectionModel().getSelectedItem();
            int cat_fk;
        if (cat==null)cat_fk = 0;
        else cat_fk = cat.getId();
        Importer imp = null;
        imp = (Importer)this.importerSelect.getSelectionModel().getSelectedItem();
        int imp_fk;
        if(imp==null)imp_fk = 0;
       else imp_fk= imp.getId();


        if (name.equals("")||code.equals("") || price==0||imp_fk == 0||cat_fk == 0){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Niste popunili sva polja!", ButtonType.OK);
            alert.setTitle("Upozorenje");
            alert.setHeaderText("Greška s unosom!");
            alert.showAndWait();

        }else {
            if(this.selectedArticle==null){
            Article a = new Article();
            a.setName(name);
            a.setPrice(price);
            a.setCode(code);
            a.setDesciption(desc);
            a.setCategory_fk(cat_fk);
            a.setImporter_fk(imp_fk);

            try {
                a.save();

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Nije kreiran proizvod");
            }
            addCategories();
            fillImporter();
            this.fillArticle();
            this.articleNameTxt.setText("");
            this.articlePriceTxt.setText("");
            this.articleCodeTxt.setText("");
            this.articleOpisTxt.setText("");
            this.categorySelect.setPromptText("Kategorija");
            this.importerSelect.setPromptText("Uvoznik");
            }else {
                this.selectedArticle.setName(name);
                this.selectedArticle.setPrice(price);
                this.selectedArticle.setCode(code);
                this.selectedArticle.setDesciption(desc);
                this.selectedArticle.setCategory_fk(cat_fk);
                this.selectedArticle.setImporter_fk(imp_fk);
                try {
                    this.selectedArticle.update();
                } catch (Exception e) {
                    System.out.println(e.getMessage()+"Niste uredili aritkl");
                }

                this.removeSelection();
            }
        }


    }

    @FXML void deleteArticle () {
        if(this.selectedArticle!=null) {
            try {
                this.selectedArticle.delete();
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }
            this.fillArticle();
        }
    }
    @FXML
    void selectArticle () {
        this.selectedArticle = (Article)articleTbl.getSelectionModel().getSelectedItem();
        this.articleNameTxt.setText(this.selectedArticle.getName());
        this.articlePriceTxt.setText(String.valueOf(this.selectedArticle.getPrice()));
        this.articleCodeTxt.setText(this.selectedArticle.getCode());
        this.articleOpisTxt.setText(this.selectedArticle.getDesciption());
        try {
            this.categorySelect.getSelectionModel().select(this.selectedArticle.getCategory());
            this.importerSelect.getSelectionModel().select(this.selectedArticle.getImporter());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.addArticleBtn.setText("Uredi kategoriju");
    }
    @FXML
    public void removeSelection() {
        this.fillArticle();
        this.selectedArticle=null;
        this.articleNameTxt.setText("");
        this.articlePriceTxt.setText("");
        this.articleCodeTxt.setText("");
        this.articleOpisTxt.setText("");
        this.categorySelect.getSelectionModel().clearSelection();
        this.categorySelect.setPromptText("Kategorija");
        this.importerSelect.getSelectionModel().clearSelection();
        this.importerSelect.setPromptText("Uvoznik");
        this.addArticleBtn.setText("Dodaj kategoriju");
    }





}
