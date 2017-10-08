package com.unimelbit.teamcobalt.tourlist.EditTrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BackButtonInterface;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.R;

public class EditTripActivity extends AppCompatActivity implements View.OnClickListener, BackButtonInterface {

    public static final String INTENT_TRIP_EXTRA = "com.unimelbit.teamcobalt.tourlist.EditTrip.TRIP_EXTRA";
    public static final String INTENT_USER_EXTRA = "com.example.spike.uitest.USER_EXTRA";

    public static final String TITLE = "Edit trip";
    public static final String FORM_INCORRECTLY_FILLED_OUT = "Please fill out form correctly";

    private static final String SIZE_SML = "1-5";
    private static final String SIZE_MED = "5-10";
    private static final String SIZE_LRG = ">10";
    private static final String COST_LOW = "$";
    private static final String COST_MED = "$$";
    private static final String COST_LRG = "$$$";

    private RadioButton size_small;
    private RadioButton size_medium;
    private RadioButton size_large;
    private RadioButton cost_small;
    private RadioButton cost_medium;
    private RadioButton cost_large;
    private Button editLocationButton;

    private EditText nameText;
    private EditText dateText;
    private EditText descText;

    private Trip trip;
    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_trip);

        this.setTitle(TITLE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        processIntent();
        initForms();
        initButtons();
    }

    private void processIntent() {
        trip = getIntent().getParcelableExtra(INTENT_TRIP_EXTRA);
        userName = getIntent().getStringExtra(INTENT_USER_EXTRA);
    }

    private void initForms() {
        nameText = (EditText) findViewById(R.id.edit_trip_name_field);
        nameText.setText(trip.getName());
        dateText =  (EditText) findViewById(R.id.edit_trip_date_field);
        dateText.setText(trip.getDate());
        descText = (EditText) findViewById(R.id.edit_trip_desc_field);
        descText.setText(trip.getDescription());
    }

    private void initButtons() {

        // Size radio buttons
        size_small = (RadioButton) this.findViewById(R.id.edit_trip_Size_small);
        size_small.setOnClickListener(this);
        size_medium = (RadioButton) this.findViewById(R.id.edit_trip_Size_medium);
        size_medium.setOnClickListener(this);
        size_large = (RadioButton) this.findViewById(R.id.edit_trip_Size_large);
        size_large.setOnClickListener(this);

        // Cost radio buttons
        cost_small = (RadioButton) this.findViewById(R.id.edit_trip_Cost_small);
        cost_small.setOnClickListener(this);
        cost_medium = (RadioButton) this.findViewById(R.id.edit_trip_Cost_medium);
        cost_medium.setOnClickListener(this);
        cost_large = (RadioButton) this.findViewById(R.id.edit_trip_Cost_large);
        cost_large.setOnClickListener(this);

        editLocationButton = (Button) this.findViewById(R.id.edit_trip_buttonApply);
        editLocationButton.setOnClickListener(this);

        if(trip.getSize().equalsIgnoreCase(SIZE_SML)) {
            size_small.setChecked(true);
        } else if(trip.getSize().equalsIgnoreCase(SIZE_MED)) {
            size_medium.setChecked(true);
        } else if(trip.getSize().equalsIgnoreCase(SIZE_LRG)) {
            size_large.setChecked(true);
        } else {
            size_small.setChecked(true);
        }

        if(trip.getCost().equalsIgnoreCase(COST_LOW)) {
            cost_small.setChecked(true);
        } else if(trip.getCost().equalsIgnoreCase(COST_MED)) {
            cost_medium.setChecked(true);
        } else if(trip.getCost().equalsIgnoreCase(COST_LRG)) {
            cost_large.setChecked(true);
        } else {
            cost_small.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {

        // Is the button now checked?
        boolean checked;

        // Check which radio button was clicked
        switch(view.getId()) {

            // Size buttons
            case R.id.edit_trip_Size_small:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    trip.setSize(SIZE_SML);
                size_medium.setChecked(false);
                size_large.setChecked(false);
                break;
            case R.id.edit_trip_Size_medium:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    trip.setSize(SIZE_MED);
                size_small.setChecked(false);
                size_large.setChecked(false);
                break;
            case R.id.edit_trip_Size_large:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    trip.setSize(SIZE_LRG);
                size_medium.setChecked(false);
                size_small.setChecked(false);
                break;

            // Cost buttons
            case R.id.edit_trip_Cost_small:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    trip.setCost(COST_LOW);
                cost_medium.setChecked(false);
                cost_large.setChecked(false);
                break;
            case R.id.edit_trip_Cost_medium:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    trip.setCost(COST_MED);
                cost_small.setChecked(false);
                cost_large.setChecked(false);
                break;
            case R.id.edit_trip_Cost_large:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    trip.setCost(COST_LRG);
                cost_medium.setChecked(false);
                cost_small.setChecked(false);
                break;

            // Edit locations button
            case R.id.edit_trip_buttonApply:
                gotoEditLocationsActivity();
                break;
        }
    }

    private void gotoEditLocationsActivity() {

        trip.setName(nameText.getText().toString());
        trip.setDate(dateText.getText().toString());
        trip.setDescription(descText.getText().toString());

        boolean notFilledOut = trip.getName().isEmpty() || trip.getDate().isEmpty() || (trip.getDescription().isEmpty());

        if (notFilledOut) {
            Toast.makeText(this, FORM_INCORRECTLY_FILLED_OUT, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, EditTripLocationsActivity.class);
        intent.putExtra(INTENT_TRIP_EXTRA, trip);
        intent.putExtra(INTENT_USER_EXTRA, userName);
        startActivity(intent);
        this.getSupportFragmentManager().popBackStack();
    }
}
