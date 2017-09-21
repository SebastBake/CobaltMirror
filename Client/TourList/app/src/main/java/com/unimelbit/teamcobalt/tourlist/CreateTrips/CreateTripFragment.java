package com.unimelbit.teamcobalt.tourlist.CreateTrips;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BackButtonInterface;
import com.unimelbit.teamcobalt.tourlist.Chat.ChatroomCreator;
import com.unimelbit.teamcobalt.tourlist.R;


public class CreateTripFragment extends Fragment implements View.OnClickListener, BackButtonInterface {

    public static final String INTENT_NAME = "com.example.spike.uitest.MESSAGE";
    public static final String INTENT_DATE = "com.example.spike.uitest.MESSAGE_TWO";
    public static final String INTENT_SIZE = "com.example.spike.uitest.MESSAGE_THREE";
    public static final String INTENT_COST = "com.example.spike.uitest.MESSAGE_FOUR";
    public static final String INTENT_DESC = "com.example.spike.uitest.MESSAGE_FIVE";

    private String size;
    private String cost;
    private String name;
    private String date;
    private String desc;

    private RadioButton size_small;
    private RadioButton size_medium;
    private RadioButton size_large;
    private RadioButton cost_small;
    private RadioButton cost_medium;
    private RadioButton cost_large;
    private Button apply;

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
        getActivity().setTitle( "Create a Trip");

        initButtons(rootView);

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
        cost_large.setOnClickListener(this);

        apply = (Button) rootView.findViewById(R.id.buttonApply);
        apply.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        // Is the button now checked?
        boolean checked;

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.Size_small:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    size = "1-5";
                size_medium.setChecked(false);
                size_large.setChecked(false);
                break;
            case R.id.Size_medium:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    size = "5-10";
                size_small.setChecked(false);
                size_large.setChecked(false);
                break;
            case R.id.Size_large:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    size = ">10";
                size_medium.setChecked(false);
                size_small.setChecked(false);
                break;
            case R.id.Cost_small:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    cost = "$";
                cost_medium.setChecked(false);
                cost_large.setChecked(false);
                break;
            case R.id.Cost_medium:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    cost = "$$";
                cost_small.setChecked(false);
                cost_large.setChecked(false);
                break;
            case R.id.Cost_large:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    cost = "$$$";
                cost_medium.setChecked(false);
                cost_small.setChecked(false);
                break;
            case R.id.buttonApply:

                gotoChooseLocationsActivity();
                break;
        }

    }

    private void gotoChooseLocationsActivity() {

        EditText nameText = (EditText) getView().findViewById(R.id.create_trip_name_field);
        EditText dateText =  (EditText) getView().findViewById(R.id.create_trip_date_field);
        EditText descText = (EditText) getView().findViewById(R.id.create_trip_desc_field);
        name = nameText.getText().toString();
        date = dateText.getText().toString();
        desc = descText.getText().toString();

        ChatroomCreator chatRoom = new ChatroomCreator();

        boolean notFilledOut = name.isEmpty() || date.isEmpty() || (size==null) || (cost==null) || (desc.isEmpty());

        if (notFilledOut) {
            Toast.makeText(getActivity(), "Please fill out form correctly", Toast.LENGTH_SHORT).show();
            return;
        }

        chatRoom.generateRoom(name);

        Intent intent = new Intent(getActivity(), AddLocationsToTripActivity.class);
        intent.putExtra(INTENT_NAME, name);
        intent.putExtra(INTENT_DATE, date);
        intent.putExtra(INTENT_SIZE, size);
        intent.putExtra(INTENT_COST, cost);
        intent.putExtra(INTENT_DESC, desc);
        startActivity(intent);
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
