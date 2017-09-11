package com.unimelbit.teamcobalt.tourlist.Search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.unimelbit.teamcobalt.tourlist.R;

/**
 * Created by spike on 8/9/2017.
 */

public class SearchFragment extends Fragment implements View.OnClickListener{
    OnSearchListener mCallBack;

    public interface OnSearchListener {
        public void onSearch(String text);
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String title);
    }

    public SearchFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallBack = (OnSearchListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        Button b = (Button) v.findViewById(R.id.Search_button);
        b.setOnClickListener( this);
        getActivity().setTitle(R.string.title_fragment_search);
        return v;
    }

    public void  onClick(View v){
        EditText searchtext = (EditText) getView().findViewById(R.id.Search_input);
        String text = searchtext.getText().toString();
        switch(v.getId()){
            case R.id.Search_button:
                mCallBack.onSearch(text);
               // SearchResultFragment Frag= new SearchResultFragment();
                //getActivity().getFragmentManager().beginTransaction()
                      //  .replace(R.id.fragment_container, Frag)
                      //  .addToBackStack(null)
                      //  .commit();

        }
    }


}

