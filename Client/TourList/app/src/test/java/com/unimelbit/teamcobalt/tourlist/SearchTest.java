package com.unimelbit.teamcobalt.tourlist;


import android.os.Bundle;

import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchResultFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;


/**
 * Created by spike on 9/9/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class)
public class SearchTest {


        @Test
        public void shouldNotBeNull() throws Exception
        {
            TripSearchResultFragment fragment = new TripSearchResultFragment();
            Bundle args = new Bundle();
            args.putString(TripSearchResultFragment.ARG_SEARCH_QUERY, "demo");
            fragment.setArguments(args);
            SupportFragmentTestUtil.startFragment(fragment);
            assertNotNull(fragment);
        }



}
