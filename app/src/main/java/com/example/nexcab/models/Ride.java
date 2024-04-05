package com.example.nexcab.models;

import java.io.Serializable;

public class Ride implements Serializable {
    private String pickupLocation, dropoffLocation, date, time, status,rideId;
    private boolean ride_sharing,is_booked,instant;

    public String getRideId() {
        return rideId;
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
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.date = date;
        this.time = time;
        this.status = status;
        this.ride_sharing = ride_sharing;
        this.userId = userId;
        this.is_booked = false;
        this.instant = instant;
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
