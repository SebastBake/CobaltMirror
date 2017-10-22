package com.unimelbit.teamcobalt.tourlist.CreateOrEditTrip;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;

import java.util.Calendar;

/**
 * Popup Fragment for adding a date to a new or edited trip
 */
public class AddDateToTripDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    /**
     * Required empty public constructor
     */
    public AddDateToTripDialogFragment() {
    }

    /**
     * Factory method
     *
     * @return new date picker fragment
     */
    public static AddDateToTripDialogFragment newInstance() {
        return new AddDateToTripDialogFragment();
    }

    /**
     * Initialise the selected date in the date picker
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int year;
        int month;
        int day;

        NewTripSingleton newTrip = AppServicesFactory.getServicesFactory().getNewTrip();

        if (newTrip.day == null || newTrip.month == null || newTrip.year == null) {

            // If the NewTrip doesn't have a date in it, use the current date as default
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

        } else {
            // If the NewTrip does have a date in it, use that as default
            day = newTrip.day;
            month = newTrip.month;
            year = newTrip.year;
        }

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * Sets the date in the NewTrip
     */
    public void onDateSet(DatePicker view, int year, int month, int day) {

        NewTripSingleton newTrip = AppServicesFactory.getServicesFactory().getNewTrip();
        newTrip.day = day;
        newTrip.month = month;
        newTrip.year = year;
    }
}

