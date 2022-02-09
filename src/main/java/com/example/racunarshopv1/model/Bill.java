package com.example.racunarshopv1.model;

public class Bill extends Table{

    @Entity(type ="INTEGER", size = 32,primary = true)
    int id;
    @Entity(type = "VARCHAR", size = 25)
    String code;
}
