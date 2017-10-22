package com.unimelbit.teamcobalt.tourlist.ServerRequester;

/**
 * PostRequest classes use the PostRequester make get requests
 */
public interface PostRequest {

    /**
     * processResult(String result) is called by the PostRequester when the request is complete
     * Implementation should take in the result of the post request as a string, process it, and
     * replace the loading screen.
     */
    void processResult(String result,int status);

    /**
     * getDataToSend() should return the data which is to be sent to the server
     */
    String getDataToSend();

    /**
     * requestFailed() is called by the PostRequester when the request fails
     * Implementation should replace the loading screen.
     */
    void requestFailed(String msg, Exception e);
}