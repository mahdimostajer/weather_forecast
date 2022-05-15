package com.example.weatherprediction;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.weatherprediction.models.City;
import com.example.weatherprediction.network.CityUtils;
import com.example.weatherprediction.ui.home.HomeViewModel;
import com.google.gson.Gson;

public class CityRepository {

    public MutableLiveData<City> city = new MutableLiveData<>();
    public CityRepository(){

    }

    public void getCity(String name) {
        new fetchCity(city).execute(name);
    }

    public class fetchCity extends AsyncTask<String, Void, String> {
        MutableLiveData<City> city;

        public fetchCity(MutableLiveData<City> city) {
            this.city = city;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            City newCity = gson.fromJson(s, City.class);
            city.setValue(newCity);
        }

        @Override
        protected String doInBackground(String... name) {
            CityUtils cityUtils = new CityUtils();
            return cityUtils.getCoordinates(name[0]);
        }
    }
}
