package com.unimelbit.teamcobalt.tourlist.CreateTrips;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;
import com.unimelbit.teamcobalt.tourlist.R;

import org.json.JSONObject;

import java.util.ArrayList;


public class TripsActivity extends AppCompatActivity {

    private int PLACE_PICKER_REQUEST = 1;

    private ArrayList <Place> placeArray;

    private FloatingActionButton locButton;

    private CustomListAdapter custAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        placeArray = new ArrayList<Place>();

        ListView listView = (ListView) findViewById(R.id.listView);

        initLocButton();

        custAdapter = new CustomListAdapter(TripsActivity.this, R.layout.list_row, placeArray);

        listView.setAdapter(custAdapter);

    }

    /*
    Floating button that starts the Places intent to get locations to add
     */
    private void initLocButton() {

        locButton = (FloatingActionButton) findViewById(R.id.locButton);

        locButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    Intent intent = builder.build(TripsActivity.this);

                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    /*
    Handles the Place objects returned from the Place intent and adds them into the place array
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(this, data);

                placeArray.add(place);

                String toastMsg = String.format("Place: %s", place.getName());

                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

                //List view will update accordingly
                custAdapter.notifyDataSetChanged();

            }
        }
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_trip_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* handle action bar button activities
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        //User presses this button to finish everything
        if (id == R.id.mybutton) {

            //send list to server
            send_to_server();

            finish();

        }

        return super.onOptionsItemSelected(item);
    }


    /*Create JSON object with array of places to upload to server

     */
    public void send_to_server(){

        String jsonPlaces = new Gson().toJson(placeArray);


    }


}
