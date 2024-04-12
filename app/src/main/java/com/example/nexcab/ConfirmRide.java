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
import com.example.nexcab.models.User;
import com.example.nexcab.ui.rides.RideFragment;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

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

        // get ParentFragment
        Bundle bundle = getArguments();
        String parentFragment = bundle.getString("ParentFragment");

        // retrieve data from parent intent
        String pickupLocation = bundle.getString("pickupLocation");
        String dropoffLocation = bundle.getString("dropoffLocation");

        String date = null, time = null;
        boolean instant = false;
        if(Objects.equals(parentFragment, "DropoffLocation")) {
            date = LocalDate.now().toString();
            Log.d("ConfirmRide: ", date);
            time = convertToLocalApproxTime(LocalTime.now());
            Log.d("ConfirmRide: ", time);
            instant = true;
        }else if(Objects.equals(parentFragment, "DropoffLocationPreebook")){
            date = bundle.getString("Date");
            time = bundle.getString("Time");
        }

        //
        binding.pickupLocation.setText(String.format("Pickup Location: %s", pickupLocation));
        binding.dropoffLocation.setText(String.format("Dropoff Location: %s", dropoffLocation));
        binding.rideDate.setText(String.format("Date: %s",date));
        binding.rideTime.setText(String.format("Time: %s",time));

        String finalTime = time;
        String finalDate = date;
        boolean finalInstantstate = instant;
        binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = firebaseAuth.getCurrentUser();
                assert user != null;
                String userid = user.getUid();

                // create ride to save in database
                // status will always be Upcoming when new ride is created
                Ride ride = new Ride(pickupLocation,dropoffLocation, finalDate, finalTime,"Upcoming",binding.rideSharingCheckbox.isChecked(),userid,finalInstantstate);
                ride.setUser1Id(userid);
                // set the hasUpcomingRide for current User
//                User thisUser = User.getUser();
//                thisUser.setHasUpcomingRide(true);

                // set ride under Users
                rides = database.getReference().child("Users").child(userid).child("Rides");
                String id = rides.push().getKey();
                // set id to ride object
                ride.setRideId(id);
                assert id != null;
                Task<Void> task = rides.child(id).setValue(ride);

                // set ride under Rides
                assert pickupLocation != null;
                String pickupLocationLowercase = pickupLocation.toLowerCase();
                rides = database.getReference().child("Rides").child(pickupLocationLowercase);
                // set id to ride object
                ride.setRideId(id);
                rides.child(id).setValue(ride);

                if(!id.equals("")){
                    Toast.makeText(getContext(), "Ride request create!\n You will be notified when accepted", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    Intent intent = new Intent(getContext(),MainActivity.class);
                    intent.putExtra("Source","booking");
                    startActivity(intent);
                    fragmentTransaction.commit();
                }
            }
        });
    }
    public static String convertToLocalApproxTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault());
        return time.format(formatter);
    }
}