package com.unimelbit.teamcobalt.tourlist.ServerRequester;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by awhite on 6/10/17.
 */

@RunWith(RobolectricTestRunner.class)
public class LoadingFragmentTest {

    LoadingFragment loadingFragment;

    @Before
    public void setUp() throws Exception {
        this.loadingFragment = new LoadingFragment();
    }

    @Test
    public void newInstance() throws Exception {
        assertNotNull(loadingFragment.newInstance("loading"));
    }

}