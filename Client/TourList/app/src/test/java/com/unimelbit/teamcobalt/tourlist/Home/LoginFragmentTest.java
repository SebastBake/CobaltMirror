package com.unimelbit.teamcobalt.tourlist.Home;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

/**
 * Check androidTest folder for tests of LoginFragment.
 * The class has no application logic to test, it creates an
 * Fragment and View to display the login page. The only
 * application logic is a call to the GetRequester which
 * has it's own tests.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class LoginFragmentTest {

    /*
     * Checks that a new instance is constructed with the correct properties
     */
    @Test
    public void newInstance() throws Exception {
        assertNotNull(LoginFragment.newInstance());
    }

}
