package com.example.racunarshopv1.controller;

import com.example.racunarshopv1.Main;
import com.example.racunarshopv1.model.*;
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


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    protected TextField buyerTxt;

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
    String user = "admin";
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
                this.createFIle();
                this.saveBill();
                this.ponisti();
            }
        }
    }
    String fileBillName () {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter datef = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm");
        String dateStr = datef.format(date);
        String buyer = buyerTxt.getText();
        buyer = buyer.replace(" ", "_");
        dateStr = buyer+"_"+dateStr;
        return dateStr;
    }
    protected void createFIle() {
        ArrayList <Article> a = new ArrayList<>(billTbl.getItems());
        String dirPath = System.getProperty("user.home") + "\\Documents\\bills";
        File mk = new File(dirPath);
        mk.mkdir();
        String fileName = this.fileBillName();
        String myDocumentPath = System.getProperty("user.home") + "\\Documents\\bills\\"+fileName+".doc";
        //String fileName = "C:\\Users\\korisnik\\Documents\\bills\\"+dateStr+".doc";

        try {

            File file = new File(myDocumentPath);
            if(!file.exists()){

                file.createNewFile();

            }
              PrintWriter pw = new PrintWriter(file);
                pw.println("                       ComputerShop\n\n");
                pw.println("Datum: "+dateTxt.getText());
                pw.println("\nRadnik: "+user+"\n");
                pw.println("Kupac: "+buyerTxt.getText()+"\n\n");
                for (int i =0; i<a.size();i++){
                    pw.println(a.get(i).getName()+" \n");
                    pw.println("          Količina : "+a.get(i).getQuantity()+"   Cijena: "+a.get(i).getPrice()+"\n");
                }
                pw.println("\n\n");
                pw.println("Ukupno: "+this.total+" KM");
                pw.close();
                System.out.println("Done");

        } catch (IOException e) {
            System.out.println(e.getMessage()+ "Nije kreiran file");
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
    void saveBill () {
        ArrayList <Article> a = new ArrayList<>(billTbl.getItems());
        Bill bill = new Bill();
        String code = this.fileBillName();
        bill.setCode(code);
        int userId = this.getUserId();
        try {
            bill.save();
        } catch (Exception e) {
            System.out.println(e.getMessage()+" Nije kreiran račun");
        }
        Date date = new Date();
        for (int i = 0 ; i < a.size(); i++){
            Article_Bill ab = new Article_Bill();
            ab.setDate(date);
            ab.setQuantity(a.get(i).getQuantity());
            ab.setBill_fk(bill.getId());
            ab.setArticle_fk(a.get(i).getId());
            ab.setUser_fk(userId);
            try {
                ab.save();
            } catch (Exception e) {
                System.out.println(e.getMessage()+"Nije spremljen racun_artikl");
            }

        }


    }
    int  getUserId () {
        String SQL = "SELECT id FROM user WHERE name = '"+ user +"' ";
        Statement stmt = null;
        int userID=0;
        try {
            stmt = Database.CONNECTION.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            if(rs.next()){
                userID = rs.getInt(1);
            }else System.out.println("greska nije dobar upit");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "Nije dohvaćen ID iz baze");
        }
        return userID;

    }




}



