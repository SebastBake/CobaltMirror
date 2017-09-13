package com.unimelbit.teamcobalt.tourlist.Trip;

import android.support.v4.app.Fragment;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetProcessor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sebastian on 13/9/17.
 */

public class TripGetProcessor implements GetProcessor {

    private String url;
    private BaseActivity activity;

    public TripGetProcessor(String url, BaseActivity activity ) {

        assert(url != null);
        assert(activity != null);

        this.url = url;
        this.activity = activity;
    }

    @Override
    public void processResult(String result) {

        try {
            TripDetails trip = parseJSONResult(result);
            initTabbedTripFragment(trip);
        } catch (Exception e) {
            e.printStackTrace();
            requestFailed();
        }
    }

    @Override
    public void requestFailed() {
        activity.clearFragmentContainer();
    }

    private void initTabbedTripFragment(TripDetails trip) {

        activity.setCurrentTrip(trip);

        TabbedTripFragment fragment = TabbedTripFragment.newInstance();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private TripDetails parseJSONResult(String result) throws JSONException {
        JSONArray tripJSON_array = new JSONArray(result);

        JSONObject tripJSON = tripJSON_array.getJSONObject(0);

        String name =  tripJSON.getString("name");
        String cost =  tripJSON.getString("cost");
        String size =  tripJSON.getString("size");
        JSONArray locations = tripJSON.getJSONArray("locations");
        String locations_titles = "";
        for (int j=0; j< locations.length();j++){
            JSONObject location = locations.getJSONObject(j);
            String title = location.getString("title");
            locations_titles = locations_titles + title + "\n";
        }

        return new TripDetails(name, cost, size, locations_titles, url);
    }
}
