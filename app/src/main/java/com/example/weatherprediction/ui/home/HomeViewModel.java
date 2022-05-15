package com.example.weatherprediction.ui.home;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherprediction.CityRepository;
import com.example.weatherprediction.models.City;
import com.example.weatherprediction.models.Weather;
import com.example.weatherprediction.network.CityUtils;
import com.example.weatherprediction.network.WeatherUtils;
import com.google.gson.Gson;

public class HomeViewModel extends ViewModel {


    public MutableLiveData<City> city;
    public MutableLiveData<Weather> weather = new MutableLiveData<>();
    public CityRepository cityRepository;

    public HomeViewModel() {
        cityRepository = new CityRepository();
        city = cityRepository.city;
    }


    public void getCity(String name) {
        cityRepository.getCity(name);
    }

    public void getWeather(String lat, String lon) {
        new fetchWeather(weather).execute(lat, lon);
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
            this.weather.setValue(newWeather);
        }

        @Override
        protected String doInBackground(String... params) {
            WeatherUtils weatherUtils = new WeatherUtils();
            return weatherUtils.getWeather(params[0], params[1]);
        }
    }

}