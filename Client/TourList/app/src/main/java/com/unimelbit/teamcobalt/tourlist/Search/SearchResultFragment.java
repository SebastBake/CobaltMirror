package com.unimelbit.teamcobalt.tourlist.Search;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.unimelbit.teamcobalt.tourlist.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by spike on 8/9/2017.
 */

public class SearchResultFragment extends Fragment {
   public static String ARG_TEXT;

    private ListView lv;


    private ProgressBar pb;

    // id + value kinda like json
    ArrayList<HashMap<String, String>> searchList;
    public SearchResultFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SearchResultFragment newInstance() {
        SearchResultFragment fragment = new SearchResultFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        TextView textview = (TextView) view.findViewById(R.id.Result_text);
        final Bundle args = getArguments();
        textview.setText(R.string.fragment_searchresults_header+ args.getString(ARG_TEXT));
        getActivity().setTitle(R.string.title_fragment_searchresults);

        pb = (ProgressBar) view.findViewById(R.id.progressBar);

        searchList = new ArrayList<>();
        // Right now search just displays all trips to test
        new GetDataTask().execute("https://cobaltwebserver.herokuapp.com/api/locations/search?searchcontent=" + args.getString(ARG_TEXT));



        return view;
    }

    // Get class, everything is here
    public class GetDataTask extends AsyncTask<String, Void, String> {


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
            lv = (ListView) getView().findViewById(R.id.results_list);
            int count =0;


            try {
                JSONArray jArray = new JSONArray(result);
                for (int i=0; i < jArray.length(); i++)
                {
                    count ++;
                    try {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        // Pulling items from the array
                        String name = oneObject.getString("name");
                        String cost = oneObject.getString("cost");
                        String size = oneObject.getString("size");
                        JSONArray locations =oneObject.getJSONArray("locations");
                        String locations_titles = "";
                        for (int j=0; j< locations.length();j++){
                            JSONObject location = locations.getJSONObject(j);
                            String title = location.getString("title");
                            locations_titles = locations_titles + title + "\n";
                        }
                        HashMap<String, String> trip = new HashMap<>();

                        trip.put("name",name);
                        trip.put("cost",cost);
                        trip.put("size",size);
                        trip.put("locations",locations_titles);



                        searchList.add(trip);



                    } catch (JSONException e) {
                        // Oops
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ListAdapter adapter = new SimpleAdapter(
                    getContext(), searchList,
                    R.layout.fragment_search_results_items, new String[]{"name", "size",
                    "cost","locations"}, new int[]{R.id.name,
                    R.id.size, R.id.cost,R.id.locations});

            lv.setAdapter(adapter);


        }

        public  String getData(String urlPath) throws IOException {
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
