package com.example.nexcab.ui.rides;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nexcab.adapters.RideAdapter;
import com.example.nexcab.databinding.FragmentRecentRidesBinding;
import com.example.nexcab.models.Ride;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class RecentRidesFragment extends Fragment {
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    private RecyclerView recyclerView;
    private List<Ride> recentRides;
    private RideAdapter adapter;
    List<Ride> rides;
    FragmentRecentRidesBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecentRidesBinding.inflate(getLayoutInflater());

        // Initialize your list of recent rides and adapter
        recentRides = new ArrayList<>();
        adapter = new RideAdapter(getContext(),recentRides);
        binding.recentRidesRecyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // init database
        database = FirebaseDatabase.getInstance();
        // init Authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        // create list of Ride objects
        rides = new ArrayList<>();
        String userid = user.getUid();
        Log.d("userid", userid);
        reference = database.getReference().child("Users").child(userid).child("Rides").child("recent");

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren() ) {
                    Ride ride;
                    ride = dataSnapshot.getValue(Ride.class);
                    assert ride != null;
                    Log.d("RideFragment", "Ride Pickup: "+ride.getPickupLocation()+"\nRide dropoff: "+ride.getDropoffLocation()+"\nDate: "+ride.getDate()+"\nTime"+ride.getTime()+"\nShare: "+ride.isRide_sharing());
                    rides.add(ride);
                    Log.d("Rides List Count", String.valueOf(rides.size()));
                }

                binding.recentRidesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recentRidesRecyclerView.setAdapter(new RideAdapter(getContext(),rides));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}