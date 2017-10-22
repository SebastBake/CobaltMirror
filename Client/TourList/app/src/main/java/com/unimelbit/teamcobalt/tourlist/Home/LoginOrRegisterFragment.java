package com.unimelbit.teamcobalt.tourlist.Home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.BaseFragmentContainerManager;
import com.unimelbit.teamcobalt.tourlist.Model.User;
import com.unimelbit.teamcobalt.tourlist.R;

/**
 * Very first page of the app users will see if they have not logged in
 * They can choose to login or register here
 */
public class LoginOrRegisterFragment extends Fragment {

    public LoginOrRegisterFragment() {
    }

    public static LoginOrRegisterFragment newInstance() {
        LoginOrRegisterFragment fragment = new LoginOrRegisterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login_or_register, container, false);

        getActivity().setTitle("Login Or Register");

        initLoginMessageText(rootView);
        initLoginAndRegisterButtons(rootView);

        //Lock app drawer if user is not logged in
        DrawerLayout drawer = (DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        return rootView;
    }

    /**
     * Show a message depending on whether user is logged in or not when starting the app
     * @param rootView
     */
    private void initLoginMessageText(View rootView) {

        TextView messageBox = (TextView) rootView.findViewById(R.id.login_or_register_text);
        User user = ((BaseActivity)getActivity()).getCurrentUser();

        if(user == null) {
            messageBox.setText("You aren't logged in.");
        } else {
            messageBox.setText("You're logged in as: " + user.getUsername());
        }
    }

    /**
     * Set up the buttons for login and register
     * @param rootView
     */
    private void initLoginAndRegisterButtons(View rootView) {
        final Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);

        /*
        The buttons below will have various animations
         */
        Button loginButton = (Button) rootView.findViewById(R.id.go_to_login_fragment);
        loginButton.setAnimation(shake);

        Button registerButton = (Button) rootView.findViewById(R.id.go_to_register_fragment);
        registerButton.setAnimation(shake);

        BaseFragmentContainerManager manager = ((BaseActivity)getActivity()).getMainContainerManager();

        /*
        Have listeners for the buttons
         */
        loginButton.setOnClickListener(new loginButtonListener(manager){
            public void onClick(View v){
                v.startAnimation(shake);
                manager.gotoLoginFragment();
            }
        });
        registerButton.setOnClickListener(new registerButtonListener(manager){
            public void onClick(View v){
                v.startAnimation(shake);
                manager.gotoRegisterFragment();
            }
        });
    }

    /**
     * Inner classes for listening to button clicks so the base fragment doesnt need to implement
     * the on click listener interface
     */
    class loginButtonListener implements Button.OnClickListener {

        BaseFragmentContainerManager manager;

        //Take to the login fragment
        loginButtonListener(BaseFragmentContainerManager baseFragmentContainerManager) {
            manager = baseFragmentContainerManager;
        }

        @Override
        public void onClick(View v) {
            manager.gotoLoginFragment();
        }
    }

    class registerButtonListener implements Button.OnClickListener {

        BaseFragmentContainerManager manager;

        //Take to the register fragment
        registerButtonListener(BaseFragmentContainerManager baseFragmentContainerManager) {
            manager = baseFragmentContainerManager;
        }

        @Override
        public void onClick(View v) {
            manager.gotoRegisterFragment();
        }
    }
}
