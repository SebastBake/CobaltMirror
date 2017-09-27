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

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class)
public class SearchTest {

        //Simple test that search is not null
        @Test
        public void shouldNotBeNull() throws Exception
        {
            TripSearchFragment fragment = new TripSearchFragment();
            SupportFragmentTestUtil.startFragment(fragment);
            assertNotNull(fragment);
        }



}
