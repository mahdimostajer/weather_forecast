package com.example.weatherprediction.models.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {CityBase.class, CityRecord.class, CityRecordDetail.class}, version = 9, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class WeatherDataBase extends RoomDatabase {
    public abstract CityDao cityDao();
    public abstract RecordDetail recordDetail();

    private static WeatherDataBase instance;

    public static WeatherDataBase getInstance(Context context) {
        if (instance != null) return instance;
        synchronized (WeatherDataBase.class) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        WeatherDataBase.class,
                        "weather_database"
                ).fallbackToDestructiveMigration()
                        .build();
            }
        }
        return instance;
    }
}