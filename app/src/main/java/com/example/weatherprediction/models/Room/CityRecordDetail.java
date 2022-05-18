package com.example.weatherprediction.models.Room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "cityrecorddetail_table", primaryKeys = {"longitude","latitude","dayNumber"})
public class CityRecordDetail {

    @TypeConverters({Converters.class})
    public Date dt;

    @TypeConverters({Converters.class})
    public Date sunRise;

    @TypeConverters({Converters.class})
    public Date sunSet;

    @TypeConverters({Converters.class})
    public Date moonRise;

    @TypeConverters({Converters.class})
    public Date moonSet;

    public Float tempDay;

    public Float tempNight;

    public Float tempEvening;

    public Float tempMorning;

    public Float dewPoint;

    public Float moonPhase;

    public Float feelsLike;

    public Float uvi;

    public Float clouds;

    public Float pop;

    public Float windSpeed;

    public Float humidity;

    public int windDeg;

    public Float pressure;

    public int visibility;

    public String description;

    public String weatherMain;

    public Float wind_deg;

    public Float min_temp;

    public Float max_temp;

    @NonNull
    public Float longitude;

    @NonNull
    public Float latitude;

    @TypeConverters({Converters.class})
    public Date fetchDate;

    public String icon;
    @NonNull
    public int dayNumber;


    public CityRecordDetail(Date dt,
                            Date sunRise,
                            Date sunSet,
                            Float tempDay,
                            @NonNull Float dewPoint,
                            Float feelsLike,
                            Float humidity,
                            @NonNull String description,
                            @NonNull String weatherMain,
                            Float tempMorning,
                            Float tempEvening,
                            Float tempNight,
                            @NonNull Float longitude,
                            @NonNull Float latitude,
                            Date fetchDate,
                            Float pressure,
                            Float windSpeed,
                            Float wind_deg,
                            Float min_temp,
                            Float max_temp,
                            String icon,
                            int dayNumber) {
        this.dt = dt;
        this.pressure = pressure;
        this.sunRise = sunRise;
        this.sunSet = sunSet;
        this.tempDay = tempDay;
        this.tempEvening = tempEvening;
        this.tempNight = tempNight;
        this.tempMorning = tempMorning;
        this.dewPoint = dewPoint;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
        this.description = description;
        this.weatherMain = weatherMain;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fetchDate = fetchDate;
        this.windSpeed = windSpeed;
        this.wind_deg = wind_deg;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.icon = icon;
        this.dayNumber = dayNumber;
    }
}
