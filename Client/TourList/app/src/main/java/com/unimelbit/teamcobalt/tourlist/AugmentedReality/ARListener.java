package com.unimelbit.teamcobalt.tourlist.AugmentedReality;

import android.content.Context;
import android.view.View;
import com.wikitude.architect.ArchitectJavaScriptInterfaceListener;

/**
 * Created by Hong Lin on 1/09/2017.
 */

public interface ARListener {

    //Create and return a listner for the JS interface
    ArchitectJavaScriptInterfaceListener getArchitectJavaScriptInterfaceListener(Context con);

    //Create the listener
    void createListener(Context c);

    //Set the listener on a particular view
    void setListener(View view);

}
