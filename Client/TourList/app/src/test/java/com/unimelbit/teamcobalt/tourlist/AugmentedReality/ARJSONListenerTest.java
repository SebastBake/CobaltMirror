package com.unimelbit.teamcobalt.tourlist.AugmentedReality;

import android.content.Context;

import com.wikitude.architect.ArchitectJavaScriptInterfaceListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Created by awhite on 5/10/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class ARJSONListenerTest {

    ARJSONListener listener;
    Context context;

    @Before
    public void setUp() {
        this.listener = new ARJSONListener();
        this.context = mock(Context.class);
    }

    @Test
    public void getArchitectJavaScriptInterfaceListener() throws Exception {
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