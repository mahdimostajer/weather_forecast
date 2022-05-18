package com.example.weatherprediction.models;


import java.util.List;

public class Day {
    public Float dt;
    public int sunrise;
    public int sunset;
    public Float moonrise;
    public Float moonset;
    public Float moon_phase;
    public Float pressure;
    public Float humidity;
    public Float dew_point;
    public Float wind_speed;
    public Float wind_deg;
    public Float wind_gust;
    public Float clouds;
    public Float pop;
    public Float rain;
    public Float uvi;

    public Temp temp;
    public List<Information> weather;
}
