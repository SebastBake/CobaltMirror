package com.unimelbit.teamcobalt.tourlist.TripDetails;

import com.unimelbit.teamcobalt.tourlist.Home.LoginOrRegisterFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by awhite on 17/10/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class TabbedTripFragmentTest {

    /**
     * Check androidTest folder for tests of TabbedTripFragment.
     * The class has no application logic to test, it creates an
     * Fragment and View to let the user view a trips detail or
     * enter the trips group chat
     */

    @Test
    public void newInstance() throws Exception {
        assertNotNull(TabbedTripFragment.newInstance());

    }

}