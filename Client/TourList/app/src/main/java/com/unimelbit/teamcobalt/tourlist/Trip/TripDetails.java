package com.unimelbit.teamcobalt.tourlist.Trip;

/**
 * Created by Sebastian Baker on 12/9/17.
 */
public class TripDetails {

    private String name;
    private String cost;
    private String size;
    private String locations;

    public TripDetails(String name, String cost, String size, String locations) {
        this.name = name;
        this.cost = cost;
        this.size = size;
        this.locations = locations;
    }

    public String getLocations() {
        return locations;
    }

    public String getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public String getCost() {
        return cost;
    }
}
