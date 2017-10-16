package com.unimelbit.teamcobalt.tourlist.AugmentedReality;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by awhite on 5/10/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class ARToolsTest {
//    ARTools tools;
    Context context;

    @Before
    public void setUp() throws Exception {
//        this.context = mock(Context.class);
//        when(context.getApplicationContext()).thenReturn(context);
//        this.tools = new ARTools(context);
    }

    @Test
    public void isGPSEnable() throws Exception {

//        LocationManager service = mock(LocationManager.class);
//        when(context.getSystemService(LOCATION_SERVICE)).thenReturn(service);
//        when(service.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(true);
//
//        assertTrue(tools.isGPSEnable(context));
//
//        when(service.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false);
//
//        assertFalse(tools.isGPSEnable(context));
    }

    @Test
    public void createLocationRequest() throws Exception {
//        tools.createLocationRequest();
//        assertNotNull(tools.getLocationRequest());
    }

    @Test
    public void getLocationRequest() throws Exception {
//        assertNull(tools.getLocationRequest());
    }

    @Test
    public void getLocationClient() throws Exception {
//        FusedLocationProviderClient client = tools.getLocationClient();
//        assertNotNull(client);
    }

    @Test
    public void isRequestingLocation() throws Exception {
//        assertFalse(tools.isRequestingLocation());
    }

    @Test
    public void setmRequestingLocationUpdates() throws Exception {
//        tools.setmRequestingLocationUpdates(true);
//        assertTrue(tools.isRequestingLocation());
    }

}