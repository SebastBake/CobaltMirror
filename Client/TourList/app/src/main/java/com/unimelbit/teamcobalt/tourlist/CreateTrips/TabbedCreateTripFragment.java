package com.unimelbit.teamcobalt.tourlist.CreateTrips;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Location;
import com.unimelbit.teamcobalt.tourlist.R;

import java.util.ArrayList;

/**
 *
 */
public class TabbedCreateTripFragment extends Fragment {

    public static final int NUM_TABS = 2;
    public static final String YOU_MUST_BE_LOGGED_IN = "You must be logged in to create a trip.";

    public TabbedCreateTripFragment() {
    }

    /**
     *
     */
    public static TabbedCreateTripFragment newInstance() {
        TabbedCreateTripFragment fragment = new TabbedCreateTripFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tabbed_create_trip, container, false);
        getActivity().setTitle( "Create a Trip");
        initTabs(rootView);
        initDoneButton(rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        getActivity().findViewById(R.id.trip_tabs).setVisibility(View.GONE);
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        getActivity().findViewById(R.id.trip_tabs).setVisibility(View.GONE);
        super.onDetach();
    }

    private void initDoneButton(View rootView) {
        Button doneButton = (Button) rootView.findViewById(R.id.done_button);
        doneButton.setOnClickListener(new DoneButtonOnClickListener((BaseActivity) getActivity()));
    }

    private void initTabs(View rootView) {

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        CreateTripPagerAdapter mSectionsPagerAdapter = new CreateTripPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter. (View which holds the fragments)
        ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.create_trip_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Link the pager to the tabs
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.trip_tabs);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private class DoneButtonOnClickListener implements View.OnClickListener {

        BaseActivity activity;

        DoneButtonOnClickListener(BaseActivity activity) {
            this.activity = activity;
        }

        @Override
        public void onClick(View view) {

            NewTripSingleton newTrip = NewTripSingleton.getInstance();

            if(BaseActivity.getCurrentUser() == null) {
                Toast.makeText(getActivity(), YOU_MUST_BE_LOGGED_IN, Toast.LENGTH_LONG).show();
                return;
            } else {
                newTrip.locations = Location.newLocationArrayFromPlaceArray(newTrip.places);
                newTrip.usernames = new ArrayList<>();
                newTrip.usernames.add(BaseActivity.getCurrentUser().getUsername());
                newTrip.userIds = new ArrayList<>();
                newTrip.userIds.add(BaseActivity.getCurrentUser().getId());
                newTrip.submitNewTrip(activity);
            }
        }
    }

    private class CreateTripPagerAdapter extends FragmentPagerAdapter {

        CreateTripPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            // getItem is called to instantiate the fragment for the given page.
            switch (position) {
                case CreateTripFragment.TAB_INDEX:
                    return CreateTripFragment.newInstance();
                case AddLocationsToTripFragment.TAB_INDEX:
                    return AddLocationsToTripFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_TABS;
        }


        @Override
        public CharSequence getPageTitle(int position) {

            // titles of the tabs
            if (getActivity() != null) {
                switch (position) {
                    case CreateTripFragment.TAB_INDEX:
                        return CreateTripFragment.TAB_TITLE;
                    case AddLocationsToTripFragment.TAB_INDEX:
                        return AddLocationsToTripFragment.TAB_TITLE;
                }
            }
            return null;
        }
    }
}
