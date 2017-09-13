package com.unimelbit.teamcobalt.tourlist.ServerRequester;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.R;

/**
 * Created by Sebast on 13/9/17.
 */

public class LoadingFragment extends Fragment {

    public static LoadingFragment newInstance() {
        LoadingFragment fragment = new LoadingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_load, container, false);
        return rootView;
    }
}
