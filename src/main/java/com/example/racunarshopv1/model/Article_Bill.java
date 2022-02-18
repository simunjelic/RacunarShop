package com.example.racunarshopv1.model;

import java.util.Date;

public class Article_Bill extends Table{

    @Entity(type ="INTEGER", size = 32,primary = true)
    int id;
    @Entity(type ="DATE",isnull = false)
    Date date;
    @Entity(type ="SMALLINT", size = 6)
    int quantity;
    @Entity(type ="INTEGER", size = 32)
    @ForeignKey(table = "User", attribute = "id")
    int user_fk;
    @Entity(type ="INTEGER", size = 32)
    @ForeignKey(table = "Bill", attribute = "id")
    int bill_fk;
    @Entity(type ="INTEGER", size = 32)
    @ForeignKey(table = "Article", attribute = "id")
    int article_fk;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() throws Exception {
        return (User)Table.get(User.class,user_fk);
    }

    public void setUser_fk(int user_fk) {
        this.user_fk = user_fk;
    }

    public Bill getBill() throws Exception {
        return (Bill)Table.get(Bill.class,bill_fk);
    }

    public void setBill_fk(int bill_fk) {
        this.bill_fk = bill_fk;
    }

    public Article getArticle() throws Exception {
        return (Article)Table.get(Article.class,article_fk) ;
    }

    public void setArticle_fk(int article_fk) {
        this.article_fk = article_fk;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
