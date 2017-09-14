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

import com.unimelbit.teamcobalt.tourlist.AugmentedReality.PermissionManager;
import com.unimelbit.teamcobalt.tourlist.CreateTrips.CreateTripFragment;
import com.unimelbit.teamcobalt.tourlist.Search.SearchFragment;
import com.unimelbit.teamcobalt.tourlist.Trip.TripDetails;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchFragment.OnSearchListener {

    private static String DEMOTRIP_URL = "https://cobaltwebserver.herokuapp.com/api/trips/DemoTrip";

    // current trip
    private static TripDetails currentTrip;

    // Permission manager
    private PermissionManager permission;

    // Manager of main fragment
    private static BaseFragmentContainerManager mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        currentTrip = null;
        mainContainer = new BaseFragmentContainerManager(this, R.id.fragment_container);

        initNavDrawer();

        // open demo trip for demo
        mainContainer.gotoTabbedTripFragment(DEMOTRIP_URL);

        //Permission check when initiating app
        permission = new PermissionManager() {};
        permission.checkAndRequestPermissions(this);
    }

    /**
     * Initializes the navigation drawer
     */
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
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * returns the base fragment container manager
     */
    public BaseFragmentContainerManager getMainContainerManager() {
        return mainContainer;
    }

    /**
     * setter for current trip
     */
    public void setCurrentTrip(TripDetails trip) {
        currentTrip = trip;
    }

    /**
     * setter for current trip
     */
    public TripDetails getCurrentTrip() {
        return currentTrip;
    }

    /**
     * Closes nav drawer when back button pressed
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Search button press start search result fragment and send text over
     */
    @Override
    public void onSearch(String text) {
        mainContainer.gotoSearchResultFragment(text);
    }

    /**
     * Links menu items in the nav drawer to the methods defining their functionality
     * @param item The menu item tapped by the user
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Fragment f = getSupportFragmentManager().findFragmentById(mainContainer.getContainerId());

        if (id == R.id.nav_Profile) {
            mainContainer.gotoTabbedTripFragment(DEMOTRIP_URL);

        } else if (id == R.id.nav_search && !(f instanceof SearchFragment) ) {
            mainContainer.gotoSearchFragment();

        } else if (id == R.id.nav_create && !(f instanceof CreateTripFragment)) {
            mainContainer.gotoCreateFragment();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Check for permissions and see if they are granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        permission.checkResult(requestCode,permissions, grantResults);
    }
}
