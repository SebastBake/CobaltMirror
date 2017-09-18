package com.unimelbit.teamcobalt.tourlist.AugmentedReality;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;
import com.unimelbit.teamcobalt.tourlist.R;

import java.io.InputStream;


public class POIDetailActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRAS_KEY_POI_TITILE = "title";
    public static final String EXTRAS_KEY_POI_DESCR = "description";
    public static final String EXTRAS_KEY_POI_LAT = "latitude";
    public static final String EXTRAS_KEY_POI_LONG = "longitude";

    private String latitude;
    private String longitude;

    private String title;

    private ImageView mapImage;

    private Button webSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_poidetail);
        title = getIntent().getExtras().getString(EXTRAS_KEY_POI_TITILE);
        ((TextView) findViewById(R.id.poi_title)).setText(title);
        ((TextView) findViewById(R.id.poi_description)).setText(getIntent().getExtras().getString(EXTRAS_KEY_POI_DESCR));

        latitude = getIntent().getExtras().getString(EXTRAS_KEY_POI_LAT);
        longitude = getIntent().getExtras().getString(EXTRAS_KEY_POI_LONG);

        mapImage = (ImageView) findViewById(R.id.MapImage);

        webSearchButton = (Button) findViewById(R.id.web_search_b);

        webSearchButton.setOnClickListener(this);

        setMapImage();

    }


    public void setMapImage() {

        String mapURL = "https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + "," +
                longitude + "&zoom=17&size=450x400&key="+"AIzaSyCzMPO3wufV3Ld4qVPquFVJbMcKBL-N80c";


        Picasso.with(this).load(mapURL).into(mapImage);
    }


    public void onClick(View v)
    {
        if (v == webSearchButton)
        {
            Uri uri = Uri.parse("https://www.google.com/search?q="+ title);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        }

        return;

    }



}


