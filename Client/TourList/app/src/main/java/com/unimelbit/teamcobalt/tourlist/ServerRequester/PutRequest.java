package com.unimelbit.teamcobalt.tourlist.ServerRequester;

/**
 * PutRequest classes use the PutRequester make get requests
 */
public interface PutRequest {

    /**
     * processResult(String result) is called by the PutRequester when the request is complete
     * Implementation should take in the result of the put request as a string, process it, and
     * replace the loading screen.
     */
    void processResult(String result,int status);

    /**
     * requestFailed() is called by the PutRequester when the request fails
     * Implementation should replace the loading screen.
     */
    void requestFailed(String msg, Exception e);
}
