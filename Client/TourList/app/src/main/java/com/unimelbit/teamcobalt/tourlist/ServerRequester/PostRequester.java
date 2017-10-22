package com.unimelbit.teamcobalt.tourlist.ServerRequester;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Initiates a post request, calls the PostRequest to get the contents of the post and process the
 * results of the request or handle errors.
 */
public class PostRequester extends AsyncTask<String, Void, String> {

    private PostRequest processor;
    private int status;

    /**
     * Constructor
     */
    public PostRequester(PostRequest processor) {
        this.processor = processor;
    }

    /**
     * Calls the request method
     */
    @Override
    protected String doInBackground(String... params) {
        return postData(params[0]);
    }

    /**
     * Handles the response from the server
     */
    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);

        try {
            processor.processResult(result, status);
        } catch (Exception e) {
            processor.requestFailed(result, e);
        }
    }

    /**
     * Makes the http request to the server
     */
    private String postData(String urlPath) {

        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try {
            //Initialize and config request, then connect to server
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10 * 1000 /* milliseconds */);
            urlConnection.setConnectTimeout(10 * 1000 /* milliseconds */);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);  //enable output (body data)
            urlConnection.setRequestProperty("Content-Type", "application/json");// set header
            urlConnection.connect();

            //Write data into server
            OutputStream outputStream = urlConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(processor.getDataToSend());
            bufferedWriter.flush();

            //Read data response from server
            InputStream inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            status = urlConnection.getResponseCode();
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line).append("\n");
            }

            bufferedReader.close();

        } catch (Exception e) {
            return e.getMessage();
        }

        return (result.toString());
    }
}