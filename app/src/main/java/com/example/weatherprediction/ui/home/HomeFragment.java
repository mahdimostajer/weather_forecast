package com.example.weatherprediction.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.google.gson.Gson;

import java.util.Date;
import java.util.LinkedList;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RadioGroup radioGroup;
    private Boolean setHandler;
    RadioButton cityBtn, coordinationBtn;
    private boolean pending;



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
        addHandlerForAutoConnection();
        homeViewModel.city.observe(getActivity(), new Observer<City>() {
            @Override
            public void onChanged(City city) {
                if (city != null) {
                    Log.d("coordinate", city.features.get(0).center.get(0));
                    Log.d("coordinate", city.features.get(0).center.get(1));
                    homeViewModel.getWeather(city.features.get(0).center.get(1), city.features.get(0).center.get(0));
                }
            }
        });

        binding.discoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pending = true;
                if (binding.radioGroup.getCheckedRadioButtonId() == R.id.radio_city) {
                    String name = binding.cityEditText.getEditText().getText().toString();
                    ConnectivityManager connMgr = (ConnectivityManager)
                            getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = null;
                    if (connMgr != null) {
                        networkInfo = connMgr.getActiveNetworkInfo();
                    }
                    if (networkInfo != null && networkInfo.isConnected() && name.length() != 0) {
                        homeViewModel.getCity(name);
                    } else {
                        if (name.length() == 0) {
                            Toast.makeText(getActivity(), "please enter city name", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "no internet connection", Toast.LENGTH_LONG).show();
                            homeViewModel.getCacheCity(name);
                        }
                    }

                } else if (binding.radioGroup.getCheckedRadioButtonId() == R.id.radio_coordination) {
                    String lat = binding.latitudeEditText.getEditText().getText().toString();
                    String lon = binding.longitudeEditText.getEditText().getText().toString();

                    ConnectivityManager connMgr = (ConnectivityManager)
                            getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = null;
                    if (connMgr != null) {
                        networkInfo = connMgr.getActiveNetworkInfo();
                    }
                    if (networkInfo != null && networkInfo.isConnected()
                            && lat.length() != 0 && lon.length() != 0) {
                        homeViewModel.getWeather(lat, lon);
                    } else {
                        if (lat.length() == 0 && lon.length() == 0) {
                            Toast.makeText(getActivity(), "please enter required fields", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "no internet connection", Toast.LENGTH_LONG).show();
                            homeViewModel.getCacheWeather(lat, lon);
                        }
                    }

                }
            }
        });


        homeViewModel.weather.observe(getActivity(), new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
                if (!isVisible() || !pending || weather == null) {
                    return;
                }
                pending = false;
                Fragment fragment = new WeatherFragment();
                Bundle bundle = new Bundle();
                bundle.putString("weather" ,new Gson().toJson(weather));
                fragment.setArguments(bundle);
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_activity_main, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null).commit();
//                Toast.makeText(getActivity(), "current temp:" + weather.current.temp.toString(), Toast.LENGTH_LONG).show();
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
        binding.longitudeEditText.requestFocus();
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

    private void addHandlerForAutoConnection() {
//        ArrayList<EditText> views = new ArrayList<EditText>(Arrays.asList(
//                binding.longitudeEditText.getEditText(),
//                binding.latitudeEditText.getEditText(),
//                binding.cityEditText.getEditText()));
//        setHandler = false;
//        for (EditText txt : views) {
//            txt.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                    if ((txt != binding.cityEditText.getEditText() && binding.latitudeEditText.getEditText().getText().toString().length() != 0 && binding.longitudeEditText.getEditText().getText().toString().length() != 0) ||
//                            (txt == binding.cityEditText.getEditText() && binding.cityEditText.getEditText().getText().toString().length() != 0)) {
//                        setHandler = true;
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (isVisible()) {
//                                    binding.discoverButton.performClick();
//                                }
//                            }
//                        }, 5000);
//
//                    }
//                }
//            });
//            if (setHandler)
//                break;
//
//        }
    }


    private void changeVisibilityForCityMode() {
        binding.cityEditText.requestFocus();
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