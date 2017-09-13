package com.unimelbit.teamcobalt.tourlist.ServerRequester;

/**
 * Created by Sebastian on 13/9/17.
 * GetProcessor classes are called by the GetRequestor to handle the results of a request, or handle a failed request
 */
public interface GetProcessor {
    void processResult(String result);
    void requestFailed();
}
