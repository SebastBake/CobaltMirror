package com.unimelbit.teamcobalt.tourlist.GPSLocation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by awhite on 17/10/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class FirebaseGoogleGpsProviderTest {

    /**
     * Can't test without creating a mock Firebase database
     * and modifying the class so you can specify the database
     * to post to. Easily tested manually by running the app
     * and looking at the firebase database to see if data is
     * posted.
     */

    @Test
    public void stubTest() throws Exception {

    }

}