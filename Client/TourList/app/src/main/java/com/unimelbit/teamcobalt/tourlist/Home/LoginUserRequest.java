package com.unimelbit.teamcobalt.tourlist.Home;

import com.unimelbit.teamcobalt.tourlist.BaseFragmentContainerManager;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequest;

/**
 * Created by Sebast on 18/9/17.
 */
public class LoginUserRequest implements GetRequest {

    private static String LOADING_MSG = "Logging in...";
    private static String LOGIN_URL = "https://cobaltwebserver.herokuapp.com/api/user/create";

    LoginUserRequest(BaseFragmentContainerManager manager) {

    }

    @Override
    public void processResult(String result) {

    }

    @Override
    public void requestFailed(String msg, Exception e) {

    }
}
