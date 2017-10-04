package com.unimelbit.teamcobalt.tourlist;

import android.content.Context;

import com.unimelbit.teamcobalt.tourlist.Chat.ChatAdaptor;
import com.unimelbit.teamcobalt.tourlist.Tracking.CoordinateDBPostRequester;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Created by awhite on 4/10/17.
 */
public class AppServicesFactoryTest {

    @Test
    public void getServicesFactory() throws Exception {

        AppServicesFactory first = AppServicesFactory.getServicesFactory();
        assertNotNull(first);

        AppServicesFactory second = AppServicesFactory.getServicesFactory();
        assertEquals(first, second);

    }

    @Test
    public void getFirebaseChatService() throws Exception {
        Context context = mock(Context.class);
        AppServicesFactory factory = AppServicesFactory.getServicesFactory();

        ChatAdaptor first = factory.getFirebaseChatService(context);
        assertNotNull(first);

        ChatAdaptor second = factory.getFirebaseChatService(context);
        assertEquals(first, second);

    }

    @Test
    public void getFirebasePostRequester() throws Exception {
        Context context = mock(Context.class);
        AppServicesFactory factory = AppServicesFactory.getServicesFactory();

        CoordinateDBPostRequester first = factory.getFirebasePostRequester(context);
        assertNotNull(first);

        CoordinateDBPostRequester second = factory.getFirebasePostRequester(context);
        assertEquals(first, second);

    }

}