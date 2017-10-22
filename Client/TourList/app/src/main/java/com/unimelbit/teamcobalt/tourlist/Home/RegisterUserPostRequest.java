package com.unimelbit.teamcobalt.tourlist.Home;

import android.util.Log;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess.ErrorActivity;
import com.unimelbit.teamcobalt.tourlist.Model.User;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PostRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PostRequester;

import org.json.JSONObject;

/**
 * Created by Sebastian on 18/9/17.
 */

/**
 * Post requester for user to send info to the server and get registered
 */
class RegisterUserPostRequest implements PostRequest {

    //Various messages
    private static final String LOADING_MSG = "Registering User ...";
    private static final String GET_DATA_FAILED = "Failed to create post request in RegisterUserPostRequest.getDataToSend()";
    private static final String USER_ALREADY_EXISTS_ERROR_MESSAGE = "Email already registered";
    private static final String GENERAL_ERROR_MESSAGE = "Something went wrong, received result: ";
    private static final String REGISTER_SUCCESS_MESSAGE = "Successfully registered user: ";
    private static final String REGISTER_URL = "https://cobaltwebserver.herokuapp.com/api/user/create";

    private BaseActivity activity;
    private String username, password, email;

    RegisterUserPostRequest(String username, String password, String email, BaseActivity activity) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.activity = activity;

        activity.getMainContainerManager().gotoLoadingFragment(LOADING_MSG);
        new PostRequester(this).execute(REGISTER_URL);
    }

    /**
     * Processes the reuslts and will determine if the user was successful with registering
     * @param result
     * @param status
     */
    @Override
    public void processResult(String result, int status) {

        //Give an error if the user already is part of the app
        if(result.equalsIgnoreCase(REGISTER_URL)) {
            requestFailed(USER_ALREADY_EXISTS_ERROR_MESSAGE, new Exception(USER_ALREADY_EXISTS_ERROR_MESSAGE+result));
            return;
        }

        //Login the user if they have registered successfully
        try {
            User user = User.newUserFromJSON(new JSONObject(result));
            activity.getMainContainer().gotoLoginOrRegisterFragmentWithMessage(REGISTER_SUCCESS_MESSAGE + user.getUsername());

            //Get an error with network problem
        } catch (Exception e) {
            requestFailed(GENERAL_ERROR_MESSAGE + result, e);
        }
    }

    /**
     * Build the JSON to send to the server containing the sign up information
     * @return
     */
    @Override
    public String getDataToSend() {

        try {
            //Create data to send to server
            JSONObject dataToSend = new JSONObject();
            dataToSend.put(User.JSON_USERNAME, username);
            dataToSend.put(User.JSON_PASSWORD, password);
            dataToSend.put(User.JSON_EMAIL, email);
            String out = dataToSend.toString();
            return out;
        } catch (Exception e) {

            // massive error
            e.printStackTrace();
            ErrorActivity.newError(activity, e, GET_DATA_FAILED);
        }
        return null;
    }

    /**
     * If user fails to register, then display the error message
     * @param msg
     * @param e
     */
    @Override
    public void requestFailed(String msg, Exception e) {
        Log.e("RegUserPostReq failed", msg);
        e.printStackTrace();
        activity.getMainContainerManager().gotoRegisterFragmentWithMessage(msg);
    }
}