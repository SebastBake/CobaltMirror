package com.unimelbit.teamcobalt.tourlist;

import android.content.Context;

import com.unimelbit.teamcobalt.tourlist.Chat.ChatAdaptor;
import com.unimelbit.teamcobalt.tourlist.Chat.FirebaseChatRoomHandler;

/**
 * Created by Hong Lin on 26/09/2017.
 */

public class AppServicesFactory {

    private static AppServicesFactory servicesInstance;

    private ChatAdaptor firebaseChatService;

    public static AppServicesFactory getServicesFactory(){

        if(servicesInstance == null){

            servicesInstance = new AppServicesFactory();

        }

        return servicesInstance;

    }


    public ChatAdaptor getFirebaseChatService(Context c){

        if(firebaseChatService == null){

            firebaseChatService = new FirebaseChatRoomHandler(c);
        }

        return firebaseChatService;

    }

}
