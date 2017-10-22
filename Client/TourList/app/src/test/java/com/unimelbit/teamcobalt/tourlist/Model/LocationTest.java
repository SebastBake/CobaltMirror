package com.unimelbit.teamcobalt.tourlist.Model;

import android.os.Parcel;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Tests for the Location class.
 * Location is used to represent a location
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
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

    /*
     * Creates a Location before each test to be used for testing
     */
    @Before
    public void setUp() throws Exception {
        // Dummy data
        this.id = "0";
        this.title = "title";
        this.description = "description";
        this.latitude = 0.0;
        this.longitude = 1.0;
        this.altitude = 2.0;

        // Location created
        this.location = new Location(
                this.id,
                this.title,
                this.description,
                this.latitude,
                this.longitude,
                this.altitude);
    }

    /*
     *  Checks the data is written to the parcel
     */
    @Test
    public void writeToParcel() throws Exception {
        //Save bundle to parcel
        Parcel parcel = Parcel.obtain();
        location.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        // Check data is the same as Trip
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

    /*
     *  Checks a Map is returned with the correct data
     */
    @Test
    public void toMap() throws Exception {

        // Gets Map
        Map<String, String> locMap = location.toMap();

        // Compares the data
        assertEquals(locMap.get(JSON_ID), this.id);
        assertEquals(locMap.get(JSON_TITLE), this.title);
        assertEquals(locMap.get(JSON_DESC), this.description);
        assertEquals(locMap.get(JSON_LAT), this.latitude.toString());
        assertEquals(locMap.get(JSON_LON), this.longitude.toString());
        assertEquals(locMap.get(JSON_ALT), this.altitude.toString());

    }

    /*
     * Checks a Json Object is returned with the correct data
     */
    @Test
    public void toJSON() throws Exception {

        // Gets the json
        JSONObject locJson = location.toJSON();

        // Compares the data
        assertEquals(locJson.get(JSON_ID), this.id);
        assertEquals(locJson.get(JSON_TITLE), this.title);
        assertEquals(locJson.get(JSON_DESC), this.description);
        assertEquals(locJson.get(JSON_LAT), this.latitude.toString());
        assertEquals(locJson.get(JSON_LON), this.longitude.toString());
        assertEquals(locJson.get(JSON_ALT), this.altitude.toString());

    }

    /*
     * Test the getter for id field
     */
    @Test
    public void getId() throws Exception {
        assertEquals(this.id, location.getId());
    }

    /*
     * Test the getter for latitude field
     */
    @Test
    public void getLatitude() throws Exception {
        assertEquals(this.latitude, location.getLatitude());
    }

    /*
     * Test the getter for description field
     */
    @Test
    public void getDescription() throws Exception {
        assertEquals(this.description, location.getDescription());
    }

    /*
     * Test the getter for title field
     */
    @Test
    public void getTitle() throws Exception {
        assertEquals(this.title, location.getTitle());
    }

    /*
     * Test the getter for longitude field
     */
    @Test
    public void getLongitude() throws Exception {
        assertEquals(this.longitude, location.getLongitude());
    }

    /*
     * Test the getter for altitude field
     */
    @Test
    public void getAltitude() throws Exception {
        assertEquals(this.altitude, location.getAltitude());
    }

}
