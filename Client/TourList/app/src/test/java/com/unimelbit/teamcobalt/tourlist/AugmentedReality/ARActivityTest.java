package com.unimelbit.teamcobalt.tourlist.AugmentedReality;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNull;

/**
 * Limited tests for ARActivity.
 *
 * Check androidTest folder in the future for tests
 * of ARActivity. The class has little application logic to test,
 * it creates an Activity and View for the AR page
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class ARActivityTest {

    /*
     * Checks that ArchitectView is returned
     */
    @Test
    public void getArchitectView() throws Exception {
        ARActivity activity = new ARActivity();
        assertNull(activity.getArchitectView());
    }

}
