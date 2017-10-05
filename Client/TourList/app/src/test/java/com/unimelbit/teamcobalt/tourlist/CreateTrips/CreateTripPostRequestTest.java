package com.unimelbit.teamcobalt.tourlist.CreateTrips;

import com.unimelbit.teamcobalt.tourlist.Model.Trip;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by awhite on 5/10/17.
 */

@RunWith(RobolectricTestRunner.class)
public class CreateTripPostRequestTest {
    CreateTripPostRequest ctpr;

    @Before
    public void setUp() throws Exception {
        ctpr = mock(CreateTripPostRequest.class);

        Trip trip = mock(Trip.class);
        JSONObject json = mock(JSONObject.class);
        AddLocationsToTripActivity activity = mock(AddLocationsToTripActivity.class);

        when(json.toString()).thenReturn("json");
        when(trip.toJSON()).thenReturn(json);

        ctpr.trip = trip;
        ctpr.activity = activity;
    }

    @Test
    public void processResult() throws Exception {

    }

    @Test
    public void getDataToSend() throws Exception {
        doCallRealMethod().when(ctpr).getDataToSend();
        assertEquals("json", ctpr.getDataToSend());

    }

    @Test
    public void requestFailed() throws Exception {

    }

}