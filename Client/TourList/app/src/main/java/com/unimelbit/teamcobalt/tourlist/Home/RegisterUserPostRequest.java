package com.unimelbit.teamcobalt.tourlist.Home;

import android.util.Log;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.BaseFragmentContainerManager;
import com.unimelbit.teamcobalt.tourlist.Model.User;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PostRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PostRequester;

import org.json.JSONException;

/**
 * Created by Sebastian on 18/9/17.
 */
public class RegisterUserPostRequest implements PostRequest {

    private static final String LOADING_MSG = "Registering User ...";
    private static final String REGISTER_URL = "https://cobaltwebserver.herokuapp.com/api/user/create";

    private BaseFragmentContainerManager containerManager;
    private BaseActivity activity;
    private User user;

    RegisterUserPostRequest(BaseFragmentContainerManager manager, User regUser, BaseActivity activity) {

        this.containerManager = manager;
        this.user = regUser;
        this.activity = activity;

        // Start loading fragment
        containerManager.gotoLoadingFragment(LOADING_MSG);

        // Start get request
        new PostRequester(this).execute(REGISTER_URL);
    }

    @Override
    public void processResult(String result) {
        Toast.makeText(activity,"Result: " + result, Toast.LENGTH_SHORT).show();
        containerManager.gotoLoginFragment();
    }

    @Override
    public String getDataToSend() {

        String out = "";

        try {
            out = user.getUserRegistration();
        } catch (JSONException e) {
            requestFailed("Failed to convert user details into JSON", e);
        }
        return out;
    }

    @Override
    public void requestFailed(String msg, Exception e) {
        Log.e("RegUserPostReq failed", msg);
        e.printStackTrace();
        containerManager.gotoErrorFragment("RegisterUserPostRequest failed: " + msg + "\n Here's the exception: " + e.toString());
    }
}