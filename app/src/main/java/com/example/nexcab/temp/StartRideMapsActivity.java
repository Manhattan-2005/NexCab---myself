package com.example.nexcab.temp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nexcab.MainActivity;
import com.example.nexcab.R;
import com.example.nexcab.models.Ride;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;


public class StartRideMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment supportMapFragment;
    String receiverToken;
    FirebaseDatabase database;
    Ride ride;
    FirebaseAuth auth;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_ride_maps);

        //
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        getRideObject();

        // set onclicklistener to end ride button
        Button endRidebutton = findViewById(R.id.endRideButtonMapsActivity);
        endRidebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StartRideMapsActivity.this, "Driver will end the ride!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setMap(){
        // Obtain the SupportMapFragment and initialize the GoogleMap object
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapStartRide);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // Get the GoogleMap object
                GoogleMap mMap = googleMap;

                // Get pickup and dropoff locations from ConfirmRideActivity
                String pickupLocation = ride.getPickupLocation();
                String dropoffLocation = ride.getDropoffLocation();

                // Check if locations are not null
                if (pickupLocation != null && dropoffLocation != null) {
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        // Get the list of addresses for pickup and dropoff locations
                        List<Address> pickupAddressList = geocoder.getFromLocationName(pickupLocation, 1);
                        List<Address> dropoffAddressList = geocoder.getFromLocationName(dropoffLocation, 1);

                        // Check if both pickup and dropoff address lists are not empty
                        if (!pickupAddressList.isEmpty() && !dropoffAddressList.isEmpty()) {
                            // Get the first address from the pickup address list
                            Address pickupAddress = pickupAddressList.get(0);
                            LatLng pickupLatLng = new LatLng(pickupAddress.getLatitude(), pickupAddress.getLongitude());
                            // Add marker for pickup location
                            mMap.addMarker(new MarkerOptions().position(pickupLatLng).title(pickupLocation));

                            // Get the first address from the dropoff address list
                            Address dropoffAddress = dropoffAddressList.get(0);
                            LatLng dropoffLatLng = new LatLng(dropoffAddress.getLatitude(), dropoffAddress.getLongitude());
                            // Add marker for dropoff location
                            mMap.addMarker(new MarkerOptions().position(dropoffLatLng).title(dropoffLocation));

                            // Move camera to show both markers
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            builder.include(pickupLatLng);
                            builder.include(dropoffLatLng);
                            LatLngBounds bounds = builder.build();
                            int padding = 100; // Padding in pixels
                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                            mMap.moveCamera(cu);
                        } else {
                            Toast.makeText(StartRideMapsActivity.this, "No matching addresses found for pickup or dropoff locations", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(StartRideMapsActivity.this, "Locations are null!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void getRideObject(){
        DatabaseReference ridesRef = FirebaseDatabase.getInstance().getReference().child("Rides")
                .child(Ride.pickup).child("booked");

        String rideId = Ride.id;

        ridesRef.child(rideId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if the ride data exists
                if (dataSnapshot.exists()) {
                    // Retrieve the Ride object
                    ride = dataSnapshot.getValue(Ride.class);

                    setMap();
                } else {
                    // Handle the case where the ride data does not exist
                    Log.d("Firebase", "Ride data does not exist for ride ID: " + rideId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors
                Log.e("Firebase", "Error fetching ride data: " + databaseError.getMessage());
            }
        });
    }
}
