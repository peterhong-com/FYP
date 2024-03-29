package com.example.groceries;

import java.util.ArrayList;

public class User {
    private String name;
    private String password;


    private ArrayList<ArrayList<String>> List;


    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
    public ArrayList<ArrayList<String>> getList() {
        return List;
    }

    public void setList(ArrayList<ArrayList<String>> list) {
        List = list;
    }

}
