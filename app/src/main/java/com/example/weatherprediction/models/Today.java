package com.example.weatherprediction.models;

import java.util.List;

public class Today {
    public int dt;
    public int sunrise;
    public int sunset;
    public Float temp;
    public Float feels_like;
    public Float pressure;
    public Float humidity;
    public Float dew_point;
    public Float uvi;
    public Float clouds;
    public Float visibility;
    public Float wind_speed;
    public Float wind_deg;

    public List<Information> weather;

    public Today(int dt, int sunrise, int sunset, Float temp, Float feels_like, Float pressure, Float humidity, Float dew_point, Float uvi, Float clouds, Float visibility, Float wind_speed, Float wind_deg, List<Information> weather) {
        this.dt = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.temp = temp;
        this.feels_like = feels_like;
        this.pressure = pressure;
        this.humidity = humidity;
        this.dew_point = dew_point;
        this.uvi = uvi;
        this.clouds = clouds;
        this.visibility = visibility;
        this.wind_speed = wind_speed;
        this.wind_deg = wind_deg;
        this.weather = weather;
    }


}
