package com.unimelbit.teamcobalt.tourlist.CreateTrips;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.unimelbit.teamcobalt.tourlist.R;

import java.util.ArrayList;

/**
 * Created by Hong Lin on 9/09/2017.
 */

public class CustomListAdapter extends ArrayAdapter<Place> {

    private ArrayList<Place> items;

    private static class ViewHolder {
        private TextView itemView;
    }

    public CustomListAdapter(Context context, int textViewResourceId, ArrayList<Place> items) {
        super(context, textViewResourceId, items);

        this.items = items;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {

        final int position = pos;
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

        delButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                items.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}