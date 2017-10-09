package com.unimelbit.teamcobalt.tourlist.AugmentedReality;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.GPSLocation.ARGoogleGpsProvider;
import com.unimelbit.teamcobalt.tourlist.GPSLocation.GoogleGpsProvider;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        arGpsTool = AppServicesFactory.getServicesFactory().getARGpsProvider(this);

        arListener = new ARJSONListener();

        //Get values for AR
        tripId = getIntent().getStringExtra(TabbedTripFragment.INTENT_TRIPID);
        tripUserids = getIntent().getStringArrayListExtra(TabbedTripFragment.INTENT_TRIP_USERIDS);
        tripUsernames = getIntent().getStringArrayListExtra(TabbedTripFragment.INTENT_TRIP_USERNAMES);
        user = getIntent().getParcelableExtra(TabbedTripFragment.INTENT_USER);

        //Initialise the request
        arGpsTool.createLocationRequest();

        //Initialise the AR framework
        architectView = (ArchitectView) this.findViewById(R.id.architectView);
        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setFeatures(ArchitectStartupConfiguration.Features.Geo);
        config.setLicenseKey("FZbpQNvIcElXwHT+V0Gytj73ElrYHjl1sanA732esQSm1ZOI5T6rnlHy/o7fLuutcXBsKMGiLqwv14AdIs0/CW67b5fMViw5z+RHoy6FnWpHnLjXqw6goOhiH7MQrCYcarqJa4XnxvvClC1NRDJXWhmWeN9uK5h/rwu/hikTOwdTYWx0ZWRfX2wCy3ZjUdwWy5VYE8Vp0OY1MA/vXDelXQZpjOZWQVFQF4PCAJ9Hmb5ZQfIiOLyFgU4sfIS1ybteLCdagpNqCKPt7usuR29mcRz2oDKWTPpFW/YRlTKNVpEQcne+uT0NrJ5V/D72CEK+IFYxq21MsHxwoAyXv4QnlUWV/j14J31VFkL5/j3UQvPICQuwmT6zzVSH5y665onpY5boqt/AnSVFnIalB8SZj3gtADVzqAV20VHZz8NQZNsdIUq6gJA4puLLKcb9LtBuyD7pWQTbK0l/PEHdBii4Zo5D/WWATvy04C8p9xcXuoOMdPthtY8a5Wq+qEf/jS3RNjZtyFJMmhEqLiv1sx7z4myHPl8JX2E/lBQNf/pYf6mEzucGtZ+Uukz0unuO3g8Swfh1VHnThGQXsqVxhYap7uqQ+HXJU7OQbj+KQ9uCTyP6Glykx4cYmo6rsbcEwjDqzdPQN5B4jR1eSpfN0kyolG3ptGw83rtopY7bI43T2f04NrA1lcMgrJhqlsvMuEPN");
        architectView.onCreate(config);

        arGpsTool.callback();

        // set JS interface listener, any calls made in JS like is forwarded to this listener, use this to interact between JS and native Android activity/fragment
        this.arListener.createListener(this);

        // set JS interface listener in architectView
        this.arListener.setListener(this.architectView);


    }




    /*
    Create view with the JS and assets provided in this function
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        architectView.onPostCreate();
        try {
            this.architectView.load("file:///android_asset/poi/index.html");
            this.architectView.callJavascript("World.newData('" + tripId +"')");
            sendUserList(this.architectView);


        } catch (Exception e) {

        }

    }

    private void sendUserList(ArchitectView architectView) throws JSONException {
        JSONArray array = new JSONArray();
        for(int i = 0;i < tripUsernames.size();i++){
            if(!Objects.equals(tripUsernames.get(i),user.getUsername()) && !Objects.equals(tripUserids.get(i),user.getId()) ){
                JSONObject object = new JSONObject();
                object.put("username",tripUsernames.get(i));
                object.put("userid",tripUserids.get(i));
                array.put(object);
            }
        }
        architectView.callJavascript("World.userMarkers('" + array +"')");
    }


    /*
    Resume app settings
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
    Destroy activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        architectView.onDestroy();

    }

    /*
    Settings when acitivty paused
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
