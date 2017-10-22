package com.unimelbit.teamcobalt.tourlist.TripDetails;

import android.util.Log;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;

import com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess.ErrorActivity;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PutRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PutRequester;

import org.json.JSONException;
import org.json.JSONObject;

import static java.security.AccessController.getContext;

/**
 * Created by spike on 16/9/2017.
 */


/**
 * Class for removing a trip from the users list
 */
public class RemoveTripRequest implements PutRequest {
    private static String LOADING_MSG = "Loading trips...";
    private static String URL_REMOVE_BASE = "https://cobaltwebserver.herokuapp.com/api/user/removetrip/";
    private String tripid;
    private String url;
    private static final int HTTP_SUCCESS_CODE = 200;

    BaseActivity activity;

    RemoveTripRequest(String tripid, String username, String userid, BaseActivity activity) throws JSONException {

        this.tripid = tripid;
        this.activity = activity;
        this.url = URL_REMOVE_BASE;
        JSONObject object = new JSONObject();
        object.put("tripid", tripid);
        object.put("userid",userid);
        object.put("username",username);
        BaseActivity.setPutObject(object);

        // Start loading fragment
        activity.getMainContainerManager().gotoLoadingFragment(LOADING_MSG);

        // Start get request
        new PutRequester(this).execute(url);
    }

    /**
     * Like its parent class, it will display an error if something goes wrong
     * @param result
     * @param status
     */
    @Override
    public void processResult(String result,int status) {
        try {
            if (status != HTTP_SUCCESS_CODE) {
                throw new Exception();
            }
            new TripGetRequestByID(tripid, activity.getMainContainerManager());
        } catch (Exception e) {
            requestFailed("Something failed for url: " + URL_REMOVE_BASE + " and result: " + result, e);
        }
    }

    @Override
    public void requestFailed(String msg, Exception e) {
        Log.e("RemoveTripPut failed", msg);
        e.printStackTrace();
        ErrorActivity.newError(activity,e, "Removing trip failed: " + msg + "\n Here's the exception: " + e.toString());
    }
}


