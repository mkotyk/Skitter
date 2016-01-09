package com.psyndicate.skitter.tests;

import com.psyndicate.skitter.view.*;
import android.test.ActivityInstrumentationTestCase2;

public class SkitterMainTests extends ActivityInstrumentationTestCase2<SkitterMainActivity> {

    private SkitterMainActivity skitterMainActivity;

    public SkitterMainTests() {
        super(SkitterMainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        skitterMainActivity = getActivity();
    }

    public void testPreconditions() {
        assertNotNull("skitterMainActivity is null", skitterMainActivity);
    }

    public void testHappy() {
        assertTrue(skitterMainActivity.isHappy());
    }
}
