package com.unimelbit.teamcobalt.tourlist.EditTrip;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.unimelbit.teamcobalt.tourlist.CreateTrips.CustomListAdapter;
import com.unimelbit.teamcobalt.tourlist.Model.Location;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.unimelbit.teamcobalt.tourlist.EditTrip.EditTripActivity.INTENT_TRIP_EXTRA;
import static com.unimelbit.teamcobalt.tourlist.EditTrip.EditTripActivity.INTENT_USER_EXTRA;


public class EditTripLocationsActivity extends AppCompatActivity {

    private int PLACE_PICKER_REQUEST = 1;

    private ArrayList<Place> placeArray;
    private Button addLocationButton;
    private Button doneAddingLocationsButton;
    private EditLocationListAdapter listAdapter;
    private Trip trip;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_trip_add_location);
        this.setTitle( "Add Locations to the Trip");

        placeArray = new ArrayList<>();
        trip = getIntent().getParcelableExtra(INTENT_TRIP_EXTRA);
        userName = getIntent().getStringExtra(INTENT_USER_EXTRA);

        initButtons();
        initLocationsList();
    }

    public Trip getTrip() {
        return this.trip;
    }

    /**
     * Floating button that starts the Places intent to get locations to add
     */
    private void initButtons() {

        addLocationButton = (Button) findViewById(R.id.add_location_button);
        addLocationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    Intent intent = builder.build(EditTripLocationsActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        doneAddingLocationsButton = (Button) findViewById(R.id.done_adding_location_button);
        doneAddingLocationsButton.setOnClickListener(new EditTripLocationsActivity.DoneButtonOnClickListener(this));
    }

    private void initLocationsList() {

        try {
            for(Location loc: trip.getLocations()) {
                placeArray.add(new MyPlace(loc));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        ListView listView = (ListView) findViewById(R.id.listView);
        listAdapter = new EditLocationListAdapter(EditTripLocationsActivity.this, R.layout.list_row, placeArray);
        listView.setAdapter(listAdapter);
    }


    /**
     * Handles the Place objects returned from the Place intent and adds them into the location array
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {

            // Add new place into place list
            Place location = PlacePicker.getPlace(this, data);
            placeArray.add(location);
            listAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Handles the pressing of the 'done' button
     */
    public class DoneButtonOnClickListener implements View.OnClickListener {

        EditTripLocationsActivity activity;

        DoneButtonOnClickListener(EditTripLocationsActivity activity) {
            this.activity = activity;
        }

        @Override
        public void onClick(View view) {
            new EditTripPutRequest(activity, getTrip());
            finish();
        }
    }


    private class MyPlace implements Place {

        private String title;
        private Double latitude;
        private Double longitude;

        MyPlace(Location loc) {
            title = loc.getTitle();
            latitude = loc.getLatitude();
            longitude = loc.getLongitude();
        }

        @Override
        public String getId() {
            return null;
        }

        @Override
        public List<Integer> getPlaceTypes() {
            return null;
        }

        @Override
        public CharSequence getAddress() {
            return null;
        }

        @Override
        public Locale getLocale() {
            return null;
        }

        @Override
        public CharSequence getName() {
            return title;
        }

        @Override
        public LatLng getLatLng() {
            return new LatLng(latitude, longitude);
        }

        @Override
        public LatLngBounds getViewport() {
            return null;
        }

        @Override
        public Uri getWebsiteUri() {
            return null;
        }

        @Override
        public CharSequence getPhoneNumber() {
            return null;
        }

        @Override
        public float getRating() {
            return 0;
        }

        @Override
        public int getPriceLevel() {
            return 0;
        }

        @Override
        public CharSequence getAttributions() {
            return null;
        }

        @Override
        public Place freeze() {
            return null;
        }

        @Override
        public boolean isDataValid() {
            return true;
        }
    }
}
