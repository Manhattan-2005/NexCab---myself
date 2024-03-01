package com.example.nexcab;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nexcab.databinding.FragmentConfirmRideBinding;
import com.example.nexcab.models.Ride;
import com.example.nexcab.ui.rides.RideFragment;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalTime;

public class ConfirmRide extends Fragment {

    FragmentConfirmRideBinding binding;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    DatabaseReference rides;
    FirebaseUser user;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConfirmRideBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize database
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        // retrieve data from parent intent
        Bundle bundle = getArguments();
        String pickupLocation = bundle.getString("pickupLocation");
        String dropoffLocation = bundle.getString("dropoffLocation");
        String date = LocalDate.now().toString();
        String time = LocalTime.now().toString();

        //
        binding.pickupLocation.setText(String.format("Pickup Location: %s", pickupLocation));
        binding.dropoffLocation.setText(String.format("Dropoff Location: %s", dropoffLocation));
        binding.rideDate.setText(String.format("Date: %s",date));
        binding.rideTime.setText(String.format("Time: %s",time));
        binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create ride to save in database
                Ride ride = new Ride(pickupLocation,dropoffLocation,date,time,binding.rideSharingCheckbox.isChecked());

                user = firebaseAuth.getCurrentUser();
                String userid = user.getUid();
                rides = database.getReference().child("Users").child(userid).child("Rides");
                String id = rides.push().getKey();
                assert id != null;
                Task<Void> task = rides.child(id).setValue(ride);

//                Log.d("Task", "was task successfull "+task.isSuccessful());
                //display toast
                if(!id.equals("")){
                    Toast.makeText(getContext(), "Ride request create!\n You will be notified when accepted", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.location_frameLayout_id,new RideFragment());
                    fragmentTransaction.commit();
                }
            }
        });
    }
}