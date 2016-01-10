package com.psyndicate.skitter.tests;

import com.psyndicate.skitter.view.*;
import com.psyndicate.skitter.R;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Log In Integration Tests
 */
public class LogInTests extends ActivityInstrumentationTestCase2<LogInActivity> {

    private LogInActivity logInActivity;

    public LogInTests() {
        super(LogInActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);

        logInActivity = getActivity();
    }

    public void testPreconditions() {
        assertNotNull("logInActivity is null", logInActivity);
    }

    public void testComponentsResolve() {
        EditText userNameEditText = (EditText) logInActivity.findViewById(R.id.user_name_edit);
        EditText passwordEditText = (EditText) logInActivity.findViewById(R.id.password_edit);
        Button logInButton = (Button) logInActivity.findViewById(R.id.login_button);

        assertNotNull(userNameEditText);
        assertNotNull(passwordEditText);
        assertNotNull(logInButton);
    }

    public void testImageValid() {
        ImageView imageView = (ImageView) logInActivity.findViewById(R.id.logo_image);

        assertNotNull(imageView);
        assertNotNull(imageView.getDrawable());
    }
}
