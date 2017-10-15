package com.unimelbit.teamcobalt.tourlist.Model;

import android.os.Parcel;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Created by awhite on 5/10/17.
 */
@RunWith(RobolectricTestRunner.class)
public class LocationTest {

    public static final String JSON_ID = "_id";
    public static final String JSON_TITLE = "title";
    public static final String JSON_DESC = "description";
    public static final String JSON_LAT = "latitude";
    public static final String JSON_LON = "longitude";
    public static final String JSON_ALT = "altitude";

    String id;
    String title;
    String description;
    Double latitude;
    Double longitude;
    Double altitude;
    Location location;

    @Before
    public void setUp() throws Exception {
        this.id = "0";
        this.title = "title";
        this.description = "description";
        this.latitude = 0.0;
        this.longitude = 1.0;
        this.altitude = 2.0;
        this.location = new Location(
                this.id,
                this.title,
                this.description,
                this.latitude,
                this.longitude,
                this.altitude);
    }

    @Test
    public void writeToParcel() throws Exception {
        //Save bundle to parcel
        Parcel parcel = Parcel.obtain();
        location.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        assertEquals(parcel.readString(), this.id);
        assertEquals(parcel.readString(), this.title);
        assertEquals(parcel.readString(), this.description);
        assertEquals(parcel.readDouble(), this.latitude);
        assertEquals(parcel.readDouble(), this.longitude);
        assertEquals(parcel.readDouble(), this.altitude);
    }

    @Test
    public void newLocationArrayFromJSON() throws Exception {

    }

    @Test
    public void newLocationArrayFromPlaceArray() throws Exception {

    }

    @Test
    public void toMap() throws Exception {
        Map<String, String> locMap = location.toMap();
        assertEquals(locMap.get(JSON_ID), this.id);
        assertEquals(locMap.get(JSON_TITLE), this.title);
        assertEquals(locMap.get(JSON_DESC), this.description);
        assertEquals(locMap.get(JSON_LAT), this.latitude.toString());
        assertEquals(locMap.get(JSON_LON), this.longitude.toString());
        assertEquals(locMap.get(JSON_ALT), this.altitude.toString());

    }

    @Test
    public void toJSON() throws Exception {
        JSONObject locJson = location.toJSON();
        assertEquals(locJson.get(JSON_ID), this.id);
        assertEquals(locJson.get(JSON_TITLE), this.title);
        assertEquals(locJson.get(JSON_DESC), this.description);
        assertEquals(locJson.get(JSON_LAT), this.latitude.toString());
        assertEquals(locJson.get(JSON_LON), this.longitude.toString());
        assertEquals(locJson.get(JSON_ALT), this.altitude.toString());

    }

    @Test
    public void getId() throws Exception {
        assertEquals(this.id, location.getId());
    }

    @Test
    public void getLatitude() throws Exception {
        assertEquals(this.latitude, location.getLatitude());
    }

    @Test
    public void getDescription() throws Exception {
        assertEquals(this.description, location.getDescription());
    }

    @Test
    public void getTitle() throws Exception {
        assertEquals(this.title, location.getTitle());
    }

    @Test
    public void getLongitude() throws Exception {
        assertEquals(this.longitude, location.getLongitude());
    }

    @Test
    public void getAltitude() throws Exception {
        assertEquals(this.altitude, location.getAltitude());
    }


}