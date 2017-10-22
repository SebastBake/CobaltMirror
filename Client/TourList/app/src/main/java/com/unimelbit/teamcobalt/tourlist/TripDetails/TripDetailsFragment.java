package com.unimelbit.teamcobalt.tourlist.TripDetails;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess.ErrorActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Location;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

import static com.unimelbit.teamcobalt.tourlist.R.color.scheme1_green;

/**
 * A fragment that displays the details of the trip the user is in
 */
public class TripDetailsFragment extends Fragment implements View.OnClickListener {

    public static final int TRIP_SECTION_INDEX = 0;
    private boolean imageLoaded;
    private ImageView imageDetail;
    private PlaceImageLoader pILoader;
    private Trip trip;
    private boolean isUserInTrip;
    private Button saveButton;

    public TripDetailsFragment() {
    }

    public static TripDetailsFragment newInstance() {
        TripDetailsFragment fragment = new TripDetailsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_trip_details, container, false);

        imageDetail = (ImageView) rootView.findViewById(R.id.imageView2);
        pILoader = new PlaceImageLoader(getActivity());

        //Only load if there is a trip in the first place
        if (BaseActivity.getCurrentTrip() != null) {

            trip = BaseActivity.getCurrentTrip();
            initTextBoxes(rootView, trip);
            initLocationsList(rootView, trip);
            saveButton = (Button) rootView.findViewById(R.id.save_button);
            saveButton.setOnClickListener(this);

            imageLoaded = false;

        } else {
            ErrorActivity.newError(getActivity(),"No current trip!");
        }
        //Check if user is in trip
        isUserInTrip = BaseActivity.getCurrentTrip().getUserids().contains(BaseActivity.getCurrentUser().getId());

        //Set save button to leave if user is in trip
        if (isUserInTrip) {
            saveButton.setBackgroundResource(R.color.scheme1_green);
            saveButton.setText("Leave");
        }


        return rootView;
    }


    /**
     * Initialise all the text in the trip details
     * @param rootView
     * @param trip
     */
    private void initTextBoxes(View rootView, Trip trip) {

        TextView tripDescription = (TextView) rootView.findViewById(R.id.trip_details_description);
        tripDescription.setText(trip.getDescription());

        TextView tripCost = (TextView) rootView.findViewById(R.id.trip_details_cost);
        tripCost.setText("Cost: " + trip.getCost());

        TextView tripSize = (TextView) rootView.findViewById(R.id.trip_details_size);
        tripSize.setText("Max Size: " + trip.getSize());

        TextView tripDate = (TextView) rootView.findViewById(R.id.trip_details_date);
        tripDate.setText("Date: " + trip.getDate());


        TextView tripOwner = (TextView) rootView.findViewById(R.id.trip_details_owner);
        tripOwner.setText("Owner: " + trip.getOwner());
    }

    /**
     * Display a list of locations that were originally saved
     * @param rootView
     * @param trip
     */
    private void initLocationsList(View rootView, Trip trip) {

        ListView listView = (ListView) rootView.findViewById(R.id.locations_list_view);

        ArrayList<HashMap<String, String>> locationsList = new ArrayList<>();

        for (Location loc : trip.getLocations()) {
            locationsList.add(loc.toMap());

            // Add screenshot of the location on the map into the trip details image
            if (!imageLoaded) {

                pILoader.setMapImage(loc.getLatitude().toString(), loc.getLongitude().toString(), imageDetail);
                imageLoaded = true;
            }
        }

        //Adapter that displays the list
        SimpleAdapter adapter = new SimpleAdapter(
                getContext(),
                locationsList,
                R.layout.trip_details_locations_list_row,
                new String[]{Location.JSON_TITLE},
                new int[]{R.id.location_name});

        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (isUserInTrip) {
            showRemoveAlert(builder);


        } else {
            showSaveAlert(builder);
        }
    }

   private void showSaveAlert(AlertDialog.Builder builder){

       //Dialogue to display
       final String message = "Do you want to save this trip?";

       //Save trip if they press OK, otherwise dismiss the display box
       builder.setMessage(message)
               .setPositiveButton("OK",
                       new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface d, int id) {
                               try {
                                   new SaveTripRequest(trip.getId(), BaseActivity.getCurrentUser().getUsername(),
                                           BaseActivity.getCurrentUser().getId(),((BaseActivity) getActivity()));
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                               d.dismiss();
                           }
                       })
               //Do no nothing if user presses 'Cancel' and close dialogue
               .setNegativeButton("Cancel",
                       new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface d, int id) {
                               d.cancel();
                           }
                       });
       builder.create().show();

   }

    private void showRemoveAlert(AlertDialog.Builder builder){

        //Dialogue to display
        final String message = "Do you want to leave the trip?";

        //Remove trip from savedtrips if they press OK, otherwise dismiss the display box
        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                try {
                                    new RemoveTripRequest(trip.getId(), BaseActivity.getCurrentUser().getUsername(),
                                            BaseActivity.getCurrentUser().getId(),((BaseActivity) getActivity()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                d.dismiss();
                            }
                        })
                //Do no nothing if user presses 'Cancel' and close dialogue
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();

    }
}



