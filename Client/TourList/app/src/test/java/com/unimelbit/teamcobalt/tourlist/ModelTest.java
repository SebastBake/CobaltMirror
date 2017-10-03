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
import java.util.Map;


import static org.junit.Assert.assertNotNull;



/**
 * Created by spike on 9/9/2017.
 */


//Test are very basic and not robust, just wanted to have something people can work off from
// Or spark some ideas
@RunWith(RobolectricTestRunner.class)
@Config( constants = BuildConfig.class)
public class ModelTest {
   private String title = "test title";
    private String description = "test description";
    private Double  lat = 10.0;
    private Double  lo = 20.0;

    //No need to mock since its a static method
    //Most test right now only test notnull but can be easily modified to test other things
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

        ArrayList<Place> places = new ArrayList<Place>();

        assertNotNull(Location.newLocationArrayFromPlaceArray(places));
    }

    //There is a more to do to truly test parcels, but here is the basic gist of it.
    // Mock class if trying to test a static method
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

    @Test
    public void locationTestMap() throws Exception{
        //Not sure how to test toMap as its dependent on a private location stuff


        // Can test if location is a public method not private as seen here
        //Location location = new Location(title,description,lat,lo,lat);
       // Map map = location.toMap();
       // assertNotNull(map);
    }

    @Test
    public void locationTestToJSON() throws Exception{
        //Same problem as tomap

    }


}
