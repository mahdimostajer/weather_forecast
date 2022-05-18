package com.example.weatherprediction.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.weatherprediction.databinding.FragmentDetailsBinding;
import com.example.weatherprediction.models.Day;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Day day = new Gson().fromJson(getArguments().getString("day"), Day.class);
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        setDetails(day);
        View root = binding.getRoot();
        return root;
    }

    private void setDetails(Day day) {

        TextView windSpeed = binding.detailWindSpeedTextView;
        TextView max = binding.detailMaxTextView;
        TextView min = binding.detailMinTextView;
        TextView sunrise = binding.detailSunriseTextView;
        TextView sunset = binding.detailSunsetTextView;
        TextView pressure = binding.detailPressureTextView;
        TextView humidity = binding.detailHumidityTextView;
        TextView wind_degree = binding.detailWindDegreeTextView;
        SimpleDateFormat formatter= new SimpleDateFormat("hh:mm:ss aa");
        windSpeed.setText(String.format("%.1f",day.wind_speed));
        max.setText(String.format("%.1f",day.temp.max));
        min.setText(String.format("%.1f",day.temp.min));
        sunrise.setText(formatter.format(new Date(day.sunrise)));
        sunset.setText(formatter.format(new Date(day.sunset)));
        pressure.setText(String.format("%.1f",day.pressure));
        humidity.setText(String.format("%.1f",day.humidity));
        wind_degree.setText(String.format("%.1f",day.wind_deg));

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}