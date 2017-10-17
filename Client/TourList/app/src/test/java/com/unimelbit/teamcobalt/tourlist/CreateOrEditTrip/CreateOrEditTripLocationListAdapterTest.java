package com.unimelbit.teamcobalt.tourlist.CreateOrEditTrip;

import android.content.Context;
import android.widget.ImageView;

import com.google.android.gms.location.places.Place;
import com.unimelbit.teamcobalt.tourlist.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.withSettings;

/**
 * Created by awhite on 16/10/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class CreateOrEditTripLocationListAdapterTest {

    CreateOrEditTripLocationListAdapter adapter;

    @Before
    public void setUp() throws Exception {
        Context context = mock(Context.class, withSettings().verboseLogging());
        int textViewResourceId = 0;
        ArrayList<Place> items = new ArrayList<>();

        Place item = mock(Place.class);
        items.add(item);

        this.adapter = new CreateOrEditTripLocationListAdapter(context, textViewResourceId, items);
    }

    @Test
    public void getView() throws Exception {
        /*
         *   Mostly UI code, check AndroidTest for test
         */
    }

    @Test
    public void setImages() throws Exception {
        ImageView i = mock(ImageView.class);
        int type = Place.TYPE_AIRPORT;

        adapter.setImages(i, type);
        verify(i).setImageResource(R.mipmap.airport);
    }

}