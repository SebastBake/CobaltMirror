package com.unimelbit.teamcobalt.tourlist.CreateTrips;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 *
 */
public class AddDateToTripDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public AddDateToTripDialogFragment() {
        // Required empty public constructor
    }

    /**
     */
    public static AddDateToTripDialogFragment newInstance() {
        AddDateToTripDialogFragment fragment = new AddDateToTripDialogFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        NewTripSingleton newTrip = NewTripSingleton.getInstance();
        int year;
        int month;
        int day;

        if(newTrip.day == null || newTrip.month == null || newTrip.year == null) {

            //Use the current date as the default date in the date picker
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

        } else {
            day = newTrip.day;
            month = newTrip.month;
            year = newTrip.year;
        }

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        NewTripSingleton newTrip = NewTripSingleton.getInstance();
        newTrip.day = day;
        newTrip.month = month;
        newTrip.year = year;
    }
}

