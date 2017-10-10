package com.unimelbit.teamcobalt.tourlist.Tracking;

import java.util.ArrayList;

/**
 * Created by Hong Lin on 1/10/2017.
 */

/**
 * App uses a service to send coordinates to the DB
 */
public interface CoordinateDBPostRequester {

    //Post coordinates to the DB
    void postToDb(double latitude, double longitude, String ref);

}
