package com.example.nexcab.models;

public class Driver {
    private String driverLocation;
    // driveStatus true for online and false for offline
    private boolean driverStatus;

    Driver(){}

    Driver(String driverLocation){
        this.driverLocation = driverLocation;
    }

    public String getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(String driverLocation) {
        this.driverLocation = driverLocation;
    }

    public boolean isDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(boolean driverStatus) {
        this.driverStatus = driverStatus;
    }
}
