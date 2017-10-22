package com.unimelbit.teamcobalt.tourlist.Tracking;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for the UserTracker class.
 * Tracker holds information about the user location and icons to be used in the
 * live tracking map activity.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class UserTrackerTest {

    UserTracker tracker;

    /*
     * Creates a tracker before each test to be used for testing
     */
    @Before
    public void setUp() throws Exception {
        // mock used to avoid constructor
        tracker = mock(UserTracker.class);

        // makes tracker mock ise the real methods for the tests
        doCallRealMethod().when(tracker).setUserIcon(isA(Bitmap.class));
        when(tracker.getUserIcon()).thenCallRealMethod();
    }

    @Test
    public void getCoordinates() throws Exception {
        /*
         * Method interacts with firebase database so can't test without affecting
         * the database since we only have one instance of the database not a
         * development and production version.
         */
    }

    /*
     * Tests Setter and Getter for UserIcon
     */
    @Test
    public void setGetUserIcon() throws Exception {
        Bitmap userIcon = mock(Bitmap.class);

        tracker.setUserIcon(userIcon);

        assertEquals(tracker.getUserIcon(), userIcon);

    }

}
