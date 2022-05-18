package com.example.weatherprediction.models;

import java.util.List;

public class Weather {
    public Today current;
    public List<Day> daily;
    public float lat;
    public float lon;

    public Weather(Today current, List<Day> daily, float lat, float lon) {
        this.current = current;
        this.daily = daily;
        this.lat = lat;
        this.lon = lon;
    }
}
