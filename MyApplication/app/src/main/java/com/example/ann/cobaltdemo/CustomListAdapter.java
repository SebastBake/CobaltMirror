package com.example.ann.cobaltdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ANn on 22/08/2017.
 */

public class CustomListAdapter extends ArrayAdapter {
    //to reference the Activity
    private final MainActivity context;

    //to store the animal images
    private final Integer[] imageIDarray;

    //to store the list of countries
    private final String[] nameArray;

    //to store the list of countries
    private final String[] infoArray;


    public CustomListAdapter(MainActivity context, String[] nameArrayParam, String[] infoArrayParam, Integer[] imageIDArrayParam){

        super(context,R.layout.placeslistview_row , nameArrayParam);

        this.context=context;
        this.imageIDarray = imageIDArrayParam;
        this.nameArray = nameArrayParam;
        this.infoArray = infoArrayParam;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.placeslistview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.placeNameID);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.placeDesID);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        infoTextField.setText(infoArray[position]);
        imageView.setImageResource(imageIDarray[position]);

        return rowView;

    };


}
