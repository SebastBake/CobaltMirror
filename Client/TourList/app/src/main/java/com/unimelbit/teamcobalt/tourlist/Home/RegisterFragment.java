package com.unimelbit.teamcobalt.tourlist.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;

public class RegisterFragment extends Fragment implements View.OnClickListener{

    public static final String FILL_FORM_MESSAGE = "Enter details";

    private EditText usernameText;
    private EditText passwordText;
    private EditText emailText;

    public RegisterFragment() {
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register, container, false);
        getActivity().setTitle("Register");

        // initialise UI components
        usernameText = (EditText) v.findViewById(R.id.register_username_field);
        passwordText =  (EditText) v.findViewById(R.id.register_password_field);
        emailText =  (EditText) v.findViewById(R.id.register_email_field);
        Button apply = (Button) v.findViewById(R.id.button_register);
        apply.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_register) {

            String username = usernameText.getText().toString();
            String password = passwordText.getText().toString();
            String email = emailText.getText().toString();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                Toast.makeText(getActivity(), FILL_FORM_MESSAGE , Toast.LENGTH_SHORT).show();

            } else {
                new RegisterUserPostRequest(username, password, email, (BaseActivity) getActivity());
            }
        }
    }
}
