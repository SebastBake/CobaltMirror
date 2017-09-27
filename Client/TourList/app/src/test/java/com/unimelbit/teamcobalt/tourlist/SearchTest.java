package com.unimelbit.teamcobalt.tourlist;




import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;


import static org.junit.Assert.assertNotNull;



/**
 * Created by spike on 9/9/2017.
 */

//For some reason, need to run test from this specific class for it to not error out.
@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class)
public class SearchTest {

        //Simple test that search is not null
        // Robo can help test fragments or individual activities if needed.
        @Test
        public void shouldNotBeNull() throws Exception
        {
            TripSearchFragment fragment = new TripSearchFragment();
            SupportFragmentTestUtil.startFragment(fragment);
            assertNotNull(fragment);
        }



}
