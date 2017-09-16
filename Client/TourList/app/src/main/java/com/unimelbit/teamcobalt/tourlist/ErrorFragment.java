package com.unimelbit.teamcobalt.tourlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ErrorFragment extends Fragment implements BackButtonInterface {

    private static final String ARG_MSG = "ARG_MSG";

    private String msg;

    public ErrorFragment() {
    }

    public static ErrorFragment newInstance(String msg) {

        ErrorFragment fragment = new ErrorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MSG, msg);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            msg = getArguments().getString(ARG_MSG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_error, container, false);

        TextView errorText = (TextView) rootView.findViewById(R.id.error_text);
        errorText.setText(msg);

        return rootView;
    }
}
