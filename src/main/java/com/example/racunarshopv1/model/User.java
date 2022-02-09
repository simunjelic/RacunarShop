package com.example.racunarshopv1.model;

public class User extends Table{

    @Entity(type ="INTEGER", size = 32,primary = true)
    int id;
    @Entity(type = "VARCHAR", size = 25)
    int name;
    @Entity(type = "VARCHAR", size = 50)
    int email;
    @Entity(type = "VARCHAR", size = 100)
    String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getEmail() {
        return email;
    }

    public void setEmail(int email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
