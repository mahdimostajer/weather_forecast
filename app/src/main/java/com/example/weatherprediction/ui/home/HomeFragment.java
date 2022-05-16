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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.weatherprediction.R;
import com.example.weatherprediction.databinding.FragmentHomeBinding;
import com.example.weatherprediction.models.City;
import com.example.weatherprediction.models.Weather;

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
        binding.latitudeEditText.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.longitudeEditText.getEditText().getText().toString().length() != 0 && editable.toString().length() != 0) {
                    Toast.makeText(getActivity(), "no internet connection", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.discoverButton.performClick();
                        }
                    }, 5000);
                }
            }
        });

        homeViewModel.city.observe(getActivity(), new Observer<City>() {
            @Override
            public void onChanged(City city) {
                if (city != null) {
                    Log.d("corrdinate", city.features.get(0).center.get(0));
                    Log.d("corrdinate", city.features.get(0).center.get(1));
                    homeViewModel.getWeather(city.features.get(0).center.get(1), city.features.get(0).center.get(0));
                }
            }
        });

        binding.discoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        homeViewModel.getWeather(lon, lat);
                    } else {
                        if (lat.length() == 0 && lon.length() == 0) {
                            Toast.makeText(getActivity(), "please enter required fields", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "no internet connection", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }
        });


        homeViewModel.weather.observe(getActivity(), new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
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
        binding.longitudeEditText.requestFocus();
        binding.coordinationText.setVisibility(View.VISIBLE);
        binding.latitudeEditText.setVisibility(View.VISIBLE);
        binding.longitudeEditText.setVisibility(View.VISIBLE);
        binding.discoverButton.setVisibility(View.VISIBLE);
    }

    private void disableChangeVisibilityForCoordinationMode() {
        binding.coordinationText.setVisibility(View.INVISIBLE);
        binding.latitudeEditText.setVisibility(View.INVISIBLE);
        binding.longitudeEditText.setVisibility(View.INVISIBLE);
        binding.discoverButton.setVisibility(View.INVISIBLE);
    }


    private void changeVisibilityForCityMode() {
        binding.cityEditText.requestFocus();
        binding.cityText.setVisibility(View.VISIBLE);
        binding.cityEditText.setVisibility(View.VISIBLE);
        binding.discoverButton.setVisibility(View.VISIBLE);
    }

    private void disableChangeVisibilityForCityMode() {
        binding.cityText.setVisibility(View.INVISIBLE);
        binding.cityEditText.setVisibility(View.INVISIBLE);
        binding.discoverButton.setVisibility(View.INVISIBLE);
    }

}