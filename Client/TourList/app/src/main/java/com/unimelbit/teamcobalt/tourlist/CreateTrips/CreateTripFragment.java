package com.unimelbit.teamcobalt.tourlist.CreateTrips;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.unimelbit.teamcobalt.tourlist.BackButtonInterface;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateTripFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateTripFragment extends Fragment implements View.OnClickListener, BackButtonInterface {
    // TODO: Rename parameter arguments, choose names that match

    public static final String EXTRA_MESSAGE = "com.example.spike.uitest.MESSAGE";
    public static final String EXTRA_MESSAGE_TWO = "com.example.spike.uitest.MESSAGE_TWO";
    public static final String EXTRA_MESSAGE_FOUR = "com.example.spike.uitest.MESSAGE_THREE"; ;
    public static final String EXTRA_MESSAGE_THREE = "com.example.spike.uitest.MESSAGE_FOUR"; ;
    String size;
    String cost;
    String name;
    String date;
    private String postresults;


    private RadioButton size_small;
    private RadioButton size_medium;
    private RadioButton size_large;
    private RadioButton cost_small;
    private RadioButton cost_medium;
    private RadioButton cost_large;
    private Button apply;


    private OnFragmentInteractionListener mListener;

    public CreateTripFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateTripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateTripFragment newInstance(String param1, String param2) {
        CreateTripFragment fragment = new CreateTripFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_trip, container, false);

        size_small = (RadioButton) v.findViewById(R.id.Size_small);
        size_small.setOnClickListener(this);
        size_medium = (RadioButton) v.findViewById(R.id.Size_medium);
        size_medium.setOnClickListener(this);
        size_large = (RadioButton) v.findViewById(R.id.Size_large);
        size_large.setOnClickListener(this);

        cost_small = (RadioButton) v.findViewById(R.id.Cost_small);
        cost_small.setOnClickListener(this);
        cost_medium = (RadioButton) v.findViewById(R.id.Cost_medium);
        cost_medium.setOnClickListener(this);
        cost_large = (RadioButton) v.findViewById(R.id.Cost_large);
        cost_large.setOnClickListener(this);

        apply = (Button) v.findViewById(R.id.buttonApply);
        apply.setOnClickListener(this);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {

        // Is the button now checked?
        boolean checked;


        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.Size_small:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    size = "1-5";
                size_medium.setChecked(false);
                size_large.setChecked(false);
                break;
            case R.id.Size_medium:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    size = "5-10";
                size_small.setChecked(false);
                size_large.setChecked(false);
                break;
            case R.id.Size_large:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    size = ">10";
                size_medium.setChecked(false);
                size_small.setChecked(false);
                break;
            case R.id.Cost_small:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    cost = "$";
                cost_medium.setChecked(false);
                cost_large.setChecked(false);
                break;
            case R.id.Cost_medium:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    cost = "$$";
                cost_small.setChecked(false);
                cost_large.setChecked(false);
                break;
            case R.id.Cost_large:
                checked = ((RadioButton) view).isChecked();
                if (checked)
                    cost = "$$$";
                cost_medium.setChecked(false);
                cost_small.setChecked(false);
                break;
            case R.id.buttonApply:

                Create_Trip(view);
                break;
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void Create_Trip(View view) {
        Intent intent = new Intent(getActivity(),TripsActivity.class);
        EditText NameText = (EditText) getView().findViewById(R.id.Name_field);
        EditText DateText =  (EditText) getView().findViewById(R.id.Date_field);
        name = NameText.getText().toString();
        date = DateText.getText().toString();
        new CreateTripFragment.PostDataTask().execute("https://cobaltwebserver.herokuapp.com/api/trips/create");
        intent.putExtra(EXTRA_MESSAGE, name);
        intent.putExtra(EXTRA_MESSAGE_TWO, date);
        intent.putExtra(EXTRA_MESSAGE_THREE, size);
        intent.putExtra(EXTRA_MESSAGE_FOUR, cost);
        startActivity(intent);
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public void Search_Trip(View view){

    }
    public void AR(View view){

    }


    // i should really change this to radio group but im lazy
    public void onRadioButtonClicked(View view) {

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
