package com.example.spike.uitest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Trip extends AppCompatActivity {

    private String date;
    private String size;
    private String name;
    private String cost;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set it to the loading layout to do http get before loading the real layout
        setContentView(R.layout.activity_loading);
        pb = (ProgressBar) findViewById(R.id.progress);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
         name = intent.getStringExtra(Create.EXTRA_MESSAGE);
        // String date = intent.getStringExtra(Create.EXTRA_MESSAGE_TWO);
        //String size = intent.getStringExtra(Create.EXTRA_MESSAGE_THREE);
        cost = intent.getStringExtra(Create.EXTRA_MESSAGE_FOUR);


        //make GET request
        new GetDataTask().execute("https://cobaltwebserver.herokuapp.com/api/trips/"+ name);




    }
// sends it back to create , doesnt really edit yet.
    public void Edit_trip(View view) {
        Intent intent2 = new Intent(this,Create.class);
        startActivity(intent2);
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
            setContentView(R.layout.activity_trip);

            // Capture the layout's TextView and set the string as its text
            TextView textView = (TextView) findViewById(R.id.Name_Trip);
            textView.setText(name);
            TextView textView4 = (TextView) findViewById(R.id.cost_field);
            textView4.setText(cost);

            try {
               JSONArray jArray = new JSONArray(result);
                for (int i=0; i < jArray.length(); i++)
                {
                    try {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        // Pulling items from the array
                        date = oneObject.getString("date");
                        size = oneObject.getString("size");
                        Log.d("date:", date);
                        Log.d("size:", size);
                        TextView textView2 = (TextView) findViewById(R.id.Date_Trip);
                        textView2.setText(date);
                        TextView textView3 = (TextView) findViewById(R.id.size_field);
                        textView3.setText(size);

                    } catch (JSONException e) {
                        // Oops
                    }
                }
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
    }

    }
