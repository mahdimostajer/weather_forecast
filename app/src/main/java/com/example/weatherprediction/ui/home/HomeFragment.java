package com.example.weatherprediction.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.weatherprediction.R;
import com.example.weatherprediction.databinding.FragmentHomeBinding;

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
        binding.coordinationText.setVisibility(View.INVISIBLE);
        binding.latitudeEditText.setVisibility(View.INVISIBLE);
        binding.longitudeEditText.setVisibility(View.INVISIBLE);
        binding.discoverButton.setVisibility(View.INVISIBLE);
    }


    private void changeVisibilityForCityMode() {
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