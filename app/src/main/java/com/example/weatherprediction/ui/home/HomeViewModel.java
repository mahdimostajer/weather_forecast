package com.example.weatherprediction.ui.home;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherprediction.CityRepository;
import com.example.weatherprediction.MainRepository;
import com.example.weatherprediction.models.City;
import com.example.weatherprediction.models.Weather;
import com.example.weatherprediction.network.CityUtils;
import com.example.weatherprediction.network.WeatherUtils;
import com.google.gson.Gson;

public class HomeViewModel extends AndroidViewModel {


    public MutableLiveData<City> city;
    public MutableLiveData<Weather> weather;
    public CityRepository cityRepository;
    public MainRepository weatherRepository;

    public HomeViewModel(Application application) {
        super(application);
        cityRepository = new CityRepository(application);
        weatherRepository = new MainRepository(application);
        city = cityRepository.city;
        weather = weatherRepository.weather;
    }


    public void getCity(String name) {
        cityRepository.getCity(name);
    }

    public void getWeather(String lat, String lon) {
        weatherRepository.getWeather(lat, lon);
    }

    public void getCacheCity(String name){
        weatherRepository.getCacheCity(name);
    }

    public void getCacheWeather(String lat, String lon){
        weatherRepository.getCacheWeather(lat, lon);
    }


}