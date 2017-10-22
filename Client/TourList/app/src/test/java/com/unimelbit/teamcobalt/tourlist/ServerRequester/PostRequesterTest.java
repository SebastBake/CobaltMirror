package com.unimelbit.teamcobalt.tourlist.ServerRequester;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class PostRequesterTest {
    PostRequest request;
    PostRequester requester;

    @Before
    public void setUp() throws Exception {
        this.request = mock(PostRequest.class);
        when(request.getDataToSend()).thenReturn("data");
        this.requester = new PostRequester(this.request);
    }


    @Test
    public void doInBackground() throws Exception {
        // runs a local server and sends a post request
        MockServer server = new MockServer(5000);
        server.start();

        // verifies that the post request was successful
        String result = this.requester.doInBackground("http://localhost:5000");
        assertEquals("POST\n", result);
        server.stop();
    }

    @Test
    public void onPostExecute() throws Exception {
        // verifies that onPostExecute runs processResult
        requester.onPostExecute("result");
        verify(request).processResult("result", 0);
    }

}
