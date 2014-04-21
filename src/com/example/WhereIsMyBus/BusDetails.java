package com.example.WhereIsMyBus;

import android.location.Location;

public class BusDetails {
    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    String source;
    String destination;
    String busNumber;
    Location location;

    public BusDetails(String source, String destination, String busNumber) {
        this.source = source;
        this.destination = destination;
        this.busNumber = busNumber;
    }

}
