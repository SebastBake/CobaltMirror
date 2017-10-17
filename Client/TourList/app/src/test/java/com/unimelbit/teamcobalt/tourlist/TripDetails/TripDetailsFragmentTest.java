package com.unimelbit.teamcobalt.tourlist.TripDetails;

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
public class TripDetailsFragmentTest {

    /**
     * Check androidTest folder for tests of TripDetailsFragment.
     * The class has no application logic to test, it creates an
     * Fragment and View that shows the user the details of a trip
     */

    @Test
    public void newInstance() throws Exception {
        assertNotNull(TripDetailsFragment.newInstance());

    }

}