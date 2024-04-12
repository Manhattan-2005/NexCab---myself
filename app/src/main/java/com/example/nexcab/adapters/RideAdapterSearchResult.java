package com.example.nexcab.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nexcab.R;
import com.example.nexcab.models.Ride;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RideAdapterSearchResult extends RecyclerView.Adapter<RideAdapterSearchResult.RideAdapterSearchResultHolder>{

    List<Ride> rides;
    Context context;
    int selectedItem  = -1;
    FirebaseDatabase database;
    FirebaseAuth auth;
    public RideAdapterSearchResult(Context context,List<Ride> rides){
        this.context = context;
        this.rides = rides;
    }
    @NonNull
    @Override
    public RideAdapterSearchResult.RideAdapterSearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // initialize database
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        return new RideAdapterSearchResultHolder(LayoutInflater.from(context).inflate(R.layout.ride_view_search_result,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RideAdapterSearchResult.RideAdapterSearchResultHolder holder, int position) {
        Ride ride = rides.get(holder.getAdapterPosition()); // Use getAdapterPosition() to retrieve the current position

        if (ride != null) {
            String pickup = ride.getPickupLocation();
            String dropoff = ride.getDropoffLocation();
            String date = "Date: " + ride.getDate();
            String driverName = "Driver: " + ride.getDriver_name();

            holder.pickupLocation.setText(pickup);
            holder.dropoffLocation.setText(dropoff);
            holder.date.setText(date);
            holder.driver_name.setText(driverName);

            // Set OnClickListener for the join_ride_button
            holder.join_ride_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addPassenger(holder.getAdapterPosition());
                }
            });
        }

        // Check if this item is selected
        if (selectedItem == holder.getAdapterPosition()) { // Use getAdapterPosition() to get the current position
            holder.driver_name.setVisibility(View.VISIBLE);
            holder.join_ride_button.setVisibility(View.VISIBLE);
        } else {
            holder.driver_name.setVisibility(View.GONE);
            holder.join_ride_button.setVisibility(View.GONE);
        }

        // Set click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition(); // Use getAdapterPosition() to get the clicked position
                if (selectedItem == clickedPosition) {
                    // If the same item is clicked again, collapse it
                    selectedItem = -1;
                } else {
                    // If a different item is clicked, expand it
                    selectedItem = clickedPosition;
                }
                notifyDataSetChanged(); // Notify adapter to update views
            }
        });
    }

    @Override
    public int getItemCount() {
        return rides.size();
    }

    static class RideAdapterSearchResultHolder extends RecyclerView.ViewHolder{
        TextView pickupLocation,dropoffLocation,date,driver_name;
        Button join_ride_button;

        public RideAdapterSearchResultHolder(@NonNull View itemView) {
            super(itemView);
            pickupLocation = itemView.findViewById(R.id.pickupTextView);
            dropoffLocation = itemView.findViewById(R.id.dropoffTextView);
            date = itemView.findViewById(R.id.dateTextView);
            driver_name = itemView.findViewById(R.id.driverNameTextView);
            join_ride_button = itemView.findViewById(R.id.join_ride_button);
        }
    }

    public void addPassenger(int position){
        Ride ride = rides.get(position);

        if(ride.getPassengerCount() > 4){
            Toast.makeText(context, "Ride is Full!!", Toast.LENGTH_SHORT).show();
        }else{
            ride.setPassengerCount(ride.getPassengerCount()+1);
            ride.addItemToPassengerList(auth.getUid());

            Task<Void> task = database.getReference().child("Rides").child(ride.getPickupLocation())
                    .child(ride.getRideId()).setValue(ride);

            if(task.isSuccessful()){
                Toast.makeText(context, "Ride booked! You will be notified when starts!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Cannot book ride! Problem while booking", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
