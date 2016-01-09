package com.psyndicate.skitter.model;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import com.psyndicate.skitter.Utils;

public class MockSkitterProvider implements SkitterProvider {

    private class UserInfo {
        public String username;
        public byte[] passwordHash;
        public long userId;
    };

    private Map<String, UserInfo> userDatabase = new HashMap<String, UserInfo>();

    public MockSkitterProvider() {
        seedUserDatabase();
    }

    private void seedUserDatabase() {
        try {
            UserInfo user1 = new UserInfo();
            user1.username = "user1";
            user1.passwordHash = Utils.hashPassword("password1");
            user1.userId = 1;
            userDatabase.put(user1.username, user1);

            UserInfo user2 = new UserInfo();
            user2.username = "user2";
            user2.passwordHash = Utils.hashPassword("password2");
            user2.userId = 2;
            userDatabase.put(user2.username, user2);
        }
        catch(Exception ex) {
            ex.printStackTrace(); // This should be a logging framework.
        }
    }

    /**
     * Authenicate a user
     **/
    public AuthToken authenticate(String username, byte[] passwordHash) throws SkitterException {
        mockNetworkDelay();
        if(!userDatabase.containsKey(username))
            return null;

        UserInfo info = userDatabase.get(username);
        if(!Arrays.equals(passwordHash, info.passwordHash))
            return null;

        AuthToken token = new AuthToken();
        token.tokenId = (long)(Math.random() * Long.MAX_VALUE);
        token.userId = info.userId;
        // Token is valid for an hour
        token.validToTimestamp = System.currentTimeMillis() + 1000 * 60 * 60 * 1;
        return token;
    }

    /**
     * Get a list of skeets for this user.
     **/
    public List<Skeet> getSkeets(AuthToken auth, long timestampStart) throws SkitterException {
        // TODO
        mockNetworkDelay();
        throw new SkitterException("Not implemented.");
    }

    /**
     * Post a skeet.
     **/
    public void post(AuthToken token, Skeet skeet) throws SkitterException {
        // TODO
        mockNetworkDelay();
        throw new SkitterException("Not implemented.");
    }

    /**
     * Used to simulate a network delay.
     **/
    private void mockNetworkDelay() {
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException ex) {
        }
    }
};
