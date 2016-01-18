package com.psyndicate.skitter.tests;

import com.psyndicate.skitter.view.*;
import com.psyndicate.skitter.R;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Log In Integration Tests
 */
@RunWith(AndroidJUnit4.class)
public class LogInTests {

    @Rule
    public ActivityTestRule<LogInActivity> skitterLogInActivityRule =
            new ActivityTestRule<>(LogInActivity.class);

    @Test
    public void testPreconditions() {
        Assert.assertNotNull("logInActivity is null", skitterLogInActivityRule.getActivity());
    }

    @Test
    public void testComponentsResolve() {
        EditText userNameEditText = (EditText) skitterLogInActivityRule.getActivity().findViewById(R.id.user_name_edit);
        EditText passwordEditText = (EditText) skitterLogInActivityRule.getActivity().findViewById(R.id.password_edit);
        Button logInButton = (Button) skitterLogInActivityRule.getActivity().findViewById(R.id.login_button);

        Assert.assertNotNull(userNameEditText);
        Assert.assertNotNull(passwordEditText);
        Assert.assertNotNull(logInButton);
    }

    @Test
    public void testImageValid() {
        ImageView imageView = (ImageView) skitterLogInActivityRule.getActivity().findViewById(R.id.logo_image);

        Assert.assertNotNull(imageView);
        Assert.assertNotNull(imageView.getDrawable());
    }
}
