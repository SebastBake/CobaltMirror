package com.unimelbit.teamcobalt.tourlist.EditTrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.unimelbit.teamcobalt.tourlist.CreateTrips.CreateTripFragment;
import com.unimelbit.teamcobalt.tourlist.CreateTrips.CustomListAdapter;
import com.unimelbit.teamcobalt.tourlist.Model.Location;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.R;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TripGetRequest;

import java.util.ArrayList;


public class EditTripLocationsActivity extends AppCompatActivity {

    private int PLACE_PICKER_REQUEST = 1;

    private ArrayList<Place> placeArray;
    private Button addLocationButton;
    private Button doneAddingLocationsButton;
    private CustomListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_trip_add_location);
        this.setTitle( "Add Locations to the Trip");

        placeArray = new ArrayList<Place>();

        initButtons();
        initLocationsList();
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
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        doneAddingLocationsButton = (Button) findViewById(R.id.done_adding_location_button);
        doneAddingLocationsButton.setOnClickListener(new EditTripLocationsActivity.DoneButtonOnClickListener(this, getTrip()));
    }

    private void initLocationsList() {

        ListView listView = (ListView) findViewById(R.id.listView);
        listAdapter = new CustomListAdapter(EditTripLocationsActivity.this, R.layout.list_row, placeArray);
        listView.setAdapter(listAdapter);
    }

    private Trip getTrip() {
        String id = getIntent().getStringExtra(CreateTripFragment.INTENT_ID);
        String name = getIntent().getStringExtra(CreateTripFragment.INTENT_NAME);
        String cost = getIntent().getStringExtra(CreateTripFragment.INTENT_COST);
        String size = getIntent().getStringExtra(CreateTripFragment.INTENT_SIZE);
        String date = getIntent().getStringExtra(CreateTripFragment.INTENT_DATE);
        String desc = getIntent().getStringExtra(CreateTripFragment.INTENT_DESC);

        ArrayList<Location> locations = Location.newLocationArrayFromPlaceArray(placeArray);
        ArrayList<String> users = new ArrayList<>();
        users.add(getIntent().getStringExtra(CreateTripFragment.INTENT_USER));
        return new Trip(id,name, desc,  date, cost, size, locations,users, TripGetRequest.DEFAULT_URL+name);
    }


    /**
     * Handles the Place objects returned from the Place intent and adds them into the location array
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {

                // Add new place into place list
                Place location = PlacePicker.getPlace(this, data);
                placeArray.add(location);
                listAdapter.notifyDataSetChanged();
            }
        }
    }

    public class DoneButtonOnClickListener implements View.OnClickListener {

        EditTripLocationsActivity activity;
        Trip newTrip;

        DoneButtonOnClickListener(EditTripLocationsActivity activity, Trip trip) {
            this.activity = activity;
            this.newTrip = trip;
        }

        @Override
        public void onClick(View view) {
            Trip newTrip = getTrip();
            new EditTripPutRequest(activity, newTrip);
            finish();
        }
    }
}
