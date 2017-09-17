package com.unimelbit.teamcobalt.tourlist.TripSearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.unimelbit.teamcobalt.tourlist.BackButtonInterface;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;

/**
 * Created by spike on 8/9/2017.
 * Edited by sebastian on 14/9/2017.
 */

public class TripSearchFragment extends Fragment implements View.OnClickListener, BackButtonInterface {


    public TripSearchFragment() {
    }

    public static TripSearchFragment newInstance() {
        TripSearchFragment fragment = new TripSearchFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        Button b = (Button) v.findViewById(R.id.Search_button);
        b.setOnClickListener(this);
        getActivity().setTitle(R.string.title_fragment_search);
        return v;
    }

    @Override
    public void  onClick(View v){

        EditText searchText = (EditText) getView().findViewById(R.id.Search_input);
        String text = searchText.getText().toString();
        new TripSearchGetRequest(text, ((BaseActivity)getActivity()).getMainContainerManager());
    }
}

