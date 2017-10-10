package com.unimelbit.teamcobalt.tourlist.ServerRequester;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by awhite on 6/10/17.
 */
@RunWith(RobolectricTestRunner.class)
public class PutRequesterTest {
    PutRequest request;
    PutRequester requester;

    @Before
    public void setUp() throws Exception {
        this.request = mock(PutRequest.class);
        this.requester = new PutRequester(this.request);
    }


    @Test
    public void doInBackground() throws Exception {
        /*
         Hard to test because of the way the BaseActivity is used.
         */

//
//        BaseActivity baseActivity = mock(BaseActivity.class);
//        when(baseActivity.PUT_OBJECT.toString()).thenReturn("PUT");
//        MockServer server = new MockServer(5000);
//        server.start();
//        String result = this.requester.doInBackground("http://localhost:5000");
//        assertEquals("PUT\n", result);
//        server.stop();
    }

    @Test
    public void onPostExecute() throws Exception {
        // verifies that onPostExecute runs processResult
        requester.onPostExecute("result");
        verify(request).processResult("result");
    }

}