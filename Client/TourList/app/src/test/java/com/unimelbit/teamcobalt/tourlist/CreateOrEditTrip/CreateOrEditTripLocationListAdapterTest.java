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
 * Tests the CreateOrEditTripLocationListAdapter
 *
 * Contains some logic and some UI elements so need to
 * check the androidTest folder for some of the test
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class CreateOrEditTripLocationListAdapterTest {

    CreateOrEditTripLocationListAdapter adapter;

    // Creates the adapter before each test with mocked data
    @Before
    public void setUp() throws Exception {

        // Creates fake data and mocked objects for the constructor
        Context context = mock(Context.class, withSettings().verboseLogging());
        int textViewResourceId = 0;
        ArrayList<Place> items = new ArrayList<>();

        Place item = mock(Place.class);
        items.add(item);

        // Constructs the adapter
        this.adapter = new CreateOrEditTripLocationListAdapter(context, textViewResourceId, items);
    }

    @Test
    public void getView() throws Exception {
        /*
         *   Mostly UI code, check AndroidTest for test
         */
    }

    /*
     * Tests the Image setter method
     */
    @Test
    public void setImages() throws Exception {

        // Sets up dumby data and mock view
        ImageView i = mock(ImageView.class);
        int type = Place.TYPE_AIRPORT;

        // Tries to set the image
        adapter.setImages(i, type);

        // Verifies that the image was set
        verify(i).setImageResource(R.mipmap.airport);
    }

}
