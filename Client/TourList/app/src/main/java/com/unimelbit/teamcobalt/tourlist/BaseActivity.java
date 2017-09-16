package com.unimelbit.teamcobalt.tourlist;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.unimelbit.teamcobalt.tourlist.AugmentedReality.PermissionManager;
import com.unimelbit.teamcobalt.tourlist.CreateTrips.CreateTripFragment;
import com.unimelbit.teamcobalt.tourlist.Home.HomeFragment;
import com.unimelbit.teamcobalt.tourlist.Home.LoginFragment;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.Model.User;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.LoadingFragment;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TabbedTripFragment;
import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchFragment;
import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchResultFragment;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static String DEMOTRIP_URL = "https://cobaltwebserver.herokuapp.com/api/trips/DemoTrip";

    // current trip and user
    private static Trip currentTrip;
    private static User currentUser;

    // Permission manager
    private PermissionManager permission;

    //Flag for loading
    private boolean loading;

    // Manager of main fragment
    private static BaseFragmentContainerManager mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        currentTrip = null;
        mainContainer = new BaseFragmentContainerManager(this, R.id.fragment_container);

        initNavDrawer();

        loading = false;

        // open home screen, no login
        mainContainer.gotoHomeFragment();

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

    // Assorted getters/setter
    public BaseFragmentContainerManager getMainContainerManager() {
        return mainContainer;
    }
    public void setCurrentTrip(Trip trip) {
        currentTrip = trip;
    }
    public Trip getCurrentTrip() {
        return currentTrip;
    }
    public void setCurrentUser(User user) {
        currentUser = user;
    }
    public User getCurrentUser() {
        return currentUser;
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
            Fragment f = getMainContainerManager().getCurrentFragment();
            int fragments = getSupportFragmentManager().getBackStackEntryCount();
            if (fragments == 1 || f instanceof HomeFragment) {
                finish();
            } else {

                if(fragments == 2 || f instanceof TabbedTripFragment){
                    setTitle("Base Activity");

                }

                if (f instanceof ErrorFragment){

                    Fragment fragmentInstance = new HomeFragment();

                    setTitle("Base Activity");

                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragmentInstance)
                            .addToBackStack(null)
                            .commit();

                    setLoading(false);

                }

                else if(isLoading()){

                    getSupportFragmentManager().popBackStackImmediate();
                    getSupportFragmentManager().popBackStackImmediate();
                    setLoading(false);
                    //getFragmentManager().popBackStack();

                }

                else if (getFragmentManager().getBackStackEntryCount() > 1) {
                    getSupportFragmentManager().popBackStackImmediate();
                } else {
                    super.onBackPressed();
                }
            }
        }
    }

    /**
     * Links menu items in the nav drawer to the methods defining their functionality
     * @param item The menu item tapped by the user
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Fragment f = getSupportFragmentManager().findFragmentById(mainContainer.getContainerId());

        if (id == R.id.nav_Profile && !(f instanceof HomeFragment) && !(f instanceof LoginFragment)) {
            mainContainer.gotoHomeFragment();

        } else if (id == R.id.nav_search && !(f instanceof TripSearchFragment)) {
            mainContainer.gotoTripSearchFragment();

        } else if (id == R.id.nav_create && !(f instanceof CreateTripFragment)) {
            mainContainer.gotoCreateFragment();

        } else if (id == R.id.nav_current) {
            if (currentTrip != null) {
                mainContainer.gotoTabbedTripFragment(currentTrip);
            } else {
                mainContainer.gotoTabbedTripFragment(DEMOTRIP_URL);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    /**
     * Check for permissions and see if they are granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        permission.checkResult(requestCode,permissions, grantResults);
    }

    public boolean isLoading(){

        return loading;
    }

    public void setLoading(boolean t){

        loading = t;
    }

}
