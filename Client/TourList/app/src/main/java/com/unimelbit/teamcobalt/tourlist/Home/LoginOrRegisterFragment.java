package com.unimelbit.teamcobalt.tourlist.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class LoginOrRegisterFragment extends Fragment {

    public LoginOrRegisterFragment() {
    }

    public static LoginOrRegisterFragment newInstance() {
        LoginOrRegisterFragment fragment = new LoginOrRegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login_or_register, container, false);

        getActivity().setTitle("Login Or Register");

        initLoginMessageText(rootView);
        initLoginAndRegisterButtons(rootView);


        return rootView;
    }

    private void initLoginMessageText(View rootView) {

        TextView messageBox = (TextView) rootView.findViewById(R.id.login_or_register_text);
        User user = ((BaseActivity)getActivity()).getCurrentUser();

        if(user == null) {
            messageBox.setText("You aren't logged in.");
        } else {
            messageBox.setText("You're logged in as: " + user.getUsername());
        }
    }

    private void initLoginAndRegisterButtons(View rootView) {
        final Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);

        Button loginButton = (Button) rootView.findViewById(R.id.go_to_login_fragment);
        loginButton.setAnimation(shake);
        Button registerButton = (Button) rootView.findViewById(R.id.go_to_register_fragment);
        registerButton.setAnimation(shake);

        BaseFragmentContainerManager manager = ((BaseActivity)getActivity()).getMainContainerManager();

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

    class loginButtonListener implements Button.OnClickListener {

        BaseFragmentContainerManager manager;

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

        registerButtonListener(BaseFragmentContainerManager baseFragmentContainerManager) {
            manager = baseFragmentContainerManager;
        }

        @Override
        public void onClick(View v) {
            manager.gotoRegisterFragment();
        }
    }
}
