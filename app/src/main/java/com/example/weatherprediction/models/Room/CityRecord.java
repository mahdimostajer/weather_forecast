package com.example.weatherprediction.models.Room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "cityrecord_table", foreignKeys = {@ForeignKey(entity = CityBase.class,
        parentColumns = "city_id", childColumns = "ICity_id", onDelete = ForeignKey.CASCADE)})
public class CityRecord {
    @PrimaryKey(autoGenerate = true)
    private int record_id;

    @TypeConverters({Converters.class})
    private Date fetchDate;

    public Date getFetchDate() {
        return fetchDate;
    }

    public void setFetchDate(Date fetchDate) {
        this.fetchDate = fetchDate;
    }
}
