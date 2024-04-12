package com.example.nexcab.ui.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.nexcab.R;
import com.example.nexcab.adapters.RideAdapter;
import com.example.nexcab.adapters.RideAdapterSearchResult;
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

public class SearchResultActivity extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseUser user;
    List<Ride> rides;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // hide the action bar

        // init resources
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        rides = new ArrayList<>();
        String userid = user.getUid();
        Log.d("userid", userid);

        //
        assert location != null;
        reference = database.getReference().child("Rides").child(location)
                .child("booked");
        RecyclerView recyclerView = findViewById(R.id.searchResultRecyclerView);

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren() ) {
                    Ride ride;
                    ride = dataSnapshot.getValue(Ride.class);
                    assert ride != null;
                    Log.d("SearchResult: ", "Ride Pickup: "+ride.getPickupLocation()+"\nRide dropoff: "+ride.getDropoffLocation()+"\nDate: "+ride.getDate()+"\nTime"+ride.getTime()+"\nShare: "+ride.isRide_sharing());
                    rides.add(ride);
                    Log.d("Rides List Count", String.valueOf(rides.size()));
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(new RideAdapterSearchResult(getApplicationContext(),rides));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}