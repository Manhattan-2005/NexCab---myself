package com.example.nexcab.driver;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nexcab.R;
import com.example.nexcab.databinding.ActivityDriverBinding;


public class DriverDashboard extends Fragment {

    ActivityDriverBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverBinding.inflate(getLayoutInflater());
    }
}