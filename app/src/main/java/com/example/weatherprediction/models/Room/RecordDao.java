package com.example.weatherprediction.models.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecordDao {
    @Insert
    void insert(CityRecord record);

    @Query("SELECT * from cityrecord_table INNER JOIN city_table WHERE city_id = :city_id ORDER BY record_id")
    LiveData<List<CityRecord>> getAllRecords(int city_id);
}
