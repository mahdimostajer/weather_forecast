package com.example.weatherprediction.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherprediction.R;
import com.example.weatherprediction.databinding.FragmentHomeBinding;
import com.example.weatherprediction.models.City;
import com.example.weatherprediction.models.Day;
import com.example.weatherprediction.models.Weather;
import com.example.weatherprediction.ui.DayListAdapter;
import com.example.weatherprediction.ui.WeatherFragment;

import java.util.LinkedList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RadioGroup radioGroup;
    RadioButton cityBtn, coordinationBtn;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        radioGroup = (RadioGroup) binding.radioGroup;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                disableChangeVisibilityForCoordinationMode();
                disableChangeVisibilityForCityMode();
                if (checkedId == R.id.radio_coordination) {
                    changeVisibilityForCoordinationMode();

                } else if (checkedId == R.id.radio_city) {
                    changeVisibilityForCityMode();
                }
            }
        });

        homeViewModel.city.observe(getActivity(), new Observer<City>() {
            @Override
            public void onChanged(City city) {
                if (city != null) {
                    Log.d("corrdinate", city.features.get(0).center.get(0));
                    Log.d("corrdinate", city.features.get(0).center.get(1));
                    homeViewModel.getWeather(city.features.get(0).center.get(1),city.features.get(0).center.get(0));

                }
            }
        });

        binding.discoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.radioGroup.getCheckedRadioButtonId() == R.id.radio_city) {
                    homeViewModel.getCity(binding.cityEditText.getEditText().getText().toString());
                } else if (binding.radioGroup.getCheckedRadioButtonId() == R.id.radio_coordination) {
                    homeViewModel.getWeather(binding.latitudeEditText.getEditText().getText().toString(),
                            binding.longitudeEditText.getEditText().getText().toString());
                }
            }
        });


        homeViewModel.weather.observe(getActivity(), new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
                Fragment fragment = new WeatherFragment();
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_activity_main, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

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

    private void changeVisibilityForCoordinationMode() {
        binding.coordinationText.setVisibility(View.VISIBLE);
        binding.latitudeEditText.setVisibility(View.VISIBLE);
        binding.longitudeEditText.setVisibility(View.VISIBLE);
        binding.discoverButton.setVisibility(View.VISIBLE);
    }

    private void disableChangeVisibilityForCoordinationMode() {
        binding.coordinationText.setVisibility(View.GONE);
        binding.latitudeEditText.setVisibility(View.GONE);
        binding.longitudeEditText.setVisibility(View.GONE);
        binding.discoverButton.setVisibility(View.GONE);
    }


    private void changeVisibilityForCityMode() {
        binding.cityText.setVisibility(View.VISIBLE);
        binding.cityEditText.setVisibility(View.VISIBLE);
        binding.discoverButton.setVisibility(View.VISIBLE);
    }

    private void disableChangeVisibilityForCityMode() {
        binding.cityText.setVisibility(View.GONE);
        binding.cityEditText.setVisibility(View.GONE);
        binding.discoverButton.setVisibility(View.GONE);
    }

}