package com.psyndicate.skitter.model;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.ArrayList;

import com.psyndicate.skitter.Utils;

import com.google.inject.Singleton;

@Singleton
public class MockSkitterProvider implements SkitterProvider {

    private class UserInfo {
        public String username;
        public byte[] passwordHash;
        public long userId;
        public SortedSet<Skeet> skeets = new TreeSet<>();
    }

    private Map<String, UserInfo> userDatabase = new HashMap<>();
    private Map<Long, UserInfo> tokens = new HashMap<>();

    public MockSkitterProvider() {
        seedUserDatabase();
    }

    private void seedUserDatabase() {
        long baseTime = 1452355797L * 1000L; // Time when I wrote this code.
        try {
            UserInfo user1 = new UserInfo();
            user1.username = "user1";
            user1.passwordHash = Utils.hashPassword("password1");
            user1.userId = 1;
            for(int x = 0; x < 15; x++) {
                user1.skeets.add(new Skeet(
                            user1.username,
                            String.format("This is skeet message %d for user 1.  Hi @user1 and #tag", x),
                            baseTime + x * 1000 * 60));
            }
            userDatabase.put(user1.username, user1);

            UserInfo user2 = new UserInfo();
            user2.username = "user2";
            user2.passwordHash = Utils.hashPassword("password2");
            user2.userId = 2;
            for(int x = 0; x < 25; x++) {
                user2.skeets.add(new Skeet(
                            user2.username,
                            String.format("This is skeet message %d for user 2.  Hi @user1 and #tag", x),
                            baseTime + x * 1000 * 60));
            }
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
        token.validToTimestamp = System.currentTimeMillis() + 1000 * 60 * 60;
        tokens.put(token.tokenId, info);
        return token;
    }

    private UserInfo checkToken(AuthToken token) throws SkitterException {
        if((token == null) ||
           !token.isValid() ||
           !tokens.containsKey(token.tokenId)) {
            throw new SkitterException("Authentication token is invalid.");
        }

        return tokens.get(token.tokenId);
    }

    /**
     * Get a list of skeets for this user.
     **/
    public List<Skeet> getSkeets(AuthToken token, long timestampStart) throws SkitterException {
        mockNetworkDelay();
        UserInfo info = checkToken(token);
        Skeet lastSeenSkeet = new Skeet();
        lastSeenSkeet.setTimestamp(timestampStart);
        return new ArrayList<>(info.skeets.headSet(lastSeenSkeet));
    }

    /**
     * Post a skeet.
     **/
    public void post(AuthToken token, Skeet skeet) throws SkitterException {
        mockNetworkDelay();
        if((skeet.getText() == null) || (skeet.getText().length() == 0))
            throw new SkitterException("Invalid skeet message");
        UserInfo info = checkToken(token);
        skeet.setPoster(info.username);
        skeet.setTimestamp(System.currentTimeMillis());
        info.skeets.add(skeet);
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
}
