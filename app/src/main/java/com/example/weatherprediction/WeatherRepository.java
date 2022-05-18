package com.example.weatherprediction;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.weatherprediction.models.Weather;
import com.example.weatherprediction.network.WeatherUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class WeatherRepository {

    public MutableLiveData<Weather> weather = new MutableLiveData<>();
    public WeatherRepository(){

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
