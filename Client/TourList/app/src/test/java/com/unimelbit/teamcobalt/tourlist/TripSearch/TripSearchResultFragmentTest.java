package com.unimelbit.teamcobalt.tourlist.TripSearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by awhite on 17/10/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class TripSearchResultFragmentTest {

    /**
     * Check androidTest folder for tests of TripSearchResultFragment.
     * The class has no application logic to test, it creates an
     * Fragment and View that shows the results of a trip search.
     */

    @Test
    public void newInstance() throws Exception {
        Fragment fragment = TripSearchResultFragment.newInstance("query");
        assertNotNull(fragment);
        Bundle args = fragment.getArguments();
        String arg = args.getString("ARG_SEARCH_QUERY");
        assertEquals(arg, "query");

    }


}