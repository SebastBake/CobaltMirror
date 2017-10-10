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
import com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess.ErrorActivity;
import com.unimelbit.teamcobalt.tourlist.R;

import java.io.IOException;

public class LoginFragment extends Fragment implements View.OnClickListener {

    public static final String FILL_FORM_MESSAGE = "Enter username and password";

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Login");

        // init submit button listener
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        Button apply = (Button) rootView.findViewById(R.id.button_login);
        apply.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_login) {
            try {
                attemptLogin();
            } catch (IOException e) {
                e.printStackTrace();
                ErrorActivity.newError(getActivity(), e);
            }
        }
    }

    public void attemptLogin() throws IOException {

        EditText usernameText = (EditText) getView().findViewById(R.id.login_username_field);
        EditText passwordText =  (EditText) getView().findViewById(R.id.login_password_field);
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), FILL_FORM_MESSAGE, Toast.LENGTH_SHORT).show();

        } else {
            new LoginUserGetRequest(username, password, (BaseActivity) getActivity());
        }
    }
}
