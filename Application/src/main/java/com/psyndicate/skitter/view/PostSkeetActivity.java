package com.psyndicate.skitter.view;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.psyndicate.skitter.R;
import com.psyndicate.skitter.controller.SkitterApp;
import com.psyndicate.skitter.model.Skeet;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Post a skeet
 */
@ContentView(R.layout.post_skeet_activity)
public class PostSkeetActivity extends RoboActivity {
    private static final String TAG = "PostSkeetActivity";

    @InjectView(R.id.post_edit)
    EditText postEditText;
    @InjectView(R.id.post_button)
    Button postButton;
    @InjectView(R.id.post_remaining_text)
    TextView postRemainingText;

    @Inject
    SkitterApp skitterApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postEditText.setText("");
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(Skeet.MAX_SKEET_LENGTH);
        postEditText.setFilters(filterArray);
        postEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        postEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                int remaining = Skeet.MAX_SKEET_LENGTH - postEditText.getText().length();
                postRemainingText.setText(Integer.toString(remaining));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void post() {
        postButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(PostSkeetActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.posting));
        progressDialog.show();

        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... strings) {
                return skitterApp.post(strings[0]);
            }

            @Override
            protected void onPostExecute(Boolean postSuccess) {
                progressDialog.dismiss();
                if (postSuccess)
                    onPostSuccess();
                else
                    onPostFailed();
            }
        }.execute(postEditText.getText().toString());
    }

    public void onPostSuccess() {
        postButton.setEnabled(true);
        finish();
    }

    public void onPostFailed() {
        Toast.makeText(getBaseContext(), R.string.post_failed, Toast.LENGTH_LONG).show();
        postButton.setEnabled(true);
    }
}
