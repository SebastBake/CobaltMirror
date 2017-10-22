package com.unimelbit.teamcobalt.tourlist.Tracking;

import android.content.Context;

import com.unimelbit.teamcobalt.tourlist.Chat.FirebaseChatRoomHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * Work in progress
 */

@RunWith(RobolectricTestRunner.class)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*" , "org.powermock.*"})
@PrepareForTest(FirebaseChatRoomHandler.class)
@Config(manifest=Config.NONE)
public class GoogleMapTrackingHandlerTest {

    GoogleMapTrackingHandler handler;
    Context context;

    /*
     * Requires some heavy mocking possibly
     * TODO: Finish GoogleMapTrackingHandler testing
     */

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

//        TODO: find out how to fix 'IBitmapDescriptorFactory is not initialized'
//
//        UserTracker tracker = mock(UserTracker.class);
//
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
    public void getMarkersOnMap() throws Exception {

        assertTrue(handler.getMarkersOnMap().isEmpty());

    }

    @Test
    public void getMarkerList() throws Exception {

        assertTrue(handler.getMarkerList().isEmpty());

    }

}