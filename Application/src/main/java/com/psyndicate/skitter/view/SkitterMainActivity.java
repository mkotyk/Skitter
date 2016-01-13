package com.psyndicate.skitter.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.inject.Inject;
import com.psyndicate.skitter.R;
import com.psyndicate.skitter.controller.SkitterApp;
import com.psyndicate.skitter.model.Skeet;

import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Skitter Main Activity
 */
@ContentView(R.layout.skitter_main_activity)
public class SkitterMainActivity extends RoboActivity {
    @InjectView(R.id.skeet_list)    ListView skeetList;
    @InjectView(R.id.post_activity_button)  Button postActivityButton;
    @Inject SkitterApp skitterApp;
    private ArrayAdapter<Skeet> skeetArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        skeetArrayAdapter =  new SkeetArrayAdapter(SkitterMainActivity.this,
                R.layout.skeet_view);
        skeetList.setAdapter(skeetArrayAdapter);

        postActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SkitterMainActivity.this, PostSkeetActivity.class);
                startActivity(intent);
            }
        });

        // If we have no valid log in, present the log in screen
        if(!skitterApp.isAuthenticated()) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        } else {
            loadSkeets();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Refresh
        loadSkeets();
    }

    public void loadSkeets() {
        // Need to run network tasks on another thread, and the follow up UI tasks
        // back on the UI thread
        new AsyncTask<Void, Void, List<Skeet>>() {

            @Override
            protected List<Skeet> doInBackground(Void... strings) {
                return skitterApp.getSkeets();
            }

            @Override
            protected void onPostExecute(List<Skeet> skeets) {
                skeetArrayAdapter.clear();
                skeetArrayAdapter.addAll(skeets);
            }
        }.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSkeets();
    }
}
