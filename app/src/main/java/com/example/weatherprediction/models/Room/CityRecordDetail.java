package com.example.weatherprediction.models.Room;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "cityrecorddetail_table")
public class CityRecordDetail {
    @PrimaryKey(autoGenerate = true)
    public final int detail_id;

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
    @ColumnInfo(name = "city_name")
    public String cityName;

    @NonNull
    public Float longitude;

    @NonNull
    public Float latitude;

    @TypeConverters({Converters.class})
    public Date fetchDate;

    public String icon;


    public CityRecordDetail(int detail_id, Date dt, Date sunRise, Date sunSet, @NonNull Float tempDay, @NonNull Float dewPoint, @NonNull Float feelsLike, Float humidity, @NonNull String description, @NonNull String weatherMain, Float tempMorning, Float tempEvening, Float tempNight, @NonNull String cityName, @NonNull Float longitude, @NonNull Float latitude, Date fetchDate, Float pressure, Float wind_deg, Float min_temp, Float max_temp, String icon) {
        this.detail_id = detail_id;
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
        this.cityName = cityName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fetchDate = fetchDate;
        this.wind_deg = wind_deg;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.icon = icon;
    }
}
