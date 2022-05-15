package com.example.weatherprediction.ui.home;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherprediction.CityRepository;
import com.example.weatherprediction.WeatherRepository;
import com.example.weatherprediction.models.City;
import com.example.weatherprediction.models.Weather;
import com.example.weatherprediction.network.CityUtils;
import com.example.weatherprediction.network.WeatherUtils;
import com.google.gson.Gson;

public class HomeViewModel extends ViewModel {


    public MutableLiveData<City> city;
    public MutableLiveData<Weather> weather;
    public CityRepository cityRepository;
    public WeatherRepository weatherRepository;

    public HomeViewModel() {
        cityRepository = new CityRepository();
        weatherRepository = new WeatherRepository();
        city = cityRepository.city;
        weather = weatherRepository.weather;
    }


    public void getCity(String name) {
        cityRepository.getCity(name);
    }

    public void getWeather(String lat, String lon) {
        weatherRepository.getWeather(lat, lon);
    }


}