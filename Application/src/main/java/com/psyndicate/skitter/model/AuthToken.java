package com.psyndicate.skitter.model;

public class AuthToken {
    public long validToTimestamp;
    public long userId;
    public long tokenId;

    public boolean isValid() {
        return validToTimestamp > System.currentTimeMillis();
    }
};

