package com.example.weatherprediction.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weatherprediction.R;
import com.example.weatherprediction.databinding.FragmentHomeBinding;
import com.example.weatherprediction.databinding.FragmentWeatherBinding;
import com.example.weatherprediction.models.City;
import com.example.weatherprediction.models.Day;
import com.example.weatherprediction.models.Weather;
import com.example.weatherprediction.ui.home.HomeViewModel;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import kotlinx.coroutines.selects.WhileSelectKt;

public class WeatherFragment extends Fragment {

    private FragmentWeatherBinding binding;
    private RecyclerView mRecyclerView;
    private LinkedList<Day> mDayList;
    private DayListAdapter mAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Weather weather = new Gson().fromJson(getArguments().getString("weather"), Weather.class);
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        setDetails(weather);

        View root = binding.getRoot();
        mRecyclerView = binding.dayListRecyclerview;
        mDayList = new LinkedList<>();
        mDayList.addAll(weather.daily);
        mDayList.removeFirst();
        mAdapter = new DayListAdapter(this,container.getContext(),mDayList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return root;
    }

    private void setDetails(Weather weather) {
        TextView feelsLike = binding.todayFeelsLikeTextView;
        TextView currentTemp = binding.todayCurrentTempTextView;
        TextView cloud = binding.todayCloudTextView;
        TextView windSpeed = binding.todayWindSpeedTextView;
        ImageView image = binding.todayImage;
        TextView max = binding.todayMaxTextView;
        TextView min = binding.todayMinTextView;
        TextView sunrise = binding.todaySunriseTextView;
        TextView sunset = binding.todaySunsetTextView;
        TextView pressure = binding.todayPressureTextView;
        TextView humidity = binding.todayHumidityTextView;
        TextView wind_degree = binding.todayWindDegreeTextView;
        SimpleDateFormat formatter= new SimpleDateFormat("hh:mm:ss aa");
        feelsLike.setText(String.format("%.1f",weather.current.feels_like));
        currentTemp.setText(String.format("%.1f",weather.current.temp));
        cloud.setText(String.format("%.1f",weather.current.clouds));
        windSpeed.setText(String.format("%.1f",weather.current.wind_speed));
        max.setText(String.format("%.1f",weather.daily.get(0).temp.max));
        min.setText(String.format("%.1f",weather.daily.get(0).temp.min));
        sunrise.setText(formatter.format(new Date(weather.current.sunrise)));
        sunset.setText(formatter.format(new Date(weather.current.sunset)));
        pressure.setText(String.format("%.1f",weather.current.pressure));
        humidity.setText(String.format("%.1f",weather.current.humidity));
        wind_degree.setText(String.format("%.1f",weather.current.wind_deg));

        String iconUrl = "http://openweathermap.org/img/wn/" + weather.current.weather.get(0).icon  + "@2x.png";
        Glide.with(getContext()).load(iconUrl).into(image);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}