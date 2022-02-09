package com.example.racunarshopv1.model;

import javafx.scene.control.Tab;

public class Article extends Table{

    @Entity(type ="INTEGER", size = 32,primary = true)
    int id;
    @Entity(type = "VARCHAR", size = 50)
    String name;
    @Entity(type = "DECIMAL", size = 10)
    float price;
    @Entity(type = "VARCHAR", size = 25)
    String code;
    @Entity(type = "VARCHAR", size = 255)
    String desciption;
    @Entity(type ="INTEGER", size = 32)
    @ForeignKey(table = "Importer", attribute = "id")
    int importer_fk;
    @Entity(type = "INTEGER", size = 32)
    @ForeignKey(table = "Category", attribute = "id")
    int category_fk;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public Importer getImporter() throws Exception {
        return (Importer)Table.get(Importer.class, importer_fk);
    }

    public void setImporter_fk(int importer_fk) {
        this.importer_fk = importer_fk;
    }

    public Category getCategory() throws Exception {
        return (Category) Table.get(Category.class,category_fk);
    }

    public void setCategory_fk(int category_fk) {
        this.category_fk = category_fk;
    }
}
