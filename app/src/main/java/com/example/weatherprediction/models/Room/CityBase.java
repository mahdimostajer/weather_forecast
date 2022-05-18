package com.example.weatherprediction.models.Room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "city_table")
public class CityBase {
    @PrimaryKey(autoGenerate = true)
    private final int city_id;
    @NonNull
    private String name;
    @NonNull
    private Float longitude;
    @NonNull
    private Float latitude;

    public CityBase(int city_id, @NonNull String name, @NonNull Float longitude, @NonNull Float latitude) {
        this.city_id = city_id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    @NonNull
    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(@NonNull Float longitude) {
        this.longitude = longitude;
    }

    @NonNull
    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(@NonNull Float latitude) {
        this.latitude = latitude;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getCity_id() {
        return city_id;
    }
}
