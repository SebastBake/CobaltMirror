package com.unimelbit.teamcobalt.tourlist.TripDetails;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

/**
 * Check androidTest folder for tests of TabbedTripFragment.
 * The class has no application logic to test, it creates an
 * Fragment and View to let the user view a trips detail or
 * enter the trips group chat
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class TabbedTripFragmentTest {

    /*
     * Checks that a new instance is constructed with the correct properties
     */
    @Test
    public void newInstance() throws Exception {
        assertNotNull(TabbedTripFragment.newInstance());

    }

}
