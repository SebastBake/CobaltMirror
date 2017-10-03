package com.unimelbit.teamcobalt.tourlist.Home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.LogPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;

import com.unimelbit.teamcobalt.tourlist.BaseFragmentContainerManager;
import com.unimelbit.teamcobalt.tourlist.Model.User;

import com.unimelbit.teamcobalt.tourlist.R;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequester;


import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LoginFragment extends Fragment implements View.OnClickListener{
    private String username;
    private String password;
    private String getresults;

    private Button apply;

    private final static int NULL_RESULT_LEN = 3;


    BaseFragmentContainerManager mainContainer;

    public LoginFragment() {
    }
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_login) {
            try {
                Verify_User();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void Verify_User () throws IOException {
        EditText usernameText = (EditText) getView().findViewById(R.id.login_username_field);
        EditText passwordText =  (EditText) getView().findViewById(R.id.login_password_field);
        this.username = usernameText.getText().toString();
        this.password = passwordText.getText().toString();

        new LoginFragment.GetDataTask().execute("https://cobaltwebserver.herokuapp.com/api/user/find/"+username+"/"+password);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle("Login");
        apply = (Button) rootView.findViewById(R.id.button_login);
        apply.setOnClickListener(this);
        return rootView;
    }

    class GetDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //add alert here
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                return getData(params[0]);
            } catch (IOException ex) {
                String errorMsg = "Network error !";
                requestFailed(errorMsg, ex);
                return errorMsg;
            }
        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            getresults = result;
            BaseActivity base = (BaseActivity) getActivity();

            new LogPrinter(0, null).println(getresults);
            try {


                if (getresults.length() > NULL_RESULT_LEN) {
                    //Toast.makeText(getActivity(), tmp.toString(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(), getresults, Toast.LENGTH_SHORT).show();
                    User user = new User(username,password, null,null,null, null);
                    Toast.makeText(getActivity(), user.getUsername(), Toast.LENGTH_SHORT).show();
                    base.setCurrentUser(user);
                    base.setUserName(username);
                    base.getMainContainer().gotoHomeFragment();

                } else {
                    Toast.makeText(getActivity(), "NO USER FOUND", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                requestFailed("Something failed for url and result: " + result, e);
            }

        }

        public void requestFailed(String msg, Exception e) {
            Log.e("UserGetRequest failed",msg);
            e.printStackTrace();
            ((BaseActivity) getActivity()).getMainContainerManager().gotoErrorFragment("UserGetRequest failed: " + msg);

        }

        private String getData(String urlPath) throws IOException {

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

            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }

            return result.toString();
        }
    }

}
