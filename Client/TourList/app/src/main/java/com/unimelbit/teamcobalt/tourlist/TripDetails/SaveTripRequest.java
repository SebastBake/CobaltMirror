package com.unimelbit.teamcobalt.tourlist.TripDetails;

import android.util.Log;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess.ErrorActivity;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PutRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PutRequester;
;import org.json.JSONException;
import org.json.JSONObject;

/**
 * Put request for a user to shortlist a trip
 */
public class SaveTripRequest implements PutRequest {

    private static String LOADING_MSG = "Loading trips...";
    private static String URL_SAVE_BASE = "https://cobaltwebserver.herokuapp.com/api/user/addtrip/";
    private String tripid;
    private String url;
    private static final int HTTP_SUCCESS_CODE = 200;

    BaseActivity activity;


    /**
     *  Initiates a save trip request
     */
    SaveTripRequest(String tripid, String username, String userid,BaseActivity activity) throws JSONException {

        this.tripid = tripid;
        this.activity = activity;
        this.url = URL_SAVE_BASE;
        JSONObject object = new JSONObject();
        object.put("tripid", tripid);
        object.put("userid",userid);
        object.put("username",username);
        BaseActivity.setPutObject(object);
        // Start get request
        new PutRequester(this).execute(url);
    }

    /**
     * Check that the request was successful, handle the response
     */
    @Override
    public void processResult(String result,int status) {
        try {
            if (status != HTTP_SUCCESS_CODE) {
                throw new Exception();
            }
            new TripGetRequestByID(tripid, activity.getMainContainerManager());
        } catch (Exception e) {
            requestFailed("Something failed for url: " + URL_SAVE_BASE + " and result: " + result, e);
        }
    }

    /**
     * handle a failed request
     */
    @Override
    public void requestFailed(String msg, Exception e) {
        Log.e("RemoveTripPut failed", msg);
        e.printStackTrace();
        ErrorActivity.newError( activity ,e, "Removing trip failed: " + msg + "\n Here's the exception: " + e.toString());
    }
}
