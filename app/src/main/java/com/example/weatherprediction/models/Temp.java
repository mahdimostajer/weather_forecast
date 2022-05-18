package com.example.weatherprediction.models;

public class Temp {
    public Float day;
    public Float min;
    public Float max;
    public Float night;
    public Float eve;
    public Float morn;

    public Temp(Float day, Float min, Float max, Float night, Float eve, Float morn) {
        this.day = day;
        this.min = min;
        this.max = max;
        this.night = night;
        this.eve = eve;
        this.morn = morn;
    }
}
