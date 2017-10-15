package com.unimelbit.teamcobalt.tourlist.Model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by awhite on 5/10/17.
 */

@RunWith(RobolectricTestRunner.class)
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

    @Before
    public void setUp() throws Exception {

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

        Location location = mock(Location.class);
        when(location.getTitle()).thenReturn("title");
        when(location.toJSON()).thenReturn(mock(JSONObject.class));

        locations.add(mock(Location.class));
        usernames.add("user");
        userids.add("0");

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

    @Test
    public void getId() throws Exception {
        assertEquals(trip.getId(), this.id);
    }

    @Test
    public void getLocations() throws Exception {
        assertEquals(trip.getLocations(), this.locations);
    }

    @Test
    public void getDescription() throws Exception {
        assertEquals(trip.getDescription(), this.description);
    }

    @Test
    public void getSize() throws Exception {
        assertEquals(trip.getSize(), this.size);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(trip.getName(), this.name);
    }

    @Test
    public void getCost() throws Exception {
        assertEquals(trip.getCost(), this.cost);
    }

    @Test
    public void getUrl() throws Exception {
        assertEquals(trip.getUrl(), this.url);
    }

    @Test
    public void getUsernames() throws Exception {
        assertEquals(trip.getUsernames(), this.usernames);
    }

    @Test
    public void getUserIds() throws Exception {
        assertEquals(trip.getUserids(), this.userids);
    }

}