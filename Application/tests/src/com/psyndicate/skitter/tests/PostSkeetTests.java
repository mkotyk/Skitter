package com.psyndicate.skitter.tests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;

import com.psyndicate.skitter.R;
import com.psyndicate.skitter.controller.SkitterApp;
import com.psyndicate.skitter.model.Skeet;
import com.psyndicate.skitter.view.PostSkeetActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Integration tests for posting a skeet
 */
@RunWith(AndroidJUnit4.class)
public class PostSkeetTests {

    @Rule
    public ActivityTestRule<PostSkeetActivity> skitterPostSkeetActivityRule =
            new ActivityTestRule<>(PostSkeetActivity.class);

    @Before
    public void setUp() {
        SkitterApp skitterApp = (SkitterApp) skitterPostSkeetActivityRule.getActivity().getApplication();
        skitterApp.login("user1","password1");
    }

    @Test
    public void testPreconditions() {
        Assert.assertNotNull("PostSkeetActivity is null", skitterPostSkeetActivityRule.getActivity());
    }

    @Test
    @UiThreadTest
    public void testPosting() {
        final String expectedText = "This is a test #post from @tests";


        SkitterApp skitterApp = (SkitterApp) skitterPostSkeetActivityRule.getActivity().getApplication();
        List<Skeet> skeets = skitterApp.getSkeets();
        int preCount = skeets.size();


        skitterPostSkeetActivityRule.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                EditText skeetEditText= (EditText) skitterPostSkeetActivityRule.getActivity().findViewById(R.id.post_edit);
                Button postButton = (Button) skitterPostSkeetActivityRule.getActivity().findViewById(R.id.post_button);
                skeetEditText.setText(expectedText);
                postButton.performClick();
            }
        });

        // TODO: These should be deterministic.  Create a listener event when the list refreshes.
        // Mock Network is set to take 400ms.  Need to wait longer than that.
        try { Thread.sleep(600); } catch(InterruptedException ex){}
        List<Skeet> newSkeets = skitterApp.getSkeets();

        Assert.assertTrue(newSkeets.size() == preCount + 1);
        Assert.assertEquals(expectedText, newSkeets.get(0).getText());
    }

}
