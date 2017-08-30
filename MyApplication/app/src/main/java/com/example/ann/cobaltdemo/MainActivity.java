package com.example.ann.cobaltdemo;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class MainActivity extends AppCompatActivity {
    String[] nameArray = {"Place", "Place", "Place", "Place" };

    String[] infoArray = {
            "Delicious in rolls",
            "Great for jumpers",
            "Great for shoes",
            "Scary."
    };

    Integer[] imageArray = {R.drawable.demo_img,
            R.drawable.demo_img,
            R.drawable.demo_img,
            R.drawable.demo_img
            };

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Chat chat = new Chat();

        CustomListAdapter whatever = new CustomListAdapter(this, nameArray, infoArray, imageArray);
        listView = (ListView) findViewById(R.id.places_list_view);
        listView.setAdapter(whatever);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_trip:
                        setContentView(R.layout.activity_main);
                        break;
                    case R.id.action_map:
                        Toast.makeText(MainActivity.this, "Map", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_chat:
                        setContentView(R.layout.activity_chat);
                        break;
                }
                return true;
            }
        });
    }
}
