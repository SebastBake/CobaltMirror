package com.unimelbit.teamcobalt.tourlist;

import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.Model.User;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

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
@Config(manifest=Config.NONE)
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

    @Test
    public void getMainContainerManager() throws Exception {
//        BaseFragmentContainerManager manager = base.getMainContainerManager();
//        assertNotNull(manager);
    }

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
    public void setLocationSharing() throws Exception {
        assertFalse(base.locationSharing);

        base.setLocationSharing(true);
        assertTrue(base.locationSharing);

        base.setLocationSharing(false);
        assertFalse(base.locationSharing);

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
        assertFalse(base.isLocationSharingOn());

        base.setLocationSharing(true);
        assertTrue(base.isLocationSharingOn());

        base.setLocationSharing(false);
        assertFalse(base.isLocationSharingOn());

    }

    @Test
    public void getSharedPreferences() throws Exception {

        assertEquals(base.getSharedPreferences(), Whitebox.getInternalState(base, "sharedpreferences"));

    }

    @Test
    public void getMainContainer() throws Exception {

        assertEquals(base.getMainContainer(), Whitebox.getInternalState(base, "mainContainer"));

    }

    @Test
    public void setGetSearchedTrip() throws Exception {
        assertEquals(base.getSearchedTrip(), Whitebox.getInternalState(base, "searchedTrip"));

        Trip trip = mock(Trip.class);
        base.setSearchedTrip(trip);

        assertEquals(base.getSearchedTrip(), trip);

    }

}
