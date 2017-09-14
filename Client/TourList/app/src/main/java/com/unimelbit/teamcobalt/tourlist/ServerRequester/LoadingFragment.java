package com.unimelbit.teamcobalt.tourlist.ServerRequester;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unimelbit.teamcobalt.tourlist.R;

/**
 * Created by Sebastian on 13/9/17.
 * A simple loading screen fragment
 */
public class LoadingFragment extends Fragment {

    public static final String ARG_LOADING_MSG = "LoadingMsgArg";
    public static final String DEFAULT_MSG = "";
    private String loadingMsg;

    public static LoadingFragment newInstance(String loadingMsg) {

        LoadingFragment fragment = new LoadingFragment();

        Bundle args = new Bundle();
        if (loadingMsg != null) {
            args.putString(ARG_LOADING_MSG, loadingMsg);
        } else {
            args.putString(ARG_LOADING_MSG, DEFAULT_MSG);
        }
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_load, container, false);

        TextView loadingMsg = (TextView) rootView.findViewById(R.id.loading_message);
        loadingMsg.setText((String)this.getArguments().get(ARG_LOADING_MSG));

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loadingMsg = (String)getArguments().get(ARG_LOADING_MSG);
        }
    }
}
