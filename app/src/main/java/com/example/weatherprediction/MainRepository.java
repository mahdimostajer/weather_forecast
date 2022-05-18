package com.example.weatherprediction;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.weatherprediction.models.Day;
import com.example.weatherprediction.models.Information;
import com.example.weatherprediction.models.Room.CityRecordDetail;
import com.example.weatherprediction.models.Room.Converters;
import com.example.weatherprediction.models.Room.RecordDetail;
import com.example.weatherprediction.models.Room.WeatherDataBase;
import com.example.weatherprediction.models.Temp;
import com.example.weatherprediction.models.Today;
import com.example.weatherprediction.models.Weather;
import com.example.weatherprediction.network.WeatherUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainRepository {
    public MutableLiveData<Weather> weather = new MutableLiveData<>();
    private RecordDetail dao;

    public MainRepository(Application application) {
        WeatherDataBase weatherDataBase = WeatherDataBase.getInstance(application);
        dao = weatherDataBase.recordDetail();
    }

    public void getWeather(String lat, String lon) {
        new fetchWeather(weather).execute(lat, lon);
    }

    public void getCacheWeather(String lat, String lon) {
        new CacheWeather(dao, weather).execute(lat, lon);


    }

    public void getCacheCity(String cityName) {
        new CacheCity(dao, weather).execute(cityName);
    }

    public class fetchWeather extends AsyncTask<String, Void, String> {
        MutableLiveData<Weather> weather;

        public fetchWeather(MutableLiveData<Weather> weather) {
            this.weather = weather;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            Weather newWeather = gson.fromJson(s, Weather.class);
            new insertAsyncTask(dao).execute(newWeather);
            this.weather.setValue(newWeather);
        }

        @Override
        protected String doInBackground(String... params) {
            WeatherUtils weatherUtils = new WeatherUtils();
            return weatherUtils.getWeather(params[0], params[1]);
        }
    }

    private static class insertAsyncTask extends AsyncTask<Weather, Void, Void> {

        private RecordDetail mAsyncTaskDao;

        insertAsyncTask(RecordDetail dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Weather... params) {
            Weather weather = params[0];
            String cityName = weather.timezone.toLowerCase().replace("_", " ").substring(weather.timezone.indexOf("/") + 1);
            CityRecordDetail cityRecordDetail = new CityRecordDetail(0,
                    new Date(weather.current.dt),
                    new Date(weather.current.sunrise),
                    new Date(weather.current.sunset),
                    weather.current.temp,
                    weather.current.dew_point,
                    weather.current.feels_like,
                    weather.current.humidity,
                    weather.current.weather.get(0).description,
                    weather.current.weather.get(0).main,
                    (float) 0,
                    (float) 0,
                    (float) 0,
                    cityName,
                    weather.lon,
                    weather.lat,
                    new Date(),
                    weather.current.pressure,
                    weather.current.wind_deg,
                    (float) 0,
                    (float) 0,
                    weather.current.weather.get(0).icon);
            mAsyncTaskDao.insert(cityRecordDetail);

            for (Day day : weather.daily) {
                CityRecordDetail newDay = new CityRecordDetail(0,
                        new Date(day.dt),
                        new Date(day.sunrise),
                        new Date(day.sunset),
                        day.temp.day,
                        day.dew_point,
                        (float) 0,
                        day.humidity,
                        day.weather.get(0).description,
                        day.weather.get(0).main,
                        day.temp.morn,
                        day.temp.eve,
                        day.temp.night,
                        cityName,
                        weather.lon,
                        weather.lat,
                        new Date(),
                        day.pressure,
                        day.wind_deg,
                        day.temp.min,
                        day.temp.max,
                        day.weather.get(0).icon);
                mAsyncTaskDao.insert(newDay);
            }
            return null;
        }
    }

    private static class CacheWeather extends AsyncTask<String, Void, List<CityRecordDetail>> {
        private RecordDetail mAsyncTaskDao;
        public MutableLiveData<Weather> weather;

        CacheWeather(RecordDetail dao, MutableLiveData<Weather> weather) {
            mAsyncTaskDao = dao;
            this.weather = weather;
        }

        @Override
        protected List<CityRecordDetail> doInBackground(final String... params) {
            List<CityRecordDetail> list = mAsyncTaskDao.getCoordinate(Float.valueOf(params[0]), Float.valueOf(params[1]));
            return list;
        }

        @Override
        protected void onPostExecute(List<CityRecordDetail> list) {
            super.onPostExecute(list);
            if (list.size() > 0) {
                CityRecordDetail first = list.get(0);
                Information info = new Information(0, first.description, first.weatherMain, first.icon);

                Today current = new Today(Converters.dateToTimestamp(first.dt).intValue(),
                        Converters.dateToTimestamp(first.sunRise).intValue(),
                        Converters.dateToTimestamp(first.sunSet).intValue(),
                        first.tempDay,
                        first.feelsLike,
                        first.pressure,
                        first.humidity,
                        first.dewPoint,
                        first.uvi,
                        first.clouds,
                        first.clouds,
                        first.windSpeed,
                        first.wind_deg,
                        Arrays.asList(info));

                List<Day> daily = new ArrayList<>();
                for (int i = 1; i < list.size(); i++) {
                    CityRecordDetail mCurrent = list.get(i);
                    Temp newTemp = new Temp(mCurrent.tempDay, mCurrent.min_temp, mCurrent.max_temp, mCurrent.tempNight, mCurrent.tempEvening, mCurrent.tempMorning);
                    Information newInfo = new Information(0, mCurrent.weatherMain, mCurrent.description, mCurrent.icon);
                    daily.add(new Day(Converters.dateToTimestamp(mCurrent.dt).intValue(),
                            Converters.dateToTimestamp(mCurrent.sunRise).intValue(),
                            Converters.dateToTimestamp(mCurrent.sunSet).intValue(),
                            null,
                            null,
                            null,
                            mCurrent.pressure,
                            mCurrent.humidity,
                            mCurrent.dewPoint,
                            mCurrent.windSpeed,
                            mCurrent.wind_deg,
                            mCurrent.wind_deg,
                            mCurrent.clouds,
                            null,
                            null,
                            mCurrent.uvi,
                            newTemp,
                            Arrays.asList(newInfo)));
                }
                Weather newWeather = new Weather(current, daily, first.latitude, first.longitude, first.cityName);
                weather.setValue(newWeather);
            }
        }
    }


    private static class CacheCity extends AsyncTask<String, Void, List<CityRecordDetail>> {
        private RecordDetail mAsyncTaskDao;
        public MutableLiveData<Weather> weather;

        CacheCity(RecordDetail dao, MutableLiveData<Weather> weather) {
            mAsyncTaskDao = dao;
            this.weather = weather;
        }

        @Override
        protected List<CityRecordDetail> doInBackground(final String... params) {
            return mAsyncTaskDao.getCity(params[0]);

        }

        @Override
        protected void onPostExecute(List<CityRecordDetail> list) {
            super.onPostExecute(list);
            if (list.size() > 0) {
                CityRecordDetail first = list.get(0);
                Information info = new Information(0, first.description, first.weatherMain, first.icon);

                Today current = new Today(Converters.dateToTimestamp(first.dt).intValue(),
                        Converters.dateToTimestamp(first.sunRise).intValue(),
                        Converters.dateToTimestamp(first.sunSet).intValue(),
                        first.tempDay,
                        first.feelsLike,
                        first.pressure,
                        first.humidity,
                        first.dewPoint,
                        first.uvi,
                        first.clouds,
                        first.clouds,
                        first.windSpeed,
                        first.wind_deg,
                        Arrays.asList(info));

                List<Day> daily = new ArrayList<>();
                for (int i = 1; i < list.size(); i++) {
                    CityRecordDetail mCurrent = list.get(i);
                    Temp newTemp = new Temp(mCurrent.tempDay, mCurrent.min_temp, mCurrent.max_temp, mCurrent.tempNight, mCurrent.tempEvening, mCurrent.tempMorning);
                    Information newInfo = new Information(0, mCurrent.weatherMain, mCurrent.description, mCurrent.icon);
                    daily.add(new Day(Converters.dateToTimestamp(mCurrent.dt).intValue(),
                            Converters.dateToTimestamp(mCurrent.sunRise).intValue(),
                            Converters.dateToTimestamp(mCurrent.sunSet).intValue(),
                            null,
                            null,
                            null,
                            mCurrent.pressure,
                            mCurrent.humidity,
                            mCurrent.dewPoint,
                            mCurrent.windSpeed,
                            mCurrent.wind_deg,
                            mCurrent.wind_deg,
                            mCurrent.clouds,
                            null,
                            null,
                            mCurrent.uvi,
                            newTemp,
                            Arrays.asList(newInfo)));
                }
                Weather newWeather = new Weather(current, daily, first.latitude, first.longitude, first.cityName);
                weather.setValue(newWeather);
            }
        }
    }
}
