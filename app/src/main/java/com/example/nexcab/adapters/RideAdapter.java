package com.example.nexcab.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nexcab.R;
import com.example.nexcab.models.Ride;

import java.util.List;

public class RideAdapter extends RecyclerView.Adapter<RideAdapter.RideViewHolder>{

    List<Ride> rides;
    Context context;
    public RideAdapter(Context context,List<Ride> rides){
        this.context = context;
        this.rides = rides;
    }
    @NonNull
    @Override
    public RideAdapter.RideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RideViewHolder(LayoutInflater.from(context).inflate(R.layout.ride_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RideAdapter.RideViewHolder holder, int position) {
        String pickup = rides.get(position).getPickupLocation();
        String dropoff = rides.get(position).getDropoffLocation();
        String date = "Date: "+rides.get(position).getDate();
        holder.pickupLocation.setText(pickup);
        holder.dropoffLocation.setText(dropoff);
        holder.date.setText(date);
    }

    @Override
    public int getItemCount() {
        return rides.size();
    }

    static class RideViewHolder extends RecyclerView.ViewHolder{
        TextView pickupLocation,dropoffLocation,date;

        public RideViewHolder(@NonNull View itemView) {
            super(itemView);
            pickupLocation = itemView.findViewById(R.id.pickupTextView);
            dropoffLocation = itemView.findViewById(R.id.dropoffTextView);
            date = itemView.findViewById(R.id.dateTextView);
        }
    }
}
