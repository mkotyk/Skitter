package com.psyndicate.skitter.model;

import junit.framework.TestCase;

public class SkitterTests extends TestCase {

    /**
     * Test setting a text over the limit.  This should truncate.
     **/
    public void testTextLimit() {
        // Arrange
        String testString = "";
        for(int index = 0; index < Skeet.MAX_SKEET_LENGTH; index++) {
            testString = testString + "AA";
        }
        Skeet skeet = new Skeet();

        // Act
        skeet.setText(testString);

        // Assert
        assertEquals(skeet.getText().length(), Skeet.MAX_SKEET_LENGTH);
    }

    /**
     * Test setting text to null.  This will be ignored and the
     * previous value will be used.
     **/
    public void testSetTextNull() {
        // Arrange
        String expectedText = "This is a test.";
        Skeet skeet = new Skeet();
        skeet.setText(expectedText);

        // Act
        skeet.setText(null);

        // Assert
        assertEquals(skeet.getText(), expectedText);
    }

    /**
     * Test setting invalid timestamp.  This is allowed for now.
     **/
    public void testSetTimestampInvalid() {
        // Arrange
        Skeet skeet = new Skeet();

        // Act
        skeet.setTimestamp(-1);

        // Assert
        assertEquals(skeet.getTimestamp(), -1);
    }

    /**
     * Test happy path of setting valid text.
     **/
    public void testSetTextGood() {
        // Arrange
        String expectedText = "This is a test of expected text.";
        Skeet skeet = new Skeet();

        // Act
        skeet.setText(expectedText);

        // Assert
        assertEquals(skeet.getText(), expectedText);
    }

    /**
     *  Test happy path of setting valid timestamp.
     **/
    public void testSetTimestampGood() {
        // Arrange
        long expectedTimestamp = System.currentTimeMillis();
        Skeet skeet = new Skeet();

        // Act
        skeet.setTimestamp(expectedTimestamp);

        // Assert
        assertEquals(skeet.getTimestamp(), expectedTimestamp);
    }
};
