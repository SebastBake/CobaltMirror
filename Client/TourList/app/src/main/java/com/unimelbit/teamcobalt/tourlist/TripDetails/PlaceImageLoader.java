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
 * Generates
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
     * Takes in a Place id from the Place object, and the image view that needs to be changed.
     * This function will try grab a photo of the first place with the id and try load on any images
     * into the view if it can
     */
    private void getPhotos(String id, ImageView i) {

        final String placeId = id;

        final ImageView image = i;

        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);

        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(Task<PlacePhotoMetadataResponse> task) {

                // Get the list of photos.
                PlacePhotoMetadataResponse photos = task.getResult();

                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();

                // Get the first photo in the list.
                PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);

                // Get the attribution text.
                CharSequence attribution = photoMetadata.getAttributions();

                // Get a full-size bitmap for the photo.
                Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);

                photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                    @Override
                    public void onComplete( Task<PlacePhotoResponse> task) {
                        PlacePhotoResponse photo = task.getResult();
                        Bitmap bitmap = photo.getBitmap();

                        if(bitmap != null) {
                            image.setImageBitmap(bitmap);
                        }
                    }
                });
            }
        });

    }


    /*
Adds a snapshot of a map to the image view
 */
    public void setMapImage(String latitude, String longitude, ImageView image) {

        String mapURL = "https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + "," +
                longitude + "&zoom=18&size=300x300&key="+"AIzaSyCzMPO3wufV3Ld4qVPquFVJbMcKBL-N80c";

        if (image != null && latitude != null && longitude != null) {

            Picasso.with(c).load(mapURL).into(image);

        }else{

            System.out.println("IMAGE IS NULL");
        }
    }


}
