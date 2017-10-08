package com.unimelbit.teamcobalt.tourlist.Home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.LogPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;

import org.json.JSONException;
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

public class RegisterFragment extends Fragment implements View.OnClickListener{

    public static final String FILL_FORM_MESSAGE = "Enter details";
    public static final String REGISTER_LOADING_MESSAGE = "Registering new user...";

    private String username;
    private String password;
    private String email;

    private String postresults;
    private Button apply;

    public RegisterFragment() {
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register, container, false);
        getActivity().setTitle("Register");

        apply = (Button) v.findViewById(R.id.button_register);
        apply.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_register) {

            EditText usernameText = (EditText) getView().findViewById(R.id.register_username_field);
            EditText passwordText =  (EditText) getView().findViewById(R.id.register_password_field);
            EditText emailText =  (EditText) getView().findViewById(R.id.register_email_field);

            username = usernameText.getText().toString();
            password = passwordText.getText().toString();
            email = emailText.getText().toString();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                Toast.makeText(getActivity(), FILL_FORM_MESSAGE , Toast.LENGTH_SHORT).show();

            } else {

                ((BaseActivity)getActivity()).getMainContainerManager().gotoLoadingFragment(REGISTER_LOADING_MESSAGE);
                new RegisterFragment.PostDataTask().execute("https://cobaltwebserver.herokuapp.com/api/user/create");
            }
        }
    }

    //http post and its functions
    class PostDataTask extends AsyncTask<String, Void, String> {

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
            postresults = result;

            new LogPrinter(0,null).println(postresults);

            Toast.makeText(getActivity(),postresults, Toast.LENGTH_SHORT).show();
            ((BaseActivity)getActivity()).getMainContainerManager().gotoLoginFragment();
        }

        private String postData(String urlPath) throws IOException, JSONException {

            StringBuilder result = new StringBuilder();
            BufferedWriter bufferedWriter = null;
            BufferedReader bufferedReader = null;

            try {
                //Create data to send to server
                JSONObject dataToSend = new JSONObject();
                dataToSend.put("username", username);
                dataToSend.put("password", password);
                dataToSend.put("email", email);

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
