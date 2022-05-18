package com.example.weatherprediction;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.weatherprediction.models.City;
import com.example.weatherprediction.models.Room.CityBase;
import com.example.weatherprediction.models.Room.CityDao;
import com.example.weatherprediction.models.Room.WeatherDataBase;
import com.example.weatherprediction.network.CityUtils;
import com.example.weatherprediction.ui.home.HomeViewModel;
import com.google.gson.Gson;

public class CityRepository {

    public MutableLiveData<City> city = new MutableLiveData<>();
    CityDao cityDao;

    public CityRepository(Application application) {
        WeatherDataBase weatherDataBase = WeatherDataBase.getInstance(application);
        cityDao = weatherDataBase.cityDao();
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
            Log.d("error", new Gson().toJson(s));
            Log.d("error2", s);
            CityBase cityBase = new CityBase( newCity.query.get(0),
                    Float.valueOf(newCity.features.get(0).center.get(0)), Float.valueOf(newCity.features.get(0).center.get(1)));
            city.setValue(newCity);
            new insertAsyncTask(cityDao).execute(cityBase);
        }

        @Override
        protected String doInBackground(String... name) {
            CityUtils cityUtils = new CityUtils();
            return cityUtils.getCoordinates(name[0]);
        }
    }


    private static class insertAsyncTask extends AsyncTask<CityBase, Void, Void> {

        private CityDao mAsyncTaskDao;

        insertAsyncTask(CityDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CityBase... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
