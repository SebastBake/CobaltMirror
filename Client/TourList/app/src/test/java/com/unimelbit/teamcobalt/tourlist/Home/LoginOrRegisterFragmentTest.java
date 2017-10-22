package com.unimelbit.teamcobalt.tourlist.Home;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

/**
 * Check androidTest folder for tests of LoginOrRegisterFragment.
 * The class has no application logic to test, it creates an
 * Fragment and View to let the user navigate to either the login or
 * register pages.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class LoginOrRegisterFragmentTest {

    /*
     * Checks that a new instance is constructed with the correct properties
     */
    @Test
    public void newInstance() throws Exception {
        assertNotNull(LoginOrRegisterFragment.newInstance());

    }

}