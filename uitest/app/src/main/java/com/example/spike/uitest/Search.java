package com.example.spike.uitest;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.woxthebox.draglistview.DragListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Search extends AppCompatActivity {


    private ProgressBar pb;
    //list to display
    private ListView lv;

    // id + value kinda like json
    ArrayList<HashMap<String, String>> searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        pb = (ProgressBar) findViewById(R.id.progress);

        searchList = new ArrayList<>();
        // Right now search just displays all trips to test
        new GetDataTask().execute("https://cobaltwebserver.herokuapp.com/api/trips/findall");

    }


    // Get class, everything is here
    class GetDataTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);

        }

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
            // now that get is done, change layout,get stuff from json, display stuff
            pb.setVisibility(View.GONE);
            setContentView(R.layout.activity_search);
            lv = (ListView) findViewById(R.id.results);



            try {
                JSONArray jArray = new JSONArray(result);
                for (int i=0; i < jArray.length(); i++)
                {
                    try {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        // Pulling items from the array
                        String name = oneObject.getString("name");
                        String date = oneObject.getString("date");
                        String size = oneObject.getString("size");

                        HashMap<String, String> trip = new HashMap<>();

                        trip.put("name",name);
                        trip.put("date",date);
                        trip.put("size",size);

                        searchList.add(trip);



                    } catch (JSONException e) {
                        // Oops
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ListAdapter adapter = new SimpleAdapter(
                    Search.this, searchList,
                    R.layout.results_item, new String[]{"name", "date",
                    "size"}, new int[]{R.id.name,
                    R.id.date, R.id.size});

            lv.setAdapter(adapter);

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
    }

}




