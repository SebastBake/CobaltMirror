package com.unimelbit.teamcobalt.tourlist.Chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by awhite on 16/10/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class ChatListRoomFragmentTest {

    /**
     * Check androidTest folder for tests of ChatListRoomFragment.
     * The class has little application logic to test,
     * it creates an Activity and View for the list of chatrooms.
     */


    @Test
    public void newInstance() throws Exception {
        ChatListRoomFragment clrf = new ChatListRoomFragment();
        Fragment fragment = clrf.newInstance("one", "two");
        assertNotNull(fragment);

        Bundle args = fragment.getArguments();
        assertTrue(args.isEmpty());
    }


}