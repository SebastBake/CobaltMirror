package cobalt.cobalto;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.SettingsClient;
import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;


public class ARActivity extends AppCompatActivity {

    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private ArchitectView architectView;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private SettingsClient mSettingsClient;
    private Boolean mRequestingLocationUpdates = false;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(100);
        mLocationRequest.setFastestInterval(50);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationRequest();

        architectView = (ArchitectView) this.findViewById(R.id.architectView);
        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setFeatures(ArchitectStartupConfiguration.Features.Geo);
        config.setLicenseKey(" FZbpQNvIcElXwHT+V0Gytj73ElrYHjl1sanA732esQSm1ZOI5T6rnlHy/o7fLuutcXBsKMGiLqwv14AdIs0/CW67b5fMViw5z+RHoy6FnWpHnLjXqw6goOhiH7MQrCYcarqJa4XnxvvClC1NRDJXWhmWeN9uK5h/rwu/hikTOwdTYWx0ZWRfX2wCy3ZjUdwWy5VYE8Vp0OY1MA/vXDelXQZpjOZWQVFQF4PCAJ9Hmb5ZQfIiOLyFgU4sfIS1ybteLCdagpNqCKPt7usuR29mcRz2oDKWTPpFW/YRlTKNVpEQcne+uT0NrJ5V/D72CEK+IFYxq21MsHxwoAyXv4QnlUWV/j14J31VFkL5/j3UQvPICQuwmT6zzVSH5y665onpY5boqt/AnSVFnIalB8SZj3gtADVzqAV20VHZz8NQZNsdIUq6gJA4puLLKcb9LtBuyD7pWQTbK0l/PEHdBii4Zo5D/WWATvy04C8p9xcXuoOMdPthtY8a5Wq+qEf/jS3RNjZtyFJMmhEqLiv1sx7z4myHPl8JX2E/lBQNf/pYf6mEzucGtZ+Uukz0unuO3g8Swfh1VHnThGQXsqVxhYap7uqQ+HXJU7OQbj+KQ9uCTyP6Glykx4cYmo6rsbcEwjDqzdPQN5B4jR1eSpfN0kyolG3ptGw83rtopY7bI43T2f04NrA1lcMgrJhqlsvMuEPN");
        architectView.onCreate(config);


        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                    if (location != null && ARActivity.this.architectView != null) {
                        Log.i("MY CURRENT LOCATION", String.valueOf(location));
                        // check if location has altitude at certain accuracy level & call right architect method (the one with altitude information)
                        if (location.hasAltitude() && location.hasAccuracy() && location.getAccuracy() < 7) {
                            ARActivity.this.architectView.setLocation(location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getAccuracy());
                        } else {
                            ARActivity.this.architectView.setLocation(location.getLatitude(), location.getLongitude(), location.hasAccuracy() ? location.getAccuracy() : 1000);
                        }
                    }
                }
            };
        };


    }






    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        architectView.onPostCreate();
        try {
            this.architectView.load("file:///android_asset/poi/index.html");
        } catch (Exception e) {

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        architectView.onResume();
        if (!mRequestingLocationUpdates) {
            startLocationUpdates();
        }

    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        architectView.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();
        architectView.onPause();
        stopLocationUpdates();
        mRequestingLocationUpdates = false;

    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        mRequestingLocationUpdates = false;

    }


}