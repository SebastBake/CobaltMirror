package com.unimelbit.teamcobalt.tourlist;

import android.content.Context;

import com.unimelbit.teamcobalt.tourlist.CreateOrEditTrip.NewTripSingleton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Tests for the AppServicesFactory class.
 * AppServicesFactory is used as a Singleton
 * factory which creates relevant handlers
 * and classes to be used in the app.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class AppServicesFactoryTest {

    AppServicesFactory factory;
    Context context;

    /*
     * Sets up the factory before every test
     */
    @Before
    public void setUp() {
        this.context = mock(Context.class);
        this.factory = AppServicesFactory.getServicesFactory();
    }

    /*
     * Checks that the services factory singleton is returned
     */
    @Test
    public void getServicesFactory() throws Exception {
        // Checks that it always returns the same instance

        assertNotNull(factory);

        AppServicesFactory second = AppServicesFactory.getServicesFactory();
        assertEquals(factory, second);

    }

    /*
     * Checks that the firebase chat service singleton is returned
     */
    @Test
    public void getFirebaseChatService() throws Exception {
        // Checks that it always returns the same instance

//        Need to find out what method to mock for initializeApp to work
//
//        FirebaseApp.initializeApp(context);
//
//        ChatAdaptor first = factory.getFirebaseChatService(context);
//        assertNotNull(first);
//
//        ChatAdaptor second = factory.getFirebaseChatService(context);
//        assertEquals(first, second);

    }

    /*
     * Checks that the Firebase Post Requester singleton is returned
     */
    @Test
    public void getFirebasePostRequester() throws Exception {
        // Checks that it always returns the same instance

//        Need to find out what method to mock for initializeApp to work
//
//        FirebaseApp.initializeApp(context);
//
//        CoordinateDBPostRequester first = factory.getFirebasePostRequester(context);
//        assertNotNull(first);
//
//        CoordinateDBPostRequester second = factory.getFirebasePostRequester(context);
//        assertEquals(first, second);

    }

    /*
     * Checks that the new trip singleton is returned
     */
    @Test
    public void getNewTrip() throws Exception {

        NewTripSingleton first = factory.getNewTrip();
        assertNotNull(first);

        NewTripSingleton second = factory.getNewTrip();
        assertEquals(first, second);

    }

}
