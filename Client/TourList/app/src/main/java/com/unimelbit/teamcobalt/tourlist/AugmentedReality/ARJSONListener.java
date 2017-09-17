package com.unimelbit.teamcobalt.tourlist.AugmentedReality;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.wikitude.architect.ArchitectJavaScriptInterfaceListener;
import com.wikitude.architect.ArchitectView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hong Lin on 3/09/2017.
 */

public class ARJSONListener implements ARListener{

    private ArchitectJavaScriptInterfaceListener mArchitectJavaScriptInterfaceListener;

    /*
 Listener to recieve JSON objects from the JS Architect View.
 Starts the activity with appropriate information from teh JSON object
  */
    public ArchitectJavaScriptInterfaceListener getArchitectJavaScriptInterfaceListener(Context con) {

        final Context c = con;

        return new ArchitectJavaScriptInterfaceListener() {
            @Override
            public void onJSONObjectReceived(JSONObject jsonObject) {
                try {
                    switch (jsonObject.getString("action")) {

                        //JSON from pressing 'More' on the AR View
                        case "present_poi_details":
                            //Send details and intiate the details activity
                            final Intent poiDetailIntent = new Intent(c, POIDetailActivity.class);
                            poiDetailIntent.putExtra(POIDetailActivity.EXTRAS_KEY_POI_ID, jsonObject.getString("id"));
                            poiDetailIntent.putExtra(POIDetailActivity.EXTRAS_KEY_POI_TITILE, jsonObject.getString("title"));
                            poiDetailIntent.putExtra(POIDetailActivity.EXTRAS_KEY_POI_DESCR, jsonObject.getString("description"));
                            poiDetailIntent.putExtra(POIDetailActivity.EXTRAS_KEY_POI_LAT, jsonObject.getString("latitude"));
                            poiDetailIntent.putExtra(POIDetailActivity.EXTRAS_KEY_POI_LONG, jsonObject.getString("longitude"));
                            //POI detail activity
                           c.startActivity(poiDetailIntent);
                            break;

                    }
                }catch (JSONException e) {
                    // Log.e(TAG, "onJSONObjectReceived: ", e);
                }


            }
        };
    }


    /*
    Create and return listener
     */
    public void createListener(Context c){

        this.mArchitectJavaScriptInterfaceListener = this.getArchitectJavaScriptInterfaceListener(c);

    }


    /*
    Set listener
     */
    public void setListener(View view){

        ArchitectView arcView = (ArchitectView)view;

        if (this.mArchitectJavaScriptInterfaceListener != null && arcView != null) {
            arcView.addArchitectJavaScriptInterfaceListener(mArchitectJavaScriptInterfaceListener);
        }
    }

}
