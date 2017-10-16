package com.unimelbit.teamcobalt.tourlist.Tracking;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by awhite on 15/10/17.
 */
public class GoogleMapTrackingHandlerTest {

    GoogleMapTrackingHandler handler;
    Context context;

    @Before
    public void setUp() throws Exception {
        this.context = mock(Context.class);
        this.handler = new GoogleMapTrackingHandler(context);
    }

    @Test
    public void makeUserTracker() throws Exception {
//        TODO: Need to find out how to mock firebase
//
//        UserTracker tracker = handler.makeUserTracker("user", context);
//        assertNotNull(tracker);
    }

    @Test
    public void getUserMarker() throws Exception {
//        Doesn't work at the moment, when() only works on methods
//        no instance variables
//
//        UserTracker tracker = mock(UserTracker.class);
//        when(tracker.LAT_INDEX).thenReturn(0);
//        when(tracker.LONG_INDEX).thenReturn(0);
//        Bitmap icon = mock(Bitmap.class);
//        when(tracker.getUserIcon()).thenReturn(icon);
//
//        MarkerOptions marker = handler.getUserMarker("user", tracker);
//        LatLng latLng = new LatLng(0, 0);
//        MarkerOptions marker2 = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(icon));
//        assertEquals(marker, marker2);
    }

    @Test
    public void getUserList() throws Exception {


//        TODO: Need to find out how to mock firebase
//
//        ArrayList<String > usernames = new ArrayList<>();
//        ArrayList<String> userids = new ArrayList<>();
//        userids.add("0");
//        usernames.add("user");
//
//        handler.putIntoUserList(userids, usernames);
//        handler.getUserList();
//
//        HashMap<String, UserTracker> userList = handler.getUserList();
//
//        assertTrue(userList.containsKey("0"));

        assertTrue(handler.getUserList().isEmpty());

    }


    @Test
    public void removeUserMarkers() throws Exception {

    }

    @Test
    public void getAllMarkers() throws Exception {

    }

    @Test
    public void initUserMarkers() throws Exception {

    }

    @Test
    public void initLocationMarkers() throws Exception {

    }

    @Test
    public void putIntoUserList() throws Exception {

    }

    @Test
    public void getMarkersOnMap() throws Exception {

        assertTrue(handler.getMarkersOnMap().isEmpty());

    }

    @Test
    public void getMarkerList() throws Exception {

        assertTrue(handler.getMarkerList().isEmpty());

    }

}