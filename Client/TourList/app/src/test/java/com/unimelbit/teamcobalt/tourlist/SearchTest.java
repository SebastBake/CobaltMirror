package com.unimelbit.teamcobalt.tourlist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.unimelbit.teamcobalt.tourlist.Search.SearchFragment;
import com.unimelbit.teamcobalt.tourlist.Search.SearchResultFragment;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
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
            SearchResultFragment fragment = new SearchResultFragment();
            Bundle args = new Bundle();
            args.putString(SearchResultFragment.ARG_TEXT, "demo");
            fragment.setArguments(args);
            SupportFragmentTestUtil.startFragment(fragment);
            assertNotNull(fragment);
        }



}
