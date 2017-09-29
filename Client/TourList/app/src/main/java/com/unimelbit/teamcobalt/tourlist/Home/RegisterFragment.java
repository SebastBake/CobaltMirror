package com.unimelbit.teamcobalt.tourlist.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.BaseFragmentContainerManager;
import com.unimelbit.teamcobalt.tourlist.Model.User;
import com.unimelbit.teamcobalt.tourlist.R;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private Button apply;

    public RegisterFragment() {
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        getActivity().setTitle("Register");

        initRegisterButton(rootView);

        return rootView;
    }

    private void initRegisterButton(View rootView) {
        apply = (Button) rootView.findViewById(R.id.button_register);
        apply.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_register) {
            Create_User();
        }
    }

    /**
     * do a post request to add a user to the database
     */
    public void Create_User() {

        EditText usernameText = (EditText) getView().findViewById(R.id.register_username_field);
        EditText passwordText =  (EditText) getView().findViewById(R.id.register_password_field);
        EditText emailText =  (EditText) getView().findViewById(R.id.register_email_field);

        String id = "temp id";
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String email = emailText.getText().toString();

        BaseFragmentContainerManager manager = ((BaseActivity)getActivity()).getMainContainerManager();

        User newUser = new User(id,username, password, email, null, null);

        new RegisterUserPostRequest(manager, newUser, (BaseActivity) getActivity());
    }
}
