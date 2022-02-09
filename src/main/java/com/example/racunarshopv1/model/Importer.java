package com.example.racunarshopv1.model;

public class Importer extends Table{

    @Entity(type ="INTEGER", size = 32,primary = true)
    int id;
    @Entity(type = "VARCHAR", size = 25)
    String name;
    @Entity(type = "VARCHAR", size = 50)
    String address;
    @Entity(type = "VARCHAR", size = 15)
    String phone;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
