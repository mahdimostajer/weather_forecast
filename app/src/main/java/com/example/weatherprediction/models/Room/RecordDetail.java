package com.example.weatherprediction.models.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecordDetail {
    @Insert
    void insert(CityRecordDetail record);

    @Query("SELECT * FROM cityrecorddetail_table INNER JOIN cityrecord_table on record_id WHERE record_id = :record_id ORDER BY dt")
    LiveData<List<CityRecordDetail>> getAllDetails(int record_id);
}
