package com.unimelbit.teamcobalt.tourlist.CreateTrips;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.os.AsyncTask;

import com.unimelbit.teamcobalt.tourlist.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


import java.util.Date;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class CreateActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.spike.uitest.MESSAGE";
    public static final String EXTRA_MESSAGE_TWO = "com.example.spike.uitest.MESSAGE_TWO";
    public static final String EXTRA_MESSAGE_FOUR = "com.example.spike.uitest.MESSAGE_THREE"; ;
    public static final String EXTRA_MESSAGE_THREE = "com.example.spike.uitest.MESSAGE_FOUR"; ;
    String size;
    String cost;
    String name;
    String date;
    private String postresults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
    }

    /** Called when the user taps the Send button */
    public void Create_Trip(View view) {
        Intent intent = new Intent(this,TripsActivity.class);
        EditText NameText = (EditText) findViewById(R.id.Name_field);
        EditText DateText =  (EditText) findViewById(R.id.Date_field);
        name = NameText.getText().toString();
        date = DateText.getText().toString();
        new PostDataTask().execute("https://cobaltwebserver.herokuapp.com/api/trips/create");
        intent.putExtra(EXTRA_MESSAGE, name);
        intent.putExtra(EXTRA_MESSAGE_TWO, date);
        intent.putExtra(EXTRA_MESSAGE_THREE, size);
        intent.putExtra(EXTRA_MESSAGE_FOUR, cost);
        startActivity(intent);
        finish();
    }

    public void Search_Trip(View view){

    }
    public void AR(View view){

    }


    // i should really change this to radio group but im lazy
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        RadioButton size_small = (RadioButton) findViewById(R.id.Size_small);
        RadioButton size_medium = (RadioButton) findViewById(R.id.Size_medium);
        RadioButton size_large = (RadioButton) findViewById(R.id.Size_large);

        RadioButton cost_small = (RadioButton) findViewById(R.id.Cost_small);
        RadioButton cost_medium = (RadioButton) findViewById(R.id.Cost_medium);
        RadioButton cost_large = (RadioButton) findViewById(R.id.Cost_large);

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.Size_small:
                if (checked)
                    size = "1-5";
                size_medium.setChecked(false);
                size_large.setChecked(false);
                break;
            case R.id.Size_medium:
                if (checked)
                    size = "5-10";
                size_small.setChecked(false);
                size_large.setChecked(false);
                break;
            case R.id.Size_large:
                if (checked)
                    size = ">10";
                size_medium.setChecked(false);
                size_small.setChecked(false);
                break;
            case R.id.Cost_small:
                if (checked)
                    cost = "$";
                cost_medium.setChecked(false);
                cost_large.setChecked(false);
                break;
            case R.id.Cost_medium:
                if (checked)
                    cost = "$$";
                cost_small.setChecked(false);
                cost_large.setChecked(false);
                break;
            case R.id.Cost_large:
                if (checked)
                    cost = "$$$";
                cost_medium.setChecked(false);
                cost_small.setChecked(false);
                break;
        }
    }

    //http post and its functions
    class PostDataTask extends AsyncTask<String, Void, String> {

        ProgressBar progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //add alert here
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                return postData(params[0]);
            } catch (IOException ex) {
                return "Network error !";
            } catch (JSONException ex) {
                return "Data Invalid !";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            postresults= result;
            //add alert here
        }

        private String postData(String urlPath) throws IOException, JSONException {

            StringBuilder result = new StringBuilder();
            BufferedWriter bufferedWriter = null;
            BufferedReader bufferedReader = null;

            try {
                //Create data to send to server
                JSONObject dataToSend = new JSONObject();
                dataToSend.put("name", name);
                dataToSend.put("date", date);
                dataToSend.put("size", size);
                dataToSend.put("cost", cost);

                //Initialize and config request, then connect to server.
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(10000 /* milliseconds */);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);  //enable output (body data)
                urlConnection.setRequestProperty("Content-Type", "application/json");// set header
                urlConnection.connect();

                //Write data into server
                OutputStream outputStream = urlConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(dataToSend.toString());
                bufferedWriter.flush();

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
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            }

            return result.toString();
        }
    }


}