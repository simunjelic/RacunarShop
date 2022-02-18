package com.example.racunarshopv1.model;

public class Bill extends Table{

    @Entity(type ="INTEGER", size = 32,primary = true)
    int id;
    @Entity(type = "VARCHAR", size = 100)
    String code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
