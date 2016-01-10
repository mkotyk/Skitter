package com.psyndicate.skitter.view;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.inject.Inject;
import com.psyndicate.skitter.R;
import com.psyndicate.skitter.controller.SkitterApp;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Log In Activity
 */
@ContentView(R.layout.log_in_activity)
public class LogInActivity extends RoboActivity {
    private static final String TAG = "LogInActivity";

    @InjectView(R.id.user_name_edit)    EditText usernameEditText;
    @InjectView(R.id.password_edit)     EditText passwordEditText;
    @InjectView(R.id.login_button)      Button loginButton;

    @Inject
    SkitterApp skitterApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // I saw some examples where the click listeners were
        // bound in code.  This made a lot of sense to me
        // by keeping layout separate from code.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void logIn() {
        loginButton.setEnabled(false);

        final String username = usernameEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        if ((username.length() == 0) || (password.length() == 0)) {
            onLoginFailed();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(LogInActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.authenticating));
        progressDialog.show();

        // Need to run network tasks on another thread, and the follow up UI tasks
        // back on the UI thread
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... strings) {
                return skitterApp.login(username, password);
            }

            @Override
            protected void onPostExecute(Boolean logInSuccess) {
                progressDialog.dismiss();
                if(logInSuccess)
                    onLoginSuccess();
                else
                    onLoginFailed();
            }
        }.execute();
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), R.string.login_failed, Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
