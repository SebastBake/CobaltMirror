package com.unimelbit.teamcobalt.tourlist.ServerRequester;

import android.os.AsyncTask;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchResultFragment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by spike on 16/9/2017.
 */

public class PutRequester extends AsyncTask<String, Void, String> {

    private PutRequest processor;
    private int status;

    public PutRequester(PutRequest processor) {
        this.processor = processor;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            return putData(params[0]);
        } catch (IOException ex) {
            String errorMsg = "Network error !";
            processor.requestFailed(errorMsg, ex);
            return errorMsg;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        processor.processResult(result,status);
    }

    private String putData(String urlPath) throws IOException {

        StringBuilder result = new StringBuilder();
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;

        try {

            //Initialize and config request, then connect to server
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10*1000 /* milliseconds */);
            urlConnection.setConnectTimeout(10*1000 /* milliseconds */);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Content-Type", "application/json");// set header
            urlConnection.connect();

            //Write data into server
            OutputStream outputStream = urlConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(BaseActivity.PUT_OBJECT.toString());
            bufferedWriter.flush();

            //Read data response from server
            InputStream inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            status = urlConnection.getResponseCode();
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
