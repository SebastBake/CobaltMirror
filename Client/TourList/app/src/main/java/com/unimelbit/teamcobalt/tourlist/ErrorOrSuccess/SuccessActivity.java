package com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;

/**
 * Simple activity intended to notify the user of a success. Isn't currently in use.
 */
public class SuccessActivity extends AppCompatActivity {

    private static String INTENT_MSG = "com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess.SuccessActivity.INTENT_MSG";

    private String msg;

    /**
     * Required onCreateMethod
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        msg = getIntent().getStringExtra(INTENT_MSG);
        ((TextView) findViewById(R.id.success_message_out)).setText(msg);
    }

    /**
     * Public static method to start the success activity
     */
    public static void newSuccess(Activity from, String msg) {

        Intent i = new Intent(from, SuccessActivity.class);
        i.putExtra(INTENT_MSG,msg);
        from.startActivity(i);
        from.finish();
    }

    /**
     * Go back to the baseactivity on back press
     */
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, BaseActivity.class);
        startActivity(i);
        finish();
    }
}
