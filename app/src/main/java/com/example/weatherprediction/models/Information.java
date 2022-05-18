package com.example.weatherprediction.models;

public class Information {
    public Integer id;
    public String main;
    public String description;
    public String icon;

    public Information(Integer id, String main, String description, String icon) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }
}
