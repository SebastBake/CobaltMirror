package com.unimelbit.teamcobalt.tourlist.CreateTrips;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;
import com.unimelbit.teamcobalt.tourlist.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Hong Lin on 9/09/2017.
 */

public class CustomListAdapter extends ArrayAdapter<Place> {

    private ArrayList<Place> items;

    private Context c;

    private GeoDataClient mGeoDataClient;

    private static class ViewHolder {
        private TextView itemView;
    }

    public CustomListAdapter(Context context, int textViewResourceId, ArrayList<Place> items) {
        super(context, textViewResourceId, items);

        this.items = items;

        this.c = context;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent) {

        final int position = pos;

        mGeoDataClient = Places.getGeoDataClient(c, null);

        // Get the data item for this position
        Place place = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.rowName);

        TextView desc = (TextView) convertView.findViewById(R.id.list_desc);
        // Populate the data into the template view using the data object
        name.setText(place.getName());

        desc.setText(place.getAddress());
        // Return the completed view to render on screen

        Button delButton = (Button) convertView.findViewById(R.id.delete_button);

        ImageView up = (ImageView) convertView.findViewById(R.id.image_up);

        ImageView down = (ImageView) convertView.findViewById(R.id.image_down);

        ImageView image = (ImageView) convertView.findViewById(R.id.bitmap_image);

        //getPhotos(items.get(position).getId(), image);

        setImages(image, items.get(position).getPlaceTypes().get(0));

        delButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                items.remove(position);
                notifyDataSetChanged();
            }
        });

        up.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (position > 0){
                    Place p = items.get(position);
                    items.remove(position);
                    items.add(position - 1, p);
                    notifyDataSetChanged();
                }
            }
        });

        down.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (position < items.size() - 1){
                    Place p = items.get(position);
                    items.remove(position);
                    items.add(position + 1, p);
                    notifyDataSetChanged();
                }
            }
        });


        return convertView;
    }


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

                        image.setImageBitmap(bitmap);


                    }
                });
            }
        });
    }

    public void setImages(ImageView i, int type) {

        switch (type) {
            case Place.TYPE_AIRPORT:

                i.setImageResource(R.mipmap.airport);

                break;

            case Place.TYPE_AMUSEMENT_PARK:

                i.setImageResource(R.mipmap.amusement);

                break;

            case Place.TYPE_ATM:

                i.setImageResource(R.mipmap.bank_dollar);

                break;

            case Place.TYPE_BAKERY:

                i.setImageResource(R.mipmap.restaurant);

                break;

            case Place.TYPE_BANK:

                i.setImageResource(R.mipmap.bank_dollar);

                break;

            case Place.TYPE_BAR:

                i.setImageResource(R.mipmap.bar);

                break;

            case Place.TYPE_LODGING:

                i.setImageResource(R.mipmap.lodging);

                break;

            case Place.TYPE_BOOK_STORE:

                i.setImageResource(R.mipmap.supermarket);

                break;

            case Place.TYPE_BUS_STATION:

                i.setImageResource(R.mipmap.bus);

                break;

            case Place.TYPE_CAFE:

                i.setImageResource(R.mipmap.cafe);

                break;

            case Place.TYPE_CLOTHING_STORE:

                i.setImageResource(R.mipmap.supermarket);

                break;

            case Place.TYPE_CONVENIENCE_STORE:

                i.setImageResource(R.mipmap.supermarket);

                break;

            case Place.TYPE_DENTIST:

                i.setImageResource(R.mipmap.dentist);

                break;

            case Place.TYPE_DEPARTMENT_STORE:

                i.setImageResource(R.mipmap.supermarket);

                break;

            case Place.TYPE_DOCTOR:

                i.setImageResource(R.mipmap.doctor);

                break;

            case Place.TYPE_FINANCE:

                i.setImageResource(R.mipmap.bank_dollar);

                break;

            case Place.TYPE_FOOD:

                i.setImageResource(R.mipmap.restaurant);

                break;

            case Place.TYPE_GAS_STATION:

                i.setImageResource(R.mipmap.gas_station);

                break;

            case Place.TYPE_GEOCODE:

                i.setImageResource(R.mipmap.geocode);

                break;

            case Place.TYPE_HARDWARE_STORE:

                i.setImageResource(R.mipmap.supermarket);

                break;

            case Place.TYPE_HOSPITAL:

                i.setImageResource(R.mipmap.doctor);

                break;

            case Place.TYPE_JEWELRY_STORE:

                i.setImageResource(R.mipmap.supermarket);

                break;

            case Place.TYPE_LIBRARY:

                i.setImageResource(R.mipmap.library);

                break;

            case Place.TYPE_MOVIE_THEATER:

                i.setImageResource(R.mipmap.movies);

                break;

            case Place.TYPE_NIGHT_CLUB:

                i.setImageResource(R.mipmap.bar);

                break;

            case Place.TYPE_PHARMACY:

                i.setImageResource(R.mipmap.doctor);

                break;

            case Place.TYPE_POLICE:

                i.setImageResource(R.mipmap.post_office);

                break;

            case Place.TYPE_POST_OFFICE:

                i.setImageResource(R.mipmap.post_office);

                break;

            case Place.TYPE_RESTAURANT:

                i.setImageResource(R.mipmap.restaurant);

                break;

            case Place.TYPE_SCHOOL:

                i.setImageResource(R.mipmap.school);

                break;

            case Place.TYPE_SHOPPING_MALL:

                i.setImageResource(R.mipmap.shopping);

                break;

            case Place.TYPE_STADIUM:

                i.setImageResource(R.mipmap.stadium);

                break;

            case Place.TYPE_STORE:

                i.setImageResource(R.mipmap.supermarket);

                break;

            case Place.TYPE_UNIVERSITY:

                i.setImageResource(R.mipmap.university);

                break;

            case Place.TYPE_PARK:

                i.setImageResource(R.mipmap.mountain);

                break;

            default:

                i.setImageResource(R.mipmap.geocode);

                break;
        }

    }


}