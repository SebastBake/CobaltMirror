package com.unimelbit.teamcobalt.tourlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Hong Lin on 24/09/2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Start home activity

        startActivity(new Intent(SplashActivity.this, BaseActivity.class));

        // close splash activity

        finish();

    }

}
