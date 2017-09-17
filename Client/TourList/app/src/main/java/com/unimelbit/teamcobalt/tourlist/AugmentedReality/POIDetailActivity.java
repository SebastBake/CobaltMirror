package com.unimelbit.teamcobalt.tourlist.AugmentedReality;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;
import com.unimelbit.teamcobalt.tourlist.R;

import java.io.InputStream;


public class POIDetailActivity extends AppCompatActivity {

    public static final String EXTRAS_KEY_POI_ID = "id";
    public static final String EXTRAS_KEY_POI_TITILE = "title";
    public static final String EXTRAS_KEY_POI_DESCR = "description";
    public static final String EXTRAS_KEY_POI_LAT = "latitude";
    public static final String EXTRAS_KEY_POI_LONG = "longitude";

    private String latitude;
    private String longitude;

    private ImageView mapImage;
    private ImageView locImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_poidetail);

        ((TextView) findViewById(R.id.poi_id)).setText(getIntent().getExtras().getString(EXTRAS_KEY_POI_ID));
        ((TextView) findViewById(R.id.poi_title)).setText(getIntent().getExtras().getString(EXTRAS_KEY_POI_TITILE));
        ((TextView) findViewById(R.id.poi_description)).setText(getIntent().getExtras().getString(EXTRAS_KEY_POI_DESCR));

        latitude = getIntent().getExtras().getString(EXTRAS_KEY_POI_LAT);
        latitude = getIntent().getExtras().getString(EXTRAS_KEY_POI_LONG);

        mapImage = (ImageView) findViewById(R.id.MapImage);
        locImage = (ImageView) findViewById(R.id.locImage);

        setMapImage();



    }


    public void setMapImage() {

        String mapURL = "https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + "," +
                longitude + "&zoom=12&size=400x400&key="+"AIzaSyCzMPO3wufV3Ld4qVPquFVJbMcKBL-N80c";


        Picasso.with(this).load(mapURL).into(mapImage);
    }

    




}


