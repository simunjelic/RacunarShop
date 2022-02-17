package com.example.racunarshopv1.controller;

import com.example.racunarshopv1.Main;
import com.example.racunarshopv1.model.Article;
import com.example.racunarshopv1.model.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.example.racunarshopv1.Main.primaryStage;

public class BillController implements Initializable {

    @FXML
    private TextField traziTxt;
    @FXML
    protected Label dateTxt;
    @FXML
    private Label totalTxt;
    @FXML
    protected Label userTxt;

    @FXML
    private TableColumn traziCodeTbl;

    @FXML
    private TableColumn traziImeCol;

    @FXML
    private TableColumn traziPriceCol;

    @FXML
    private TableView traziTbl;

    @FXML
    private TableColumn billCodeCol;

    @FXML
    private TableColumn billImeCol1;

    @FXML
    private TableColumn billPriceCol1;

    @FXML
    private TableColumn billQunatityCol;

    @FXML
    private TableView billTbl;
    @FXML
            Button addBtn;
    @FXML
            Button deleteBtn;
    @FXML
            TextField qunatityTxt;
    @FXML
            TextField backMoneyTxt;
    @FXML
            Label backMoneyLabel;

    ObservableList articleObsList;
    Article selectedFindArticle=null;
    Article selectedBillArticle=null;
    float total =0;
    int qunatity = 1;
    ObservableList billArticleList = FXCollections.observableArrayList();
    int billCheck;



 // vraćamo se u izbornik
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


    }
    // prosljeđuje korisnika ovom controlleru
    String user = "test";
    public void pass (String s){
        this.user=s;
        this.userTxt.setText(user);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.traziCodeTbl.setCellValueFactory(new PropertyValueFactory<>("code"));
        this.traziImeCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.traziPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.billCodeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        this.billImeCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.billPriceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.billQunatityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));


        this.fillArticle();
        this.filterArticle();
        this.showDate();



    }
    private void fillArticle() {
        try {
            List<?> articleList = Table.list(Article.class);
            articleObsList = FXCollections.observableList(articleList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void filterArticle () {
        FilteredList<Article> filterData = new FilteredList(articleObsList, b->true);

        traziTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(article -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyWord = newValue.toLowerCase();

                if(article.getCode().toLowerCase().indexOf(searchKeyWord)> -1) {
                    return true;

                } else if (article.getName().toLowerCase().indexOf(searchKeyWord)>-1){
                    return true;
                }
                else {
                    return false;
                }
            });
        });
        SortedList <Article> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(traziTbl.comparatorProperty());
        this.traziTbl.setItems(sortedData);
    }

    @FXML
    private void selectTraziArticle() {
        this.selectedFindArticle = (Article)traziTbl.getSelectionModel().getSelectedItem();
    }
    @FXML
    private void selectBillArticle() {
        this.selectedBillArticle = (Article)billTbl.getSelectionModel().getSelectedItem();
        String quantityStr = Integer.toString(selectedBillArticle.getQuantity());
        this.qunatityTxt.setText(quantityStr);
    }
    // dodaje proizvod na racun
    @FXML
    private void billAdd() {

        if(selectedFindArticle!=null) {
            total += this.selectedFindArticle.getPrice();
            String totalStr = Float.toString(total);
            totalTxt.setText(totalStr);

            billArticleList.add(selectedFindArticle);
            billTbl.setItems(billArticleList);
            selectedFindArticle = null;
        }

    }
    // brise proizvod sa racuna
    @FXML
    private void billDelete() {
            if(selectedBillArticle!=null) {
                Float price = this.selectedBillArticle.getPrice();
                qunatity = this.selectedBillArticle.getQuantity();
                total -= (qunatity*price);
                String totalStr = Float.toString(total);
                totalTxt.setText(totalStr);

                selectedBillArticle.setQuantity(1);
                billArticleList.remove(selectedBillArticle);
                billTbl.refresh();;
                this.selectedBillArticle=null;

            }
    }

    @FXML
    protected void removeSelection() {
        this.fillArticle();
        this.filterArticle();
        this.selectedFindArticle=null;
        this.selectedBillArticle=null;


    }
    @FXML
    void changeQunatity(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER && selectedBillArticle!= null) {
            String s = qunatityTxt.getText();

            int v = -1;
            try {

                v = Integer.parseInt(s);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Nije valjana vrijednost količine");
            }
            if (v > 0) {
                int oldQunatity = selectedBillArticle.getQuantity();
                selectedBillArticle.setQuantity(v);
                billTbl.refresh();
                int diffrence;
                if (oldQunatity< v) {
                    diffrence = v-oldQunatity;
                    diffrence *= selectedBillArticle.getPrice();
                    total += diffrence;
                    String totStr = Float.toString(total);
                    this.totalTxt.setText(totStr);
                } else if (oldQunatity>v) {
                    diffrence = oldQunatity-v;
                    diffrence*= selectedBillArticle.getPrice();
                    total -= diffrence;
                    String totStr = Float.toString(total);
                    this.totalTxt.setText(totStr);
                }
            }else if (v==0){
                this.billDelete();
            }
            this.selectedBillArticle=null;
        }
    }
    @FXML
    protected void pay() {
        if(billArticleList!= null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Da li želite izdati račun", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            alert.setHeaderText("POTVRDI");

            if (alert.getResult() == ButtonType.YES) {
                ArrayList <Article> a = new ArrayList<>(billTbl.getItems());
                String fileName = "C:\\Users\\korisnik\\Documents\\bills\\";
                String date = dateTxt.getText();
                fileName += date+".pdf";







                this.ponisti();
            }
        }
    }
    @FXML
    void backMoney(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                String moneyS = backMoneyTxt.getText();
                float money = Float.parseFloat(moneyS);
                money = money - total;
                moneyS = String.valueOf(money);
                backMoneyLabel.setText(moneyS);
            } catch (Exception e) {
                System.out.println("Netočan podatak");
                backMoneyLabel.setText("ERROR");
            }

        }
    }
    @FXML
    protected void ponisti () {
        billArticleList.clear();
        backMoneyLabel.setText("");
        backMoneyTxt.setText("");
        qunatityTxt.setText("");
        total =0;
        totalTxt.setText("");

    }

    @FXML
    void showDate () {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter datef = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        String a = datef.format(date);
        dateTxt.setText(a);

    }




}



