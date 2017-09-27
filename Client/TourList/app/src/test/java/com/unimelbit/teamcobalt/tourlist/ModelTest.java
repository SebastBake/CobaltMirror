package com.unimelbit.teamcobalt.tourlist;



import android.os.Parcel;


import com.google.android.gms.location.places.Place;
import com.unimelbit.teamcobalt.tourlist.Model.Location;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


import java.util.ArrayList;


import static org.junit.Assert.assertNotNull;



/**
 * Created by spike on 9/9/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class)
public class ModelTest {
   private String title = "test title";
    private String description = "test description";
    private String lat = "10.0";
    private String lo = "20.0";

    @Test
    public void locationTestJSON() throws Exception{
        JSONObject object = new JSONObject();
        object.put("title", title);
        object.put("description",description);
        object.put("latitude",lat);
        object.put("longtitude",lo);
        JSONArray array = new JSONArray();
        array.put(object);

        assertNotNull(Location.newLocationArrayFromJSON(array));

    }

    @Test
    public void locationTestPlaceArray() throws Exception{
        Location location = Mockito.mock(Location.class);
        ArrayList<Place> places = new ArrayList<Place>();

        assertNotNull(Location.newLocationArrayFromPlaceArray(places));
    }

    @Test
    public void locationTestParcel() throws Exception{
        Location location = Mockito.mock(Location.class);
        //Save bundle to parcel
        Parcel parcel = Parcel.obtain();
        location.writeToParcel(parcel, 0);

        // After you're done with writing, you need to reset the parcel for reading:
        parcel.setDataPosition(0);

        assertNotNull(parcel);
    }
}
