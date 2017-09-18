package com.unimelbit.teamcobalt.tourlist.ServerRequester;

import android.os.AsyncTask;

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
 * Created by Sebastian on 13/9/17.
 * Initiates a post request, calls the PostRequest to get the contents of the post and process the
 * results of the request or handle errors.
 */
public class PostRequester extends AsyncTask<String, Void, String> {

    private PostRequest processor;

    public PostRequester(PostRequest processor) {
        this.processor = processor;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            return postData(params[0]);
        } catch (IOException ex) {
            String msg = "Network error, received an IO exception !";
            processor.requestFailed(msg, ex);
            return msg;
        }
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

    private String postData(String urlPath) throws IOException {

        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try {
            //Initialize and config request, then connect to server
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(6 * 1000 /* milliseconds */);
            urlConnection.setConnectTimeout(6 * 1000 /* milliseconds */);
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
