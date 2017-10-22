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
 * Created by awhite on 16/10/17.
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

    @Before
    public void setUp() throws Exception {

        handler = Whitebox.newInstance(FirebaseChatRoomHandler.class);
        this.context = mock(Context.class);
        this.root = mock(DatabaseReference.class);
        this.rootRef = mock(DatabaseReference.class);
        when(rootRef.child(isA(String.class))).thenReturn(rootRef);

        Whitebox.setInternalState(handler, "context", context);
        Whitebox.setInternalState(handler, "root", root);
        Whitebox.setInternalState(handler, "rootRef", rootRef);

    }

    @Test
    public void generateChatRoom() throws Exception {

        handler.generateChatRoom("chat room");

        verify(root).updateChildren(isA(Map.class));

    }

    @Test
    public void enterChatRoom() throws Exception {
        Intent intent = mock(Intent.class);
        whenNew(Intent.class).withAnyArguments().thenReturn(intent);

        ArrayList users = new ArrayList<String>();
        users.add("user");

        handler.enterChatRoom("0", "room", "1", users, "user");

        verify(intent).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        verify(intent, times(4)).putExtra(isA(String.class), isA(String.class));

        verify(intent).putStringArrayListExtra(isA(String.class), isA(ArrayList.class));

        verify(context).startActivity(intent);

    }

    @Test
    public void checkRoom() throws Exception {

        handler.checkRoom("room");

        verify(rootRef).addListenerForSingleValueEvent(isA(ValueEventListener.class));
    }

    @Test
    public void deleteRoom() throws Exception {

        handler.deleteRoom("room");

        verify(rootRef).child(isA(String.class));
        verify(rootRef).setValue("");
    }

}