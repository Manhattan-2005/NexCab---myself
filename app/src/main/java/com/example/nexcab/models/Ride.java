package com.example.nexcab.models;

import java.io.Serializable;

public class Ride implements Serializable {
    private String pickupLocation, dropoffLocation, date, time, status;
    private boolean ride_sharing;
    private String userId; // New field to store user's ID

    public Ride() {
    }

    public Ride(String pickupLocation, String dropoffLocation, String date, String time, String status, boolean ride_sharing, String userId) {
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.date = date;
        this.time = time;
        this.status = status;
        this.ride_sharing = ride_sharing;
        this.userId = userId;
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
}
