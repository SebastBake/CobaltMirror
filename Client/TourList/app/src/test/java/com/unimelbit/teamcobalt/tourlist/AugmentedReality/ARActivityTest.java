package com.unimelbit.teamcobalt.tourlist.AugmentedReality;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNull;

/**
 * Created by awhite on 16/10/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class ARActivityTest {


    /**
     * Check androidTest folder for tests of ARActivity.
     * The class has no application logic to test,
     * it creates an Activity and View for the AR page
     */

    @Test
    public void getArchitectView() throws Exception {
        ARActivity activity = new ARActivity();
        assertNull(activity.getArchitectView());
    }



}