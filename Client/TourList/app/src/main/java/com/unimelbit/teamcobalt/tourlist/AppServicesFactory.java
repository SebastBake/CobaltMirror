package com.unimelbit.teamcobalt.tourlist;

import android.content.Context;

import com.unimelbit.teamcobalt.tourlist.Chat.ChatAdaptor;
import com.unimelbit.teamcobalt.tourlist.Chat.FirebaseChatRoomHandler;
import com.unimelbit.teamcobalt.tourlist.CreateOrEditTrip.NewTripSingleton;
import com.unimelbit.teamcobalt.tourlist.GPSLocation.ARGoogleGpsProvider;
import com.unimelbit.teamcobalt.tourlist.GPSLocation.FirebaseGoogleGpsProvider;
import com.unimelbit.teamcobalt.tourlist.GPSLocation.GoogleGpsProvider;
import com.unimelbit.teamcobalt.tourlist.Tracking.CoordinateDBPostRequester;
import com.unimelbit.teamcobalt.tourlist.Tracking.FireBaseRequester;

/**
 * Created by Hong Lin on 26/09/2017.
 */

/**
 * Singleton factory which creates relevant handlers and classes to be used in the app
 */
public class AppServicesFactory {

    private static AppServicesFactory servicesInstance;

    private ChatAdaptor firebaseChatService;

    private FireBaseRequester firebasePoster;

    private NewTripSingleton newTripSingletonInstance;

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


    public CoordinateDBPostRequester getFirebasePostRequester(Context c){

        if(firebasePoster == null){

            firebasePoster = new FireBaseRequester(c);
        }

        return firebasePoster;

    }

    /**
     * Creates a Gps provider class. Note that this will create a new instance every time it is
     * not shared
     * @param c
     * @return
     */
    public GoogleGpsProvider getFirebaseGpsProvider(Context c){

        return new FirebaseGoogleGpsProvider(c);

    }

    /**
     * Same as the Gps provider class, but for AR
     * @param c
     * @return
     */
    public GoogleGpsProvider getARGpsProvider(Context c){

       return new ARGoogleGpsProvider(c);

    }

    /**
     * Returns the NewTripSingleton service
     */
    public NewTripSingleton getNewTrip() {
        if(newTripSingletonInstance == null) {
            newTripSingletonInstance = new NewTripSingleton();
        }
        return newTripSingletonInstance;
    }
}
