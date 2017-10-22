package com.unimelbit.teamcobalt.tourlist.Chat;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.powermock.reflect.Whitebox;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Map;

import static org.mockito.Matchers.isA;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Tests the FirebaseChatRoomHandler.
 * FirebaseChatRoomHandler handles most the logic of
 * chat rooms.
 */

@RunWith(RobolectricTestRunner.class)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*" , "org.powermock.*"})
@PrepareForTest(FirebaseChatRoomHandler.class)
@Config(manifest=Config.NONE)
public class FirebaseChatRoomHandlerTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    FirebaseChatRoomHandler handler;
    Context context;
    DatabaseReference root;
    DatabaseReference rootRef;

    /*
     *  Uses whitebox to create a FirebaseChatRoomHandler
     *  without using the constructor, allowing us to
     *  stub the firebase dependencies.
     */
    @Before
    public void setUp() throws Exception {

        // Sets up variables used in the test
        handler = Whitebox.newInstance(FirebaseChatRoomHandler.class);
        this.context = mock(Context.class);
        this.root = mock(DatabaseReference.class);
        this.rootRef = mock(DatabaseReference.class);
        when(rootRef.child(isA(String.class))).thenReturn(rootRef);

        // Sets the handler instance variables using whitebox
        Whitebox.setInternalState(handler, "context", context);
        Whitebox.setInternalState(handler, "root", root);
        Whitebox.setInternalState(handler, "rootRef", rootRef);

    }

    @Test
    /*
     * Checks that a chat room is generated
     */
    public void generateChatRoom() throws Exception {

        handler.generateChatRoom("chat room");

        verify(root).updateChildren(isA(Map.class));

    }

    /*
     * Checks that a user enters the given chat room
     */
    @Test
    public void enterChatRoom() throws Exception {
        // Mocks the intent to test that data is added
        Intent intent = mock(Intent.class);
        whenNew(Intent.class).withAnyArguments().thenReturn(intent);

        ArrayList users = new ArrayList<String>();
        users.add("user");

        // Trys to enter user into chat room
        handler.enterChatRoom("0", "room", "1", users, "user");

        // Verifies that the required data is added to enter the room
        verify(intent).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        verify(intent, times(4)).putExtra(isA(String.class), isA(String.class));
        verify(intent).putStringArrayListExtra(isA(String.class), isA(ArrayList.class));

        // Verifies the chat room is entered
        verify(context).startActivity(intent);

    }

    /*
     * Checks a listener is added to the room
     */
    @Test
    public void checkRoom() throws Exception {

        // Tries to add listener
        handler.checkRoom("room");

        // Verifies listener was added
        verify(rootRef).addListenerForSingleValueEvent(isA(ValueEventListener.class));

    }

    @Test
    /*
     * Checks that the chat room is deleted
     */
    public void deleteRoom() throws Exception {

        // Tries to delete chat room
        handler.deleteRoom("room");

        // Verifies that chat room was deleted
        verify(rootRef).child(isA(String.class));
        verify(rootRef).setValue("");

    }

}
