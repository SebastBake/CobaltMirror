package com.unimelbit.teamcobalt.tourlist.AugmentedReality;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.GPSLocation.GoogleGpsProvider;
import com.unimelbit.teamcobalt.tourlist.Model.User;
import com.unimelbit.teamcobalt.tourlist.R;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TabbedTripFragment;
import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

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
    private ArrayList<String> tripUsernames;
    private ArrayList<String> tripUserids;
    private User user;

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
//        tripUserids = getIntent().getStringArrayListExtra(TabbedTripFragment.INTENT_TRIP_USERIDS);
//        tripUsernames = getIntent().getStringArrayListExtra(TabbedTripFragment.INTENT_TRIP_USERNAMES);
//        user = getIntent().getParcelableExtra(TabbedTripFragment.INTENT_USER);

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




    /**Create view with the JS and assets provided in this function
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        architectView.onPostCreate();
        try {
            this.architectView.load(assetLoc);
            this.architectView.callJavascript("World.newData('" + tripId +"')");
            //sendUserList(this.architectView);


        } catch (Exception e) {

        }

    }

    /**
     * Send the user list to the AR list view to handle in the AR view
     * @param architectView
     * @throws JSONException
     */
//    private void sendUserList(ArchitectView architectView) throws JSONException {
//        JSONArray array = new JSONArray();
//
//        //Loop through and place the user into a json to send
//        for(int i = 0;i < tripUsernames.size();i++){
//            if(!Objects.equals(tripUsernames.get(i),user.getUsername()) &&
//                    !Objects.equals(tripUserids.get(i),user.getId()) ){
//                JSONObject object = new JSONObject();
//                object.put("username",tripUsernames.get(i));
//                object.put("userid",tripUserids.get(i));
//                array.put(object);
//            }
//        }
//        architectView.callJavascript("World.userMarkers('" + array +"')");
//    }


    /** Resume app settings
     */
    @Override
    protected void onResume() {
        super.onResume();
        architectView.onResume();
        if (!arGpsTool.isRequestingLocation()) {
            arGpsTool.startLocationUpdates();
        }

    }

    /** Destroy activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        architectView.onDestroy();

    }

    /** Settings when acitivty paused
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
