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
        assertTrue(token.userId == 1);
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
};
