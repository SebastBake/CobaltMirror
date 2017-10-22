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
 * Tests for the GoogleGpsProvider class.
 * GoogleGpsProvider is used for location tracking in the app.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class GoogleGpsProviderTest {

    Context context;
    GoogleGpsProvider gps;

    /*
     *  Sets up the GoogleGpsProvider before each test
     */
    @Before
    public void setUp() throws Exception {

        // Mocks context for creation of GoogleGpsProvider
        this.context = mock(Context.class);
        when(context.getApplicationContext()).thenReturn(context);

        // Constructs the GoogleGpsProvider
        this.gps = new GoogleGpsProvider(context) {
            @Override
            public void callback() {

            }
        };

    }

    /*
     *  Checks that the right value is returned depending if
     *  the gps is enabled or disabled
     */
    @Test
    public void isGPSEnable() throws Exception {

        // Mocks the locations services
        LocationManager service = mock(LocationManager.class);
        when(context.getSystemService(LOCATION_SERVICE)).thenReturn(service);
        when(service.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(true);

        // Tests returns true when enabled
        assertTrue(gps.isGPSEnable(context));

        // Tests returns false when disabled
        when(service.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false);
        assertFalse(gps.isGPSEnable(context));

    }

    /*
     *  Checks that a request has been created
     */
    @Test
    public void createGetLocationRequest() throws Exception {

        // Checks there isn't a request
        assertNull(gps.getLocationRequest());

        // Creates the request
        gps.createLocationRequest();

        // Checks there is a request now
        assertNotNull(gps.getLocationRequest());

    }

    /*
     * Checks that the client is returned
     */
    @Test
    public void getLocationClient() throws Exception {

        assertNotNull(gps.getLocationClient());

    }

    /*
     * Checks setter and is for mRequestingLocationUpdates
     */
    @Test
    public void isSetmRequestingLocationUpdates() throws Exception {

        // checks isn't requesting
        assertFalse(gps.isRequestingLocation());

        // sets is requesting and check value updated
        gps.setmRequestingLocationUpdates(true);
        assertTrue(gps.isRequestingLocation());

        // sets is requesting and check value still true
        gps.setmRequestingLocationUpdates(true);
        assertTrue(gps.isRequestingLocation());

        // sets isn't requesting and check value updated
        gps.setmRequestingLocationUpdates(false);
        assertFalse(gps.isRequestingLocation());

        // sets isn't requesting and check value still false
        gps.setmRequestingLocationUpdates(false);
        assertFalse(gps.isRequestingLocation());

    }

    /*
     *  Verifies that getmLocationCallback runs when called
     */
    @Test
    public void getmLocationCallback() throws Exception {

        gps = mock(GoogleGpsProvider.class);
        gps.getmLocationCallback();
        verify(gps).getmLocationCallback();

    }

    /*
     *  Verifies that startLocationUpdates runs when called
     */
    @Test
    public void startLocationUpdates() throws Exception {

        gps = mock(GoogleGpsProvider.class);
        gps.startLocationUpdates();
        verify(gps).startLocationUpdates();

    }

    /*
     *  Verifies that stopLocationUpdates runs when called
     */
    @Test
    public void stopLocationUpdates() throws Exception {

        gps = mock(GoogleGpsProvider.class);
        gps.stopLocationUpdates();
        verify(gps).stopLocationUpdates();

    }

}
