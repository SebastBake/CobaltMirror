package com.unimelbit.teamcobalt.tourlist.Home;

import android.content.SharedPreferences;
import android.util.Log;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.User;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequester;

import java.util.ArrayList;

/**
 * Created by ANn on 18/09/2017.
 */
class LoginUserGetRequest implements GetRequest {

    private static final String LOADING_MSG = "Logging in...";
    private static final String INCORRECT_LOGIN_MESSAGE = "Incorrect username or password!";
    private static final String NETWORK_ERROR_MESSAGE = "Network error!";
    private static final String LOGIN_SUCCESS_MESSAGE = "Logged in as: ";
    private static final String LOGIN_URL = "https://cobaltwebserver.herokuapp.com/api/user/find/";
    private final static int NULL_RESULT_LEN = 3;
    private static final String SESSION_NAME_KEY = "nameKey";
    private static final String SESSION_PASSWORD_KEY = "passwordKey";
    private static final String SESSION_USER_KEY = "aUser";

    private String url;
    private BaseActivity activity;

    LoginUserGetRequest(String username, String password, BaseActivity activity) {

        this.url = LOGIN_URL+username+"/"+password;
        this.activity = activity;

        activity.getMainContainerManager().gotoLoadingFragment(LOADING_MSG);
        new GetRequester(this).execute(url);
    }

    @Override
    public void processResult(String result) {
        try {

            if (result.length() > NULL_RESULT_LEN) {

                ArrayList<User> users = User.newUserArrayFromJSON(result);
                User user = users.get(0);
                BaseActivity.setCurrentUser(user);

                SharedPreferences.Editor editor = BaseActivity.sharedpreferences.edit();

                editor.putString(SESSION_NAME_KEY, user.getUsername());
                editor.putString(SESSION_PASSWORD_KEY, user.getPassword());
                editor.putString(SESSION_USER_KEY, result);
                editor.apply();

                activity.getMainContainer().gotoHomeFragmentWithMessage(LOGIN_SUCCESS_MESSAGE + user.getUsername());

            } else {
                activity.getMainContainer().gotoLoginFragmentWithMessage(INCORRECT_LOGIN_MESSAGE);
            }

        } catch (Exception e) {
            requestFailed("Something failed for url: " + url + " and result: " + result, e);
        }
    }

    @Override
    public void requestFailed(String msg, Exception e) {
        Log.e("Login failed", msg);
        e.printStackTrace();
        activity.getMainContainer().gotoLoginFragmentWithMessage(NETWORK_ERROR_MESSAGE);
    }
}
