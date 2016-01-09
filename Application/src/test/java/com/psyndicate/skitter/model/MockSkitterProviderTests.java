package com.psyndicate.skitter.model;

import junit.framework.TestCase;
import java.util.List;
import com.psyndicate.skitter.Utils;

public class MockSkitterProviderTests extends TestCase {

    /**
     * Test authenticating hard coded user1.
     **/
    public void testAuthenticateUser1() throws Exception {
        // Arrange
        MockSkitterProvider skitterProvider = new MockSkitterProvider();

        // Act
        AuthToken token = skitterProvider.authenticate("user1", Utils.hashPassword("password1"));

        // Assert
        assertNotNull(token);
        assertTrue(token.validToTimestamp > System.currentTimeMillis());
        assertEquals(1, token.userId);
    }

    /**
     * Test authenticating an unknown user
     **/
    public void testAuthenticateUnknownUser() throws Exception {
        // Arrange
        MockSkitterProvider skitterProvider = new MockSkitterProvider();

        // Act
        AuthToken token = skitterProvider.authenticate("hacker1", Utils.hashPassword("hackword1"));

        // Assert
        assertNull(token);
    }

    /**
     * Ensure all calls to the Mock provider take about 1/2 second.
     **/
    public void testNetworkDelay() throws Exception {
        // Arrange
        long startTime = System.currentTimeMillis();
        MockSkitterProvider skitterProvider = new MockSkitterProvider();

        // Act
        skitterProvider.authenticate("hacker1", Utils.hashPassword("hackword1"));

        // Assert
        assertTrue(System.currentTimeMillis() - startTime >= 500);
    }

    /**
     * Ensure calling get skeets with no valid auth token fails.
     **/
    public void testGetSkeetsUnauth() throws Exception {
        // Arrange
        MockSkitterProvider skitterProvider = new MockSkitterProvider();
        AuthToken token = new AuthToken(); // Blank token
        boolean expectedResult = false;

        // Act
        try {
            skitterProvider.getSkeets(token, System.currentTimeMillis());
        }
        catch(SkitterException ex) {
            expectedResult = true;
        }

        // Assert
        assertTrue(expectedResult);
    }

    /**
     * Get all known skeets
     **/
    public void testGetSkeetsAll() throws Exception {
        // Arrange
        MockSkitterProvider skitterProvider = new MockSkitterProvider();
        AuthToken token = skitterProvider.authenticate("user1", Utils.hashPassword("password1"));

        // Act
        List<Skeet> skeets = skitterProvider.getSkeets(token, 0);

        // Assert
        assertNotNull(skeets);
        assertEquals(15, skeets.size());
    }

    /**
     * Get new skeets
     **/
    public void testGetSkeetsNew() throws Exception {
        // Arrange
        MockSkitterProvider skitterProvider = new MockSkitterProvider();
        AuthToken token = skitterProvider.authenticate("user1", Utils.hashPassword("password1"));
        List<Skeet> skeets = skitterProvider.getSkeets(token, 0);
        long lastSkeetTimestamp = skeets.get(0).getTimestamp();

        // Act
        List<Skeet> newSkeets = skitterProvider.getSkeets(token, lastSkeetTimestamp);

        // Assert
        assertNotNull(newSkeets);
        assertEquals(0, newSkeets.size());
    }

    /**
     * Post a skeet
     **/
    public void testPostSkeet() throws Exception {
        // Arrange
        MockSkitterProvider skitterProvider = new MockSkitterProvider();
        AuthToken token = skitterProvider.authenticate("user1", Utils.hashPassword("password1"));
        String expectedMessage = "This is a test message";

        // Act
        skitterProvider.post(token, new Skeet(expectedMessage));
        List<Skeet> skeets = skitterProvider.getSkeets(token, 0);

        // Assert
        assertNotNull(skeets);
        assertEquals(16, skeets.size());
        assertEquals(expectedMessage, skeets.get(0).getText());
    }
};
