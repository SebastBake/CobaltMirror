package com.unimelbit.teamcobalt.tourlist.CreateTrips;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.unimelbit.teamcobalt.tourlist.BackButtonInterface;
import com.unimelbit.teamcobalt.tourlist.R;


public class CreateTripFragment extends Fragment implements View.OnClickListener, BackButtonInterface {

    public static final int TAB_INDEX = 0;
    public static final String TAB_TITLE = "Details";

    private RadioButton size_small;
    private RadioButton size_medium;
    private RadioButton size_large;
    private RadioButton cost_small;
    private RadioButton cost_medium;
    private RadioButton cost_large;
    private EditText nameText;
    private EditText descText;
    private Button getDateBtn;

    public CreateTripFragment() {
    }

    public static CreateTripFragment newInstance() {
        CreateTripFragment fragment = new CreateTripFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_trip, container, false);
        initButtons(rootView);
        initTextBoxes(rootView);
        return rootView;
    }

    private void initButtons(View rootView) {

        // Size radio buttons
        size_small = (RadioButton) rootView.findViewById(R.id.Size_small);
        size_small.setOnClickListener(this);
        size_medium = (RadioButton) rootView.findViewById(R.id.Size_medium);
        size_medium.setOnClickListener(this);
        size_large = (RadioButton) rootView.findViewById(R.id.Size_large);
        size_large.setOnClickListener(this);

        // Cost radio buttons
        cost_small = (RadioButton) rootView.findViewById(R.id.Cost_small);
        cost_small.setOnClickListener(this);
        cost_medium = (RadioButton) rootView.findViewById(R.id.Cost_medium);
        cost_medium.setOnClickListener(this);
        cost_large = (RadioButton) rootView.findViewById(R.id.Cost_large);

        getDateBtn = (Button) rootView.findViewById(R.id.button_set_date);
        getDateBtn.setOnClickListener(this);
    }

    private void initTextBoxes(View rootView) {

        nameText = (EditText) rootView.findViewById(R.id.create_trip_name_field);
        descText = (EditText) rootView.findViewById(R.id.create_trip_desc_field);

        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) { updateEditTextBoxes(); }
        };

        nameText.addTextChangedListener(textWatcher);
        descText.addTextChangedListener(textWatcher);
    }

    @Override
    public void onClick(View view) {

        // Is the button now checked?
        int clickedId = view.getId();
        boolean clickedSizeRadio = clickedId == R.id.Size_small || clickedId == R.id.Size_medium || clickedId == R.id.Size_large;
        boolean clickedCostRadio = clickedId == R.id.Cost_small || clickedId == R.id.Cost_medium || clickedId == R.id.Cost_large;

        if (clickedCostRadio) {
            resetCostRadioButtons((RadioButton) view);

        } else if (clickedSizeRadio) {
            resetSizeRadioButtons((RadioButton) view);

        } else {
            switch(clickedId) {

                case R.id.button_set_date:

                    DialogFragment newFragment = new AddDateToTripDialogFragment();
                    newFragment.show(getFragmentManager(),"Date Picker");
                    break;
            }
        }
    }

    private void resetCostRadioButtons(RadioButton checked) {
        cost_small.setChecked(false);
        cost_medium.setChecked(false);
        cost_large.setChecked(false);
        checked.setChecked(true);
        NewTripSingleton.getInstance().cost = checked.getText().toString();
    }

    private void resetSizeRadioButtons(RadioButton checked) {
        size_small.setChecked(false);
        size_medium.setChecked(false);
        size_large.setChecked(false);
        checked.setChecked(true);
        NewTripSingleton.getInstance().size = checked.getText().toString();
    }

    protected void updateEditTextBoxes() {
        NewTripSingleton newTrip = NewTripSingleton.getInstance();
        newTrip.name = nameText.getText().toString();
        newTrip.description = descText.getText().toString();
    }
}
