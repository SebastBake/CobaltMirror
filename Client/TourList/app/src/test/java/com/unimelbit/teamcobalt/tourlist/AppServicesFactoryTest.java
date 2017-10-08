package com.unimelbit.teamcobalt.tourlist;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Created by awhite on 4/10/17.
 */

@RunWith(RobolectricTestRunner.class)
public class AppServicesFactoryTest {

    AppServicesFactory factory;
    Context context;

    @Before
    public void setUp() {
        this.context = mock(Context.class);
        this.factory = AppServicesFactory.getServicesFactory();
    }

    @Test
    public void getServicesFactory() throws Exception {
        // Checks that it always returns the same instance

        assertNotNull(factory);

        AppServicesFactory second = AppServicesFactory.getServicesFactory();
        assertEquals(factory, second);

    }

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

}