package com.example.nexcab.models;

import android.util.Log;
import android.widget.Toast;

import com.example.nexcab.ui.search.SearchResultActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ride implements Serializable {
    private static final int MAX_PASSENGER_COUNT = 4;
    private String pickupLocation;
    private String dropoffLocation;
    private String date;

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        Ride.id = id;
    }

    public static String id,pickup;


    private String user1Id, user2Id, user3Id, user4Id;

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    private String time;
    private String status;
    private String rideId;

    public void setUser1Id(String user1Id) {
        this.user1Id = user1Id;
    }

    public void setUser2Id(String user2Id) {
        this.user2Id = user2Id;
    }

    public void setUser3Id(String user3Id) {
        this.user3Id = user3Id;
    }

    public void setUser4Id(String user4Id) {
        this.user4Id = user4Id;
    }

    public String getUser1Id() {
        return user1Id;
    }

    public String getUser2Id() {
        return user2Id;
    }

    public String getUser3Id() {
        return user3Id;
    }

    public String getUser4Id() {
        return user4Id;
    }

    private String driver_name;
    private List<String> passengerList;
    int passengerCount;
    private boolean ride_sharing,is_booked,instant;

    public int getPassengerCount() {
        return passengerCount;
    }

    public String getRideId() {
        return rideId;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_name() {
        return driver_name;
    }

    private String userId,driverId; // New field to store user's ID

    public void setIs_booked(boolean is_booked) {
        this.is_booked = is_booked;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public boolean isInstant() {
        return instant;
    }

    public void setInstant(boolean instant) {
        this.instant = instant;
    }

    public Ride() {
    }

    public boolean isIs_booked() {
        return is_booked;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public Ride(String pickupLocation, String dropoffLocation, String date, String time, String status, boolean ride_sharing, String userId,boolean instant) {

        //init passenger list
        passengerList = new ArrayList<>();
        passengerList.add(userId);

        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.date = date;
        this.time = time;
        this.status = status;
        this.ride_sharing = ride_sharing;
        this.userId = userId;
        this.is_booked = false;
        this.instant = instant;
        this.passengerCount = 1;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setRide_sharing(boolean ride_sharing) {
        this.ride_sharing = ride_sharing;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public boolean isRide_sharing() {
        return ride_sharing;
    }

    public String getUserId() {
        return userId;
    }

    public void getRideDetails(){
        String details = "Pickup location: "+this.getPickupLocation()+"\n"+
                "Dropoff Location: "+this.getDropoffLocation()+"\n"+
                "Date: "+this.getDate()+"\n"+
                "Time: "+this.getTime()+"\n"+
                "Status: "+this.getStatus()+"\n"+
                "User id: "+this.getUserId()+"\n"+
                "Ride id: "+this.getRideId()+"\n"+
                "Ride Sharing: "+this.isRide_sharing()+"\n"+
                "Is booked: "+this.is_booked+"\n"+
                "Passenger List Count: "+this.passengerList.size()+"\n";
        Log.d("Ride details: ",details);
    }

    public void addItemToPassengerList(String item){
        if(passengerList != null){
            passengerList.add(item);
        }else{
            Log.d("Ride: ", "Passenger list is null!!");
        }
    }
}
