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
 * Check androidTest folder for tests of AddDateToTripDialogFragment.
 * The class has no application logic to test,
 * it creates a Fragment used to set the date of a trip
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class CreateOrEditTripFragmentTest {

    CreateOrEditTripFragment coetf;

    /*
     * Creates a Fragment before each test
     */
    @Before
    public void setUp() throws Exception {
        coetf = new CreateOrEditTripFragment();
    }

    /*
     * Checks that a new instance is constructed with the correct properties
     */
    @Test
    public void newInstance() throws Exception {
        Fragment fragment = coetf.newInstance();
        assertNotNull(fragment);
        assertNotEquals(fragment, coetf.newInstance());
    }


}
