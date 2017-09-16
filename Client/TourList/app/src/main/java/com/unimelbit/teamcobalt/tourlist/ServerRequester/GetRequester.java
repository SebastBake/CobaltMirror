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
        try {
            return getData(params[0]);
        } catch (IOException ex) {
            String errorMsg = "Network error !";
            processor.requestFailed(errorMsg, ex);
            return errorMsg;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        processor.processResult(result);
    }

    private String getData(String urlPath) throws IOException {

        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            //Initialize and config request, then connect to server
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(2*1000 /* milliseconds */);
            urlConnection.setConnectTimeout(2*1000 /* milliseconds */);
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
