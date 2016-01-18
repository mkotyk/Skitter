package com.psyndicate.skitter.tests;


import com.psyndicate.skitter.R;
import com.psyndicate.skitter.controller.SkitterApp;
import com.psyndicate.skitter.model.Skeet;
import com.psyndicate.skitter.view.*;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;

import android.widget.Button;
import android.widget.ListView;

@RunWith(AndroidJUnit4.class)
public class SkitterMainTests {

    @Rule
    public ActivityTestRule<SkitterMainActivity> skitterMainActivityRule =
            new ActivityTestRule<>(SkitterMainActivity.class);

    @Before
    public void setUp() throws Exception {
        SkitterApp skitterApp = (SkitterApp) skitterMainActivityRule.getActivity().getApplication();
        skitterApp.login("user1", "password1");
    }

    @Test
    public void testPreconditions() {
        Assert.assertNotNull("skitterMainActivity is null",
                skitterMainActivityRule.getActivity());
    }

    @Test
    public void testListView() {
        ListView skeetList = (ListView) skitterMainActivityRule.getActivity().findViewById(R.id.skeet_list);

        // Mock Network is set to take 400ms.  Need to wait longer than that.
        try { Thread.sleep(600); } catch(InterruptedException ex){}
        Assert.assertTrue(skeetList.getAdapter() instanceof SkeetArrayAdapter);
        Assert.assertTrue(skeetList.getItemAtPosition(0) instanceof Skeet);
    }

    @Test
    public void testPostButton() {
        Button postButton = (Button) skitterMainActivityRule.getActivity().findViewById(R.id.post_activity_button);
        Assert.assertNotNull(postButton);
    }
}
