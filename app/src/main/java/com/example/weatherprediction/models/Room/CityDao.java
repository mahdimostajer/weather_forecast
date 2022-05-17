package com.example.weatherprediction.models.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CityDao {
    @Insert
    void insert(CityBase city);

    @Query("SELECT * from city_table ORDER BY name")
    LiveData<List<CityBase>> getAllCities();
}
