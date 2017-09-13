package com.unimelbit.teamcobalt.tourlist.Trip;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadTripDetailsFragment extends Fragment {

    private static final String ARG_URL = "param_URL";
    private String tripURL;
    private GetDataTask download;

    public LoadTripDetailsFragment() {
    }

    public static LoadTripDetailsFragment newInstance(String tripURL) {
        LoadTripDetailsFragment fragment = new LoadTripDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, tripURL);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            tripURL = getArguments().getString(ARG_URL);

            // Tell the user that we're getting the trip details
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    "Getting trip details from:" + tripURL,
                    Toast.LENGTH_LONG).show();

            download = (GetDataTask) new GetDataTask().execute(tripURL);
        } else {
            // TODO: add some code here that sends us back to a previous page
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_load_trip_details, container, false);
        return rootView;
    }

    private void startTripFragmentOnDownloadComplete(TripDetails trip) {

        if (trip != null) {
            // Tell the user that we found the trip
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    "Found a trip:" + trip.getName(),
                    Toast.LENGTH_LONG).show();

            ((BaseActivity)getActivity()).setCurrentTrip(trip);
        } else {

            // Tell the user that we didn't find the trip
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    "Couldn't find trip at:" + tripURL,
                    Toast.LENGTH_LONG).show();
        }

        TabbedTripFragment fragment = TabbedTripFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    // Asyncronosly gets trip details from the server, then replaces this fragment when it's done
    public class GetDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                return getData(params[0]);
            } catch (IOException ex) {
                return "Network error !";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                TripDetails trip = parseJSONResult(result);
                startTripFragmentOnDownloadComplete(trip);
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }

        private String getData(String urlPath) throws IOException {

            StringBuilder result = new StringBuilder();
            BufferedReader bufferedReader =null;

            try {
                //Initialize and config request, then connect to server
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(10000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");// set header
                urlConnection.connect();

                //Read data response from server
                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }

            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }

            return result.toString();
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

            return new TripDetails(name, cost, size, locations_titles);
        }
    }
}
