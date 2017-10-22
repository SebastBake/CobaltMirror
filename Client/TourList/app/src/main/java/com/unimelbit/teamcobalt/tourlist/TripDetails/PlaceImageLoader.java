package com.unimelbit.teamcobalt.tourlist.TripDetails;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

/**
 * Created by Hong Lin on 17/09/2017.
 */

/**
 * Loads a static map image into the image view
 */
public class PlaceImageLoader {

    private GeoDataClient mGeoDataClient;

    private Context c;

    /** Initialise this class with the context which calls it
     */
    public PlaceImageLoader(Context c){

        this.c = c;

        mGeoDataClient = Places.getGeoDataClient(c, null);

    }


    /**
     * Add a snapshot of the map to the image view from the given coordinates
     * @param latitude
     * @param longitude
     * @param image
     */
    public void setMapImage(String latitude, String longitude, ImageView image) {

        //Url to get the image
        String mapURL = "https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + "," +
                longitude + "&zoom=18&size=300x300&key="+"AIzaSyCzMPO3wufV3Ld4qVPquFVJbMcKBL-N80c";

        //Load the image into the imageview through Picasso
        if (image != null && latitude != null && longitude != null) {

            Picasso.with(c).load(mapURL).into(image);
        }
    }


}
