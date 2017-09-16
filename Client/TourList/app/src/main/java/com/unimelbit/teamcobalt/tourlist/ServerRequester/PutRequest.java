package com.unimelbit.teamcobalt.tourlist.ServerRequester;

/**
 * Created by spike on 16/9/2017.
 */

public interface PutRequest {

    void processResult(String result);

    /**
     * requestFailed() is called by the GetRequester when the request fails
     * Implementation should replace the loading screen.
     */
    void requestFailed(String msg, Exception e);
}
