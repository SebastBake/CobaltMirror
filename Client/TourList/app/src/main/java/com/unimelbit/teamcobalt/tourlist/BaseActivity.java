package com.unimelbit.teamcobalt.tourlist;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.unimelbit.teamcobalt.tourlist.AugmentedReality.PermissionManager;
import com.unimelbit.teamcobalt.tourlist.CreateTrips.CreateTripFragment;
import com.unimelbit.teamcobalt.tourlist.Error.ErrorActivity;
import com.unimelbit.teamcobalt.tourlist.GPSLocation.FirebaseGoogleGpsProvider;
import com.unimelbit.teamcobalt.tourlist.GPSLocation.GoogleGpsProvider;
import com.unimelbit.teamcobalt.tourlist.Home.HomeFragment;
import com.unimelbit.teamcobalt.tourlist.Home.LoginOrRegisterFragment;
import com.unimelbit.teamcobalt.tourlist.Home.LoginFragment;
import com.unimelbit.teamcobalt.tourlist.Home.ProfileFragment;
import com.unimelbit.teamcobalt.tourlist.Home.RegisterFragment;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.Model.User;

import com.unimelbit.teamcobalt.tourlist.Tracking.FireBaseRequester;
import com.unimelbit.teamcobalt.tourlist.Tracking.UserTracker;
import com.unimelbit.teamcobalt.tourlist.TripSearch.SearchedTripDetailsFragment;
import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchFragment;
import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchResultFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String DEMOTRIP_NAME = "DemoTrip"; // this will be removed eventually
    private static final String STARTUP_FAIL_MESSAGE = "Couldn't start BaseActivity";
    private static final String RESUME_FAIL_MESSAGE = "Couldn't resume BaseActivity";
    private static final String PAUSE_FAIL_MESSAGE = "Couldn't pause BaseActivity";

    public static JSONObject PUT_OBJECT; // what's this?

    private static Trip currentTrip;
    private static User currentUser;
    private static Trip searchedTrip;

    private static final String LOC_SHARING_ON_MSG = "Location sharing is ON";
    private static final String LOC_SHARING_OFF_MSG = "Location sharing is OFF";
    public static Boolean locationSharing;

    public static SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    private static BaseFragmentContainerManager mainContainer;
    private PermissionManager permission;
    private GoogleGpsProvider gpsTool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_base);

        currentTrip = null;
        locationSharing = false;
        mainContainer = new BaseFragmentContainerManager(this, R.id.fragment_container);

        try {
            initNavDrawer();
            initUserPreferencesAndLogin();
            initPermissions();
            initGPSTools();

        } catch (Exception e) {
            ErrorActivity.newError(this, e, STARTUP_FAIL_MESSAGE);
        }
    }

    /**
     * Initializes the permissions manager
     */
    private void initPermissions() {
        // Permission check when initiating app
        permission = new PermissionManager() {};
        permission.checkAndRequestPermissions(this);
    }

    /**
     * Initializes the location update system
     */
    private void initGPSTools() {

        //Set up location updates
        gpsTool = AppServicesFactory.getServicesFactory().getFirebaseGpsProvider(this);
        gpsTool.createLocationRequest();
        gpsTool.callback();
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
     * Initializes the user preferences and/or directs the user to login on startup
     */
    private void initUserPreferencesAndLogin() {

        //Create Shared Preferences
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        // open home screen, no login
        if (!sharedpreferences.getString("nameKey","").isEmpty()) {

            ArrayList<User> users;

            try {
                users = User.newUserArrayFromJSON(sharedpreferences.getString("aUser",""));
                setCurrentUser(users.get(0));
                mainContainer.gotoHomeFragmentWithMessage("Logged in as: " + getCurrentUser().getUsername());

            } catch (Exception e) {
                e.printStackTrace();
                mainContainer.gotoLoginOrRegisterFragmentWithMessage("Failed to load user preferences.");
            }

        } else {
            mainContainer.gotoLoginOrRegisterFragment();
        }
    }

    // Assorted getters/setter
    public BaseFragmentContainerManager getMainContainerManager() {
        return mainContainer;
    }
    public static void setCurrentTrip(Trip trip) {
        currentTrip = trip;
    }
    public static Trip getCurrentTrip() {
        return currentTrip;
    }
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public static User getCurrentUser() {
        return currentUser;
    }
    public static void setLocationSharing(boolean share) {
        locationSharing = share;
    }
    public static boolean isLocationSharingOn() {
        return locationSharing;
    }
    public static Trip getSearchedTrip() {
        return searchedTrip;
    }
    public static void setSearchedTrip(Trip searchedTrip) {
        BaseActivity.searchedTrip = searchedTrip;
    }
    public static void setPutObject(JSONObject putObject) {
        PUT_OBJECT = putObject;
    } // what's this?

    public void toggleLocationSharing() {
        setLocationSharing(!locationSharing);

        if(isLocationSharingOn()) {
            Toast.makeText(this,LOC_SHARING_ON_MSG,
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,LOC_SHARING_OFF_MSG,
                    Toast.LENGTH_SHORT).show();
        }
    }
    public SharedPreferences getSharedPreferences () {
        return sharedpreferences;
    }
    public BaseFragmentContainerManager getMainContainer(){

        return mainContainer;
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
            if (fragments == 1 || f instanceof LoginOrRegisterFragment || f instanceof HomeFragment) {
                finish();
            } else {

                if (f instanceof BackButtonInterface){

                    Fragment fragmentInstance = new HomeFragment();

                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragmentInstance)
                            .addToBackStack(null)
                            .commit();

                //Go back to the search instead of home from the search results fragment
                } else if(f instanceof TripSearchResultFragment || f instanceof SearchedTripDetailsFragment){

                    getSupportFragmentManager().popBackStackImmediate();
                    getSupportFragmentManager().popBackStackImmediate();
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

        if (id == R.id.nav_Profile &&
                !(f instanceof LoginOrRegisterFragment) &&
                !(f instanceof ProfileFragment)
                ) {
            if (currentUser == null) {
                mainContainer.gotoLoginOrRegisterFragment();
            } else {
                mainContainer.gotoProfileFragment();
            }

        } else if (id == R.id.nav_search && !(f instanceof TripSearchFragment)) {
            mainContainer.gotoTripSearchFragment();

        } else if (id == R.id.nav_create && !(f instanceof CreateTripFragment)) {
            mainContainer.gotoCreateFragment();

        } else if (id == R.id.nav_current) {
            if (currentTrip != null) {
                mainContainer.gotoTabbedTripFragment(currentTrip);
            } else {
                mainContainer.gotoTabbedTripFragment(DEMOTRIP_NAME);
            }
        }else if (id == R.id.nav_logout){

            attemptLogOut();
        } else if (id == R.id.nav_chat_rooms){

            mainContainer.goToChatRooms();
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


    public void attemptLogOut(){

        Fragment f = getMainContainerManager().getCurrentFragment();

        if(f instanceof LoginFragment || f instanceof LoginOrRegisterFragment){

            Toast.makeText(this, "Cannot log out without logging in", Toast.LENGTH_LONG).show();

        }else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //Dialogue to display
            String message = "Are you sure you wish to logout?";

            //Direct user to location settings if they press OK, otherwise dismiss the display box
            builder.setMessage(message)
                    .setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {

                                    logOut();
                                    d.dismiss();
                                }
                            })

                    //Do no nothing if user presses 'Cancel' and close dialogue
                    .setNegativeButton("CANCEL",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    d.cancel();
                                }
                            });
            builder.create().show();
        }
    }

    public void logOut(){

        Fragment fragmentInstance = new LoginOrRegisterFragment();
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.remove("nameKey");
        editor.remove("passwordKey");
        editor.apply();

        setCurrentUser(null);

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmentInstance)
                .addToBackStack(null)
                .commit();

        Toast.makeText(this, "Logged Out", Toast.LENGTH_LONG).show();
    }

    protected void onResume() {
        super.onResume();
        try {
            if (!gpsTool.isRequestingLocation()) {
                gpsTool.startLocationUpdates();
            }
        } catch (Exception e) {
            ErrorActivity.newError(this, e, RESUME_FAIL_MESSAGE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            ((FirebaseGoogleGpsProvider)gpsTool).stopTrack(currentUser);
            gpsTool.stopLocationUpdates();
            gpsTool.setmRequestingLocationUpdates(false);

        } catch(Exception e) {
            ErrorActivity.newError(this, e, PAUSE_FAIL_MESSAGE);
        }
    }
}
