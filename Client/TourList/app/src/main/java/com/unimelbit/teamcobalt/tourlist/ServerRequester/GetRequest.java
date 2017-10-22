package com.unimelbit.teamcobalt.tourlist.ServerRequester;

/**
 * GetRequest classes use the GetRequester make get requests
 */
public interface GetRequest {

    /**
     * processResult(String result) is called by the GetRequester when the request is complete
     * Implementation should take in the result of the get request as a string, process it, and
     * replace the loading screen.
     */
    void processResult(String result);

    /**
     * requestFailed() is called by the GetRequester when the request fails
     * Implementation should replace the loading screen.
     */
    void requestFailed(String msg, Exception e);
}
