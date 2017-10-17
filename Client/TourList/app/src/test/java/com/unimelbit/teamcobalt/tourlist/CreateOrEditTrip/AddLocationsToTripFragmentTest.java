package com.unimelbit.teamcobalt.tourlist.CreateOrEditTrip;

import android.support.v4.app.Fragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by awhite on 16/10/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class AddLocationsToTripFragmentTest {
    /**
     * Check androidTest folder for tests of AddLocationToTripDialogFragment.
     * The class has no application logic to test,
     * it creates a Fragment and View used to add locations to a trip.
     */

    AddLocationsToTripFragment alttdf;

    @Before
    public void setUp() throws Exception {
        alttdf = new AddLocationsToTripFragment();
    }

    @Test
    public void newInstance() throws Exception {
        Fragment fragment = alttdf.newInstance();
        assertNotNull(fragment);
        assertNotEquals(fragment, alttdf.newInstance());
    }


}