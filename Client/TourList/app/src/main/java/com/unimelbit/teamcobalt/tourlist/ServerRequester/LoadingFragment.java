package com.unimelbit.teamcobalt.tourlist.ServerRequester;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unimelbit.teamcobalt.tourlist.R;

/**
 * A simple loading screen fragment
 */
public class LoadingFragment extends Fragment {

    public static final String DEFAULT_MSG = "Loading...";
    private static String loadingMsg = DEFAULT_MSG;

    /**
     * Required factory method
     */
    public static LoadingFragment newInstance() {
        LoadingFragment fragment = new LoadingFragment();
        return fragment;
    }

    /**
     * Inflates the View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_load, container, false);

        try {
            getActivity().getActionBar().hide();
        } catch (Exception e) {}


        TextView loadingTexView = (TextView) rootView.findViewById(R.id.loading_message);
        loadingTexView.setText(loadingMsg);

        return rootView;
    }

    /**
     * Shows the action bar when leaving the fragment
     */
    @Override
    public void onDestroyView() {
        try {
            getActivity().getActionBar().show();
        } catch (Exception e) {}
        super.onDestroyView();
    }

    /**
     * Shows the action bar when leaving the fragment
     */
    @Override
    public void onDetach() {
        try {
            getActivity().getActionBar().show();
        } catch (Exception e) {}
        super.onDetach();
    }

    /**
     * Simple setter for the loading message
     */
    public static void setLoadingMsg(String msg) {
        loadingMsg = msg;
    }
}
