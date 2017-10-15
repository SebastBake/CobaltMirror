package com.unimelbit.teamcobalt.tourlist.GPSLocation;

import android.content.Context;
import android.location.LocationManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static android.content.Context.LOCATION_SERVICE;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by awhite on 15/10/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class GoogleGpsProviderTest {

    Context context;
    GoogleGpsProvider gps;

    @Before
    public void setUp() throws Exception {

        this.context = mock(Context.class);
        when(context.getApplicationContext()).thenReturn(context);
        this.gps = new GoogleGpsProvider(context) {
            @Override
            public void callback() {

            }
        };

    }

    @Test
    public void isGPSEnable() throws Exception {

        LocationManager service = mock(LocationManager.class);
        when(context.getSystemService(LOCATION_SERVICE)).thenReturn(service);
        when(service.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(true);

        assertTrue(gps.isGPSEnable(context));

        when(service.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false);

        assertFalse(gps.isGPSEnable(context));

    }

    @Test
    public void createGetLocationRequest() throws Exception {

        assertNull(gps.getLocationRequest());

        gps.createLocationRequest();

        assertNotNull(gps.getLocationRequest());

    }

    @Test
    public void getLocationClient() throws Exception {
        assertNotNull(gps.getLocationClient());
    }

    @Test
    public void isSetmRequestingLocationUpdates() throws Exception {
        assertFalse(gps.isRequestingLocation());

        gps.setmRequestingLocationUpdates(true);
        assertTrue(gps.isRequestingLocation());

        gps.setmRequestingLocationUpdates(true);
        assertTrue(gps.isRequestingLocation());

        gps.setmRequestingLocationUpdates(false);
        assertFalse(gps.isRequestingLocation());

        gps.setmRequestingLocationUpdates(false);
        assertFalse(gps.isRequestingLocation());


    }

    @Test
    public void getmLocationCallback() throws Exception {
        gps = mock(GoogleGpsProvider.class);
        gps.getmLocationCallback();
        verify(gps).getmLocationCallback();
    }

    @Test
    public void startLocationUpdates() throws Exception {
        gps = mock(GoogleGpsProvider.class);
        gps.startLocationUpdates();
        verify(gps).startLocationUpdates();
    }

    @Test
    public void stopLocationUpdates() throws Exception {
        gps = mock(GoogleGpsProvider.class);
        gps.stopLocationUpdates();
        verify(gps).stopLocationUpdates();
    }

}