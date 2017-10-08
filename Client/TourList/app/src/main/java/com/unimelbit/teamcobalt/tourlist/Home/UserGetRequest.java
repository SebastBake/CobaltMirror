package com.unimelbit.teamcobalt.tourlist.Home;

import android.util.Log;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.BaseFragmentContainerManager;
import com.unimelbit.teamcobalt.tourlist.Error.ErrorActivity;
import com.unimelbit.teamcobalt.tourlist.Model.User;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequester;

import java.util.ArrayList;

/**
 * Created by ANn on 18/09/2017.
 */

public class UserGetRequest implements GetRequest {
    private static String LOADING_MSG = "Loading user...";

    private String url;
    private BaseFragmentContainerManager containerManager;

    public UserGetRequest(String url, BaseFragmentContainerManager containerManager) {

        this.url = url;
        this.containerManager = containerManager;

        // Start loading fragment
        containerManager.gotoLoadingFragment(LOADING_MSG);

        // Start get request
        new GetRequester(this).execute(url);
    }

    @Override
    public void processResult(String result) {
        try {
            ArrayList<User> trip = User.newUserArrayFromJSON(result);
            containerManager.gotoProfileFragment();
        } catch (Exception e) {
            requestFailed("Something failed for url: " + url + " and result: " + result, e);
        }

    }

    @Override
    public void requestFailed(String msg, Exception e) {
        Log.e("UserGetRequest failed",msg);
        e.printStackTrace();
        ErrorActivity.newError(containerManager.getBaseActivity(),e,"TripGetRequest failed: " + msg);

    }
}
