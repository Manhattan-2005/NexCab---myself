package com.example.nexcab;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.nexcab.databinding.FragmentCustomerHomePageBinding;

import java.util.Objects;

public class customerHomePage extends Fragment {
    FragmentCustomerHomePageBinding binding;
    public customerHomePage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // initialize the binding
        binding = FragmentCustomerHomePageBinding.inflate(inflater,container,false);

        // find both buttons
        Button instant_ride = binding.instantRideButton;
        Button prebook = binding.prebookButton;
        // When the buttons are clicked
        instant_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(binding.getRoot());
            }
        });
        prebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return binding.getRoot();
    }

    public void replaceFragment(View view){
        Fragment instant_ride = new CustomerInstantRide();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        // Remove all views before replacing the fragment
        FrameLayout frameLayout = view.findViewById(R.id.customer_home_page_frame_layout);
        frameLayout.removeAllViews();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.customer_home_page_frame_layout,instant_ride);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}