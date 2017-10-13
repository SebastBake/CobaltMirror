package com.unimelbit.teamcobalt.tourlist;

import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.Model.User;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by awhite on 4/10/17.
 */
@RunWith(RobolectricTestRunner.class)
public class BaseActivityTest {

    BaseActivity base;

    @Before
    public void setUp() {
        this.base = new BaseActivity();
        base.locationSharing = false;
    }

    @Test
    public void setPutObject() throws Exception {
        assertNull(base.PUT_OBJECT);

        JSONObject testObj = new JSONObject();
        JSONObject testObj2 = new JSONObject();

        base.setPutObject(testObj);
        assertEquals(base.PUT_OBJECT, testObj);
        assertNotEquals(base.PUT_OBJECT, testObj2);

        base.setPutObject(testObj2);
        assertEquals(base.PUT_OBJECT, testObj2);
    }

//    @Test
//    public void getMainContainerManager() throws Exception {
//        BaseFragmentContainerManager manager = base.getMainContainerManager();
//        assertNotNull(manager);
//    }

    @Test
    public void setGetCurrentTrip() throws Exception {
        Trip trip = mock(Trip.class);
        assertNull(base.getCurrentTrip());

        base.setCurrentTrip(trip);
        assertEquals(trip, base.getCurrentTrip());

    }

    @Test
    public void setGetCurrentUser() throws Exception {
        User user = mock(User.class);
        assertNull(base.getCurrentUser());

        base.setCurrentUser(user);
        assertEquals(user, base.getCurrentUser());
    }

    @Test
    public void setlocationSharing() throws Exception {
//        assertFalse(base.locationSharing);
//
//        base.setlocationSharing(true);
//        assertTrue(base.locationSharing);
//
//        base.setlocationSharing(false);
//        assertFalse(base.locationSharing);

    }

    @Test
    public void toggleLocationSharing() throws Exception {
        assertFalse(base.locationSharing);

        base.toggleLocationSharing();
        assertTrue(base.locationSharing);

        base.toggleLocationSharing();
        assertFalse(base.locationSharing);
    }

    @Test
    public void isLocationSharingOn() throws Exception {
//        assertFalse(base.isLocationSharingOn());
//
//        base.setlocationSharing(true);
//        assertTrue(base.isLocationSharingOn());
//
//        base.setlocationSharing(false);
//        assertFalse(base.isLocationSharingOn());

    }

    @Test
    public void onRequestPermissionsResult() throws Exception {

    }

    @Test
    public void getMainContainer() throws Exception {

    }

    @Test
    public void attemptLogOut() throws Exception {

    }

    @Test
    public void logOut() throws Exception {

    }

    @Test
    public void setUserName() throws Exception {

    }

    @Test
    public void getUserName() throws Exception {

    }

    @Test
    public void getSearchedTrip() throws Exception {

    }

    @Test
    public void setSearchedTrip() throws Exception {

    }

    @Test
    public void setLatLong() throws Exception {

    }


}