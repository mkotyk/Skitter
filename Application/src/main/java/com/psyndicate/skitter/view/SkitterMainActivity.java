package com.psyndicate.skitter.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.inject.Inject;
import com.psyndicate.skitter.R;
import com.psyndicate.skitter.SkitterApp;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;

/**
 * Skitter Main Activity
 */
@ContentView(R.layout.skitter_main_activity)
public class SkitterMainActivity extends RoboActivity {
    @Inject SkitterApp skitterApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If we have no valid log in, present the log in screen
        if(!skitterApp.isAuthenticated()) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
}
