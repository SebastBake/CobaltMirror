package com.unimelbit.teamcobalt.tourlist.AugmentedReality;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.GPSLocation.GoogleGpsProvider;
import com.unimelbit.teamcobalt.tourlist.R;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TabbedTripFragment;
import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

/**
 * Class that is responsible for containing and initialising the AR View.
 * It utilises services through Wikitude to implement this.
 * Google maps GPS and other relevant tools are needed for location sharing function of AR View.
 */
public class ARActivity extends AppCompatActivity {

    //SDK AR view
    private ArchitectView architectView;
    //AR tools including GPS and location services needed by AR sdk
    private GoogleGpsProvider arGpsTool;
    //Listener for JSON objects from AR sdk
    private ARJSONListener arListener;

    private String tripId;
    //Location of JS used by AR view
    private String assetLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        arGpsTool = AppServicesFactory.getServicesFactory().getARGpsProvider(this);

        assetLoc = "file:///android_asset/poi/index.html";

        //Listener for the JSONs that will be sent
        arListener = new ARJSONListener();

        //Get values for AR
        tripId = getIntent().getStringExtra(TabbedTripFragment.INTENT_TRIPID);


        //Initialise the request
        arGpsTool.createLocationRequest();

        //Initialise the AR framework
        architectView = (ArchitectView) this.findViewById(R.id.architectView);
        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setFeatures(ArchitectStartupConfiguration.Features.Geo);
        config.setLicenseKey(getString(R.string.wikitude_key));
        architectView.onCreate(config);

        arGpsTool.callback();

        // set JS interface listener, any calls made in JS like is forwarded to this listener,
        // use this to interact between JS and native Android activity/fragment
        this.arListener.createListener(this);

        // set JS interface listener in architectView
        this.arListener.setListener(this.architectView);


    }




    /*
     * Create view with the JS and assets provided in this function
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        architectView.onPostCreate();
        try {
            this.architectView.load(assetLoc);
            this.architectView.callJavascript("World.tripId('" + tripId +"')");


        } catch (Exception e) {

        }

    }


    /*
     * Resume app settings
     */
    @Override
    protected void onResume() {
        super.onResume();
        architectView.onResume();
        if (!arGpsTool.isRequestingLocation()) {
            arGpsTool.startLocationUpdates();
        }

    }

    /*
     * Destroy activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        architectView.onDestroy();

    }

    /*
     * Settings when activity paused
     */
    @Override
    protected void onPause() {
        super.onPause();
        architectView.onPause();
        arGpsTool.stopLocationUpdates();
        arGpsTool.setmRequestingLocationUpdates(false);

    }


    public ArchitectView getArchitectView() {
        return architectView;
    }
}
