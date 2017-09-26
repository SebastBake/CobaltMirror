package com.unimelbit.teamcobalt.tourlist;

import android.content.Context;

import com.unimelbit.teamcobalt.tourlist.Chat.ChatAdaptor;
import com.unimelbit.teamcobalt.tourlist.Chat.FirebaseChatRoomHandler;

/**
 * Created by Hong Lin on 26/09/2017.
 */

/**
 * Singleton factory which creates relevant handlers and classes to be used in the app
 */
public class AppServicesFactory {

    private static AppServicesFactory servicesInstance;

    private ChatAdaptor firebaseChatService;

    /**
     * Return itself as it is a singleton class
     * @return
     */
    public static AppServicesFactory getServicesFactory(){

        if(servicesInstance == null){

            servicesInstance = new AppServicesFactory();

        }

        return servicesInstance;

    }


    /**
     * Returns the Firebase chat handler
     * @param c
     * @return
     */
    public ChatAdaptor getFirebaseChatService(Context c){

        if(firebaseChatService == null){

            firebaseChatService = new FirebaseChatRoomHandler(c);
        }

        return firebaseChatService;

    }

}
