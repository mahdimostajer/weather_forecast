package com.example.weatherprediction.models;


import java.util.List;

public class Day {
    public int dt;
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

    public Day(int dt, int sunrise, int sunset, Float moonrise, Float moonset, Float moon_phase, Float pressure, Float humidity, Float dew_point, Float wind_speed, Float wind_deg, Float wind_gust, Float clouds, Float pop, Float rain, Float uvi, Temp temp, List<Information> weather) {
        this.dt = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.moonrise = moonrise;
        this.moonset = moonset;
        this.moon_phase = moon_phase;
        this.pressure = pressure;
        this.humidity = humidity;
        this.dew_point = dew_point;
        this.wind_speed = wind_speed;
        this.wind_deg = wind_deg;
        this.wind_gust = wind_gust;
        this.clouds = clouds;
        this.pop = pop;
        this.rain = rain;
        this.uvi = uvi;
        this.temp = temp;
        this.weather = weather;
    }
}
