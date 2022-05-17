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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.weatherprediction.R;
import com.example.weatherprediction.databinding.FragmentHomeBinding;
import com.example.weatherprediction.databinding.FragmentWeatherBinding;
import com.example.weatherprediction.models.City;
import com.example.weatherprediction.models.Day;
import com.example.weatherprediction.models.Weather;
import com.example.weatherprediction.ui.home.HomeViewModel;

import java.util.LinkedList;

public class WeatherFragment extends Fragment {

    private FragmentWeatherBinding binding;
    private RecyclerView mRecyclerView;
    private LinkedList<Day> mDayList;
    private DayListAdapter mAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mRecyclerView = binding.dayListRecyclerview;
        mDayList = new LinkedList<>();

        mAdapter = new DayListAdapter(container.getContext(),mDayList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        homeViewModel.weather.observe(getActivity(), new Observer<Weather>() {

            @Override
            public void onChanged(Weather weather) {
                mDayList.clear();
                mDayList.addAll(weather.daily);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                Toast.makeText(getActivity(), "current temp:" + weather.current.temp.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}