package com.unimelbit.teamcobalt.tourlist.Tracking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by awhite on 17/10/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class FireBaseRequesterTest {

    /**
     * Can't test without posting to Firebase database.
     * Would require mocking a Firebase database and possibly
     * restructuring the code. However it mostly just uses
     * the firebase library methods which themselves are well tested.
     */

    @Test
    public void stubTest() throws Exception {

    }

}