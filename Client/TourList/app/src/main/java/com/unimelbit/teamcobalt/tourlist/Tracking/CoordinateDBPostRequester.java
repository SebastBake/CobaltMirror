package com.unimelbit.teamcobalt.tourlist.Tracking;

/**
 * Created by Hong Lin on 1/10/2017.
 */

public interface CoordinateDBPostRequester {

    //Post coordinates to the DB
    void postToDb(double latitude, double longitude, String ref);

}
