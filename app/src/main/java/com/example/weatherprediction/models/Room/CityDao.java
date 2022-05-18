package com.example.weatherprediction.models.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CityBase city);

    @Query("SELECT * from city_table WHERE name=:name ORDER BY name")
    LiveData<List<CityBase>> getAllCitiesByName(String name);

    @Query("SELECT * from city_table WHERE longitude=:longitude AND latitude=:latitude ORDER BY name")
    LiveData<List<CityBase>> getAllCitiesByCoordination(Float longitude, Float latitude);
}
