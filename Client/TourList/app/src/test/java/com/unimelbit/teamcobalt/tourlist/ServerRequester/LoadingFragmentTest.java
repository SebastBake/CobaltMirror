package com.unimelbit.teamcobalt.tourlist.ServerRequester;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by awhite on 6/10/17.
 *
 * Not much to test as Fragments are mostly interface/UI
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class LoadingFragmentTest {

    LoadingFragment loadingFragment;

    @Before
    public void setUp() throws Exception {
        this.loadingFragment = new LoadingFragment();
    }

    @Test
    public void newInstance() throws Exception {
        // verifies that an instance is returned
        assertNotNull(loadingFragment.newInstance());
    }

}