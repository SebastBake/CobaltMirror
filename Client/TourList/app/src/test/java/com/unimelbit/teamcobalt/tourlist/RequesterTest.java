package com.unimelbit.teamcobalt.tourlist;


import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequester;
import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchGetRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static junit.framework.Assert.assertEquals;

/**
 * Created by spike on 27/9/2017.
 */


@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class)
public class RequesterTest  {



    // Not sure if its possible to test the requesters
    @Test
    public void getTest() throws Exception{
        String url = "https://cobaltwebserver.herokuapp.com/api/trips/findrandom";
        GetRequest getter = Mockito.mock(GetRequest.class);
        GetRequester get = new GetRequester(getter);
        get.execute(url);

    }

    //We can test Server connection with this template
    @Test
    public void testURL() throws Exception {
        String strUrl = "https://cobaltwebserver.herokuapp.com/api/trips/findrandom";

        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.connect();

            assertEquals(HttpURLConnection.HTTP_OK, urlConn.getResponseCode());
        } catch (IOException e) {
            System.err.println("Error creating HTTP connection");
            e.printStackTrace();
            throw e;
        }
    }


}
