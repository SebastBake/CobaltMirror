package com.unimelbit.teamcobalt.tourlist.Model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for the Trip class.
 * Trip is used to represent a trip
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class TripTest {

    public static final String JSON_ID = "_id";
    public static final String JSON_NAME = "name";
    public static final String JSON_COST = "cost";
    public static final String JSON_SIZE = "size";
    public static final String JSON_DATE = "date";
    public static final String JSON_LOC = "locations";
    public static final String JSON_DESC = "description";
    public static final String JSON_OWNER = "owner";
    public static final String JSON_USERS_NAMES = "usernames";
    public static final String JSON_USERS_IDS = "userids";

    Trip trip;
    String id;
    String name;
    String description;
    String date;
    String cost;
    String size;
    String owner;
    ArrayList<Location> locations;
    ArrayList<String> usernames;
    ArrayList<String> userids;
    String url;

    /*
     * Creates a Trip before each test to be used for testing
     */
    @Before
    public void setUp() throws Exception {

        // dummy data
        this.id = "0";
        this.name = "name";
        this.description = "description";
        this.date = "date";
        this.cost = "cost";
        this.size = "size";
        this.owner = "owner";
        this.locations = new ArrayList<>();
        this.usernames = new ArrayList<>();
        this.userids = new ArrayList<>();
        this.url = "www.url.com";

        // mocked objects added
        Location location = mock(Location.class);
        when(location.getTitle()).thenReturn("title");
        when(location.toJSON()).thenReturn(mock(JSONObject.class));

        locations.add(mock(Location.class));
        usernames.add("user");
        userids.add("0");

        // Trip created
        this.trip = new Trip(
                this.id,
                this.name,
                this.description,
                this.date,
                this.cost,
                this.size,
                this.owner,
                this.locations,
                this.usernames,
                this.userids,
                this.url);
    }

    @Test
    public void newTripArrayFromJSON() throws Exception {

    }

    /*
     * Checks that a Map with the trip data is returned
     */
    @Test
    public void toMap() throws Exception {
        Map<String, String> mapTrip = trip.toMap();

        assertEquals(mapTrip.get(JSON_ID), this.id);
        assertEquals(mapTrip.get(JSON_NAME), this.name);
        assertEquals(mapTrip.get(JSON_DESC), this.description);
        assertEquals(mapTrip.get(JSON_DATE), this.date);
        assertEquals(mapTrip.get(JSON_COST), this.cost);
        assertEquals(mapTrip.get(JSON_SIZE), this.size);
        assertEquals(mapTrip.get(JSON_OWNER), this.owner);
        assertEquals(mapTrip.get(JSON_USERS_NAMES), this.usernames.get(0) + "\n");
        assertEquals(mapTrip.get(JSON_USERS_IDS), this.userids.get(0) + "\n");
        assertEquals(mapTrip.get(JSON_LOC), this.locations.get(0).getTitle() + "\n");
    }

    /*
     *  Checks that a Json object containing the trip data is returned
     */
    @Test
    public void toJSON() throws Exception {
        JSONObject jsonTrip = trip.toJSON();

        assertEquals(jsonTrip.get(JSON_ID), this.id);
        assertEquals(jsonTrip.get(JSON_NAME), this.name);
        assertEquals(jsonTrip.get(JSON_DESC), this.description);
        assertEquals(jsonTrip.get(JSON_DATE), this.date);
        assertEquals(jsonTrip.get(JSON_COST), this.cost);
        assertEquals(jsonTrip.get(JSON_SIZE), this.size);
        assertEquals(jsonTrip.get(JSON_OWNER), this.owner);
        assertEquals(jsonTrip.get(JSON_USERS_NAMES),  new JSONArray().put(this.usernames.get(0)));
        assertEquals(jsonTrip.get(JSON_USERS_IDS), new JSONArray().put(this.userids.get(0)));
        assertEquals(jsonTrip.get(JSON_LOC), new JSONArray().put(this.locations.get(0).toJSON()));
    }

    /*
     * Tests getter for id field
     */
    @Test
    public void getId() throws Exception {
        assertEquals(trip.getId(), this.id);
    }

    /*
     * Tests getter for locations field
     */
    @Test
    public void getLocations() throws Exception {
        assertEquals(trip.getLocations(), this.locations);
    }

    /*
     * Tests getter for description field
     */
    @Test
    public void getDescription() throws Exception {
        assertEquals(trip.getDescription(), this.description);
    }

    /*
     * Tests getter for size field
     */
    @Test
    public void getSize() throws Exception {
        assertEquals(trip.getSize(), this.size);
    }

    /*
     * Tests getter for name field
     */
    @Test
    public void getName() throws Exception {
        assertEquals(trip.getName(), this.name);
    }

    /*
     * Tests getter for cost field
     */
    @Test
    public void getCost() throws Exception {
        assertEquals(trip.getCost(), this.cost);
    }

    /*
     * Tests getter for url field
     */
    @Test
    public void getUrl() throws Exception {
        assertEquals(trip.getUrl(), this.url);
    }

    /*
     * Tests getter for usernames field
     */
    @Test
    public void getUsernames() throws Exception {
        assertEquals(trip.getUsernames(), this.usernames);
    }

    /*
     * Tests getter for iuser ids field
     */
    @Test
    public void getUserIds() throws Exception {
        assertEquals(trip.getUserids(), this.userids);
    }

}
