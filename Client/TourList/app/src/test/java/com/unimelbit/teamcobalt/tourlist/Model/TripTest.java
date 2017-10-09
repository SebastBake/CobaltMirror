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

    Trip trip;
    String id;
    String name;
    String description;
    String date;
    String cost;
    String size;
    ArrayList<Location> locations;
    ArrayList<String> users;
    String url;

    @Before
    public void setUp() throws Exception {

        this.id = "0";
        this.name = "name";
        this.description = "description";
        this.date = "date";
        this.cost = "cost";
        this.size = "size";
        this.locations = new ArrayList<>();
        this.users = new ArrayList<>();
        this.url = "www.url.com";

        Location location = mock(Location.class);
        when(location.getTitle()).thenReturn("title");
        when(location.toJSON()).thenReturn(mock(JSONObject.class));

        locations.add(mock(Location.class));
        users.add("user");

        this.trip = new Trip(
                this.id,
                this.name,
                this.description,
                this.date,
                this.cost,
                this.size,
                this.locations,
                this.users,
                this.url);
    }

    @Test
    public void newTripArrayFromJSON() throws Exception {

    }

    @Test
    public void toMap() throws Exception {
        Map<String, String> mapTrip = trip.toMap();

        assertEquals(mapTrip.get("_id"), this.id);
        assertEquals(mapTrip.get("name"), this.name);
        assertEquals(mapTrip.get("description"), this.description);
        assertEquals(mapTrip.get("date"), this.date);
        assertEquals(mapTrip.get("cost"), this.cost);
        assertEquals(mapTrip.get("size"), this.size);
        assertEquals(mapTrip.get("users"), this.users.get(0) + "\n");
        assertEquals(mapTrip.get("locations"), this.locations.get(0).getTitle() + "\n");
    }

    @Test
    public void toJSON() throws Exception {
        JSONObject jsonTrip = trip.toJSON();

        assertEquals(jsonTrip.get("_id"), this.id);
        assertEquals(jsonTrip.get("name"), this.name);
        assertEquals(jsonTrip.get("description"), this.description);
        assertEquals(jsonTrip.get("date"), this.date);
        assertEquals(jsonTrip.get("cost"), this.cost);
        assertEquals(jsonTrip.get("size"), this.size);
        assertEquals(jsonTrip.get("users"), new JSONArray().put(this.users.get(0)));
        assertEquals(jsonTrip.get("locations"), new JSONArray().put(this.locations.get(0).toJSON()));
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
    public void getUsers() throws Exception {
        assertEquals(trip.getUsers(), this.users);
    }

}