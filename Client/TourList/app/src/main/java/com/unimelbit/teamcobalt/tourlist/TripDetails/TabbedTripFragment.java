package com.unimelbit.teamcobalt.tourlist.TripDetails;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unimelbit.teamcobalt.tourlist.R;

public class TabbedTripFragment extends Fragment {

    private TripPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public TabbedTripFragment() {
    }

    public static TabbedTripFragment newInstance() {
        TabbedTripFragment fragment = new TabbedTripFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tabbed_trip, container, false);
        initTabs(rootView);

        getActivity().setTitle(R.string.title_fragment_current_trip);

        return rootView;
    }

    private void initTabs(View rootView) {

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new TripPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter. (View which holds the fragments)
        mViewPager = (ViewPager) rootView.findViewById(R.id.trip_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Link the pager to the tabs
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.trip_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    public class TripPagerAdapter extends FragmentPagerAdapter {

        public TripPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position) {
                case TripDetailsFragment.TRIP_SECTION_INDEX:
                    return TripDetailsFragment.newInstance();
                case ChatFragment.TRIP_SECTION_INDEX:
                    return ChatFragment.newInstance();
                case MapFragment.TRIP_SECTION_INDEX:
                    return MapFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages on the tab bar
            return 3;
        }

        // titles of the tabs
        @Override
        public CharSequence getPageTitle(int position) {

            Activity hostActivity = getActivity();

            if (hostActivity != null) {
                switch (position) {
                    case TripDetailsFragment.TRIP_SECTION_INDEX:
                        return hostActivity.getString(R.string.title_fragment_trip_details);
                    case ChatFragment.TRIP_SECTION_INDEX:
                        return hostActivity.getString(R.string.title_fragment_chat);
                    case MapFragment.TRIP_SECTION_INDEX:
                        return hostActivity.getString(R.string.title_fragment_map);
                }
            }
            return null;
        }
    }
}
