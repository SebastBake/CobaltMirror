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
public class AddDateToTripDialogFragmentTest {

    /**
     * Check androidTest folder for tests of AddDateToTripDialogFragment.
     * The class has no application logic to test,
     * it creates a Fragment used to set the date of a trip
     */

    AddDateToTripDialogFragment adttdf;

    @Before
    public void setUp() throws Exception {
        adttdf = new AddDateToTripDialogFragment();
    }

    @Test
    public void newInstance() throws Exception {
        Fragment fragment = adttdf.newInstance();
        assertNotNull(fragment);
        assertNotEquals(fragment, adttdf.newInstance());
    }

}