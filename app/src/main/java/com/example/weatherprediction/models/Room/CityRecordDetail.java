package com.example.weatherprediction.models.Room;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "cityrecorddetail_table", foreignKeys = {@ForeignKey(entity = CityBase.class,
        parentColumns = "record_id", childColumns = "iRecord", onDelete = ForeignKey.CASCADE)})
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

    @NonNull
    public Float tempDay;

    @Nullable
    public Float tempNight;

    @Nullable
    public Float tempEvening;

    @Nullable
    public Float tempMorning;

    @NonNull
    public Float dewPoint;

    @NonNull
    public Float moonPhase;

    @NonNull
    public Float feelsLike;

    @NonNull
    public Float uvi;

    @NonNull
    public Float clouds;

    @NonNull
    public Float pop;

    @NonNull
    public Float windSpeed;

    @NonNull
    public int humidity;

    @NonNull
    public int windDeg;

    @NonNull
    public int pressure;

    @NonNull
    public int visibility;

    @NonNull
    public String description;

    @NonNull
    public String weatherMain;


    public CityRecordDetail(int detail_id, Date dt, Date sunRise, Date sunSet, @NonNull Float tempDay, @NonNull Float dewPoint, @NonNull Float feelsLike, int humidity, @NonNull String description, @NonNull String weatherMain, Float tempMorning, Float tempEvening, Float tempNight) {
        this.detail_id = detail_id;
        this.dt = dt;
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
    }
}
