package com.unimelbit.teamcobalt.tourlist.ServerRequester;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sebastian on 13/9/17.
 * Initiates a get request, calls the GetProcesor to process the results of the request or handle errors.
*/
public class GetRequester extends AsyncTask<String, Void, String> {

    private GetRequest processor;

    public GetRequester(GetRequest processor) {
        this.processor = processor;
    }

    @Override
    protected String doInBackground(String... params) {
        return getData(params[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            processor.processResult(result);
        } catch (Exception e) {
            processor.requestFailed(result, e);
        }
    }

    private String getData(String urlPath) {

        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            //Initialize and config request, then connect to server
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(5 * 1000 /* milliseconds */);
            urlConnection.setConnectTimeout(5 * 1000 /* milliseconds */);
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

            bufferedReader.close();

        } catch (Exception e) {
            return e.getMessage();
        }

        return result.toString();
    }
}
