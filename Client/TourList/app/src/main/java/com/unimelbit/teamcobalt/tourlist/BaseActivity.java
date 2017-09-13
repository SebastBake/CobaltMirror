package com.unimelbit.teamcobalt.tourlist;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.AugmentedReality.PermissionManager;
import com.unimelbit.teamcobalt.tourlist.CreateTrips.CreateTripFragment;
import com.unimelbit.teamcobalt.tourlist.Search.SearchResultFragment;
import com.unimelbit.teamcobalt.tourlist.Search.SearchFragment;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequester;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.LoadingFragment;
import com.unimelbit.teamcobalt.tourlist.Trip.TripDetails;
import com.unimelbit.teamcobalt.tourlist.Trip.TripGetProcessor;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchFragment.OnSearchListener {

    // current trip
    private static TripDetails currentTrip = null;

    //Permission manager
    private PermissionManager permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initNavDrawer();

        // open demo trip for demo
        initTabbedTripFragment("https://cobaltwebserver.herokuapp.com/api/trips/DemoTrip");

        //Permission check when initiating app
        permission = new PermissionManager() {};
        permission.checkAndRequestPermissions(this);
    }

    private void initNavDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Set toolbar as action bar
        setSupportActionBar(toolbar);

        // Attach toggle to the drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState(); // What does this do?
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initTabbedTripFragment(String tripURL) {
        TripGetProcessor processor = new TripGetProcessor(tripURL, this);
        LoadingFragment fragment = LoadingFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        new GetRequester(processor).execute(tripURL);
    }

    // Create search fragment -spike
    private void initSearchFragment() {
        clearFragmentContainer();
        SearchFragment fragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void setCurrentTrip(TripDetails trip) {
        currentTrip = trip;
    }
    public TripDetails getCurrentTrip() {
        return currentTrip;
    }

    // Create trips
    private void initCreateFragment() {
        clearFragmentContainer();
        CreateTripFragment fragment = new CreateTripFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void clearFragmentContainer() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (f!=null) {
            getSupportFragmentManager().beginTransaction().remove(f).commit();
        }
    }

    // On search button press start search result fragment and send text over - spike
    @Override
    public void onSearch(String text) {
        clearFragmentContainer();
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putString(SearchResultFragment.ARG_TEXT, text);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null).commit();
        }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Added start fragment in search part of nav
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (id == R.id.nav_Profile) {
            initTabbedTripFragment("https://cobaltwebserver.herokuapp.com/api/trips/DemoTrip");

        } else if (id == R.id.nav_search && !(f instanceof SearchFragment) ) {
            initSearchFragment();

        } else if (id == R.id.nav_create && !(f instanceof CreateTripFragment)) {
            initCreateFragment();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    Check for permissions and see if they are granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        permission.checkResult(requestCode,permissions, grantResults);
    }


}
