package com.unimelbit.teamcobalt.tourlist.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;

public class LoginFragment extends Fragment implements View.OnClickListener{

    private Button loginButton;

    private EditText userName;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle("Login");

        loginButton = (Button) rootView.findViewById(R.id.button_login);

        loginButton.setOnClickListener(this);

        userName = (EditText) rootView.findViewById(R.id.login_username_field);

        return rootView;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        //Take user to main menu
        if(id == R.id.button_login){

            String enteredUser = userName.getText().toString();

            if(enteredUser.isEmpty()){

                enteredUser = "Demo User";
            }

            String message = "Logged in as: "+ enteredUser;

            BaseActivity base = (BaseActivity) getActivity();

            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

            base.getMainContainer().gotoHomeFragment();

        }


    }

}
