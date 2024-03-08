package com.example.nexcab;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.gms.maps.SupportMapFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CustomerInstantRide extends Fragment {

    public CustomerInstantRide() {}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = view.findViewById(R.id.letsgo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rideUnavailable(v);
            }
        });
    }

    public static CustomerInstantRide newInstance(String param1, String param2) {
        return new CustomerInstantRide();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_instant_ride, container, false);
    }
    public void rideUnavailable(View view){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        Fragment rides_not_available = new RidesNotAvailable();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.constraintLayout1,rides_not_available);
        ConstraintLayout constraintLayout = view.findViewById(R.id.constraintLayout1);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}