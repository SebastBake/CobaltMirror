package com.unimelbit.teamcobalt.tourlist.AugmentedReality;

import android.content.Context;

import com.wikitude.architect.ArchitectJavaScriptInterfaceListener;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Created by awhite on 5/10/17.
 */
public class ARJSONListenerTest {

    ARJSONListener listener;

    @Before
    public void setUp() {
        this.listener = new ARJSONListener();
    }

    @Test
    public void getArchitectJavaScriptInterfaceListener() throws Exception {
        Context context = mock(Context.class);
        ArchitectJavaScriptInterfaceListener temp = listener.getArchitectJavaScriptInterfaceListener(context);
        assertNotNull(temp);

    }

    @Test
    public void createListener() throws Exception {

    }

    @Test
    public void setListener() throws Exception {

    }

}