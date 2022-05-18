package com.example.weatherprediction.models.Room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "cityrecord_table", foreignKeys = {@ForeignKey(entity = CityBase.class,
        parentColumns = "city_id", childColumns = "iCity_id", onDelete = ForeignKey.CASCADE)})
public class CityRecord {
    @PrimaryKey(autoGenerate = true)
    private int record_id;

    @TypeConverters({Converters.class})
    private Date fetchDate;

    @ColumnInfo(index = true)
    public int iCity_id;

    public Date getFetchDate() {
        return fetchDate;
    }

    public void setFetchDate(Date fetchDate) {
        this.fetchDate = fetchDate;
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }
}
