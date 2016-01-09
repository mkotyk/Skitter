package com.psyndicate.skitter.view;

import android.app.Activity;
import android.os.Bundle;
import com.psyndicate.skitter.R;

/**
 * Skitter Main Activity
 */
public class SkitterMainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skitter_main_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public boolean isHappy() {
        return true;
    }
}
