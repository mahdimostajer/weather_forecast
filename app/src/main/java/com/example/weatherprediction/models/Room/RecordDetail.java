package com.example.weatherprediction.models.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecordDetail {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CityRecordDetail record);

    @Query("SELECT * FROM cityrecorddetail_table where (latitude = (select latitude from city_table where name = :cityName limit 1) and longitude = (select longitude from city_table where name = :cityName limit 1)) order by dayNumber Asc")
    List<CityRecordDetail> getCity(String cityName);

    @Query("select * from cityrecorddetail_table where latitude = :lat and longitude = :lon order by dayNumber Asc")
    List<CityRecordDetail> getCoordinate(Float lat, Float lon);
}
