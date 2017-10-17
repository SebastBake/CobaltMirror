package com.unimelbit.teamcobalt.tourlist.Home;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by awhite on 17/10/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class LoginFragmentTest {

    /**
     * Check androidTest folder for tests of LoginFragment.
     * The class has no application logic to test, it creates an
     * Fragment and View to display the login page. The only
     * application logic is a call to the GetRequester which
     * has it's own tests.
     */

    @Test
    public void newInstance() throws Exception {
        assertNotNull(LoginFragment.newInstance());
    }

}