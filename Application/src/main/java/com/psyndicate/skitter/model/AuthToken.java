package com.psyndicate.skitter.model;

public class AuthToken {
    public long validToTimestamp = -1;
    public long userId = -1;
    public long tokenId = -1;

    public boolean isValid() {
        return ((validToTimestamp > System.currentTimeMillis()) &&
                (userId != -1) &&
                (tokenId != -1));
    }
}

