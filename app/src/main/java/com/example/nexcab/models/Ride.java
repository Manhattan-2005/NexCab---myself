package com.example.nexcab.models;

import java.io.Serializable;

public class Ride implements Serializable {
    private String pickupLocation, dropoffLocation,date,time;
    boolean ride_sharing;
    public Ride(){}
    public Ride(String pickupLocation, String dropoffLocation, String date, String time, boolean ride_sharing) {
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.date = date;
        this.time = time;
        this.ride_sharing = ride_sharing;
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
}
