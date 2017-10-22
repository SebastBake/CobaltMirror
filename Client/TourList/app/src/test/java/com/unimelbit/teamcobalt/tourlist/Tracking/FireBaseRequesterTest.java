package com.unimelbit.teamcobalt.tourlist.Tracking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Can't test without posting to Firebase database.
 * Would require mocking a Firebase database and possibly
 * restructuring the code. However it mostly just uses
 * the firebase library methods which themselves are well tested.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class FireBaseRequesterTest {

    /*
     * Place holder because class needs to contain a test
     *
     * Replace when/if tests are added.
     * i.e. if application logic that doesn't  require
     *      connecting with the firebase database is
     *      added to the class.
     */

    @Test
    public void stubTest() throws Exception {

    }

}
