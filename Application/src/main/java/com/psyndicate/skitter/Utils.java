package com.psyndicate.skitter;

import java.security.MessageDigest;

/**
 * Junk drawer of utility functions.  First candidate for refactoring.
 **/
public class Utils {
    /**
     * SHA256 a plain text password.
     * NOTE: This is just for testing.  Java strings are considered
     * unsafe memory.
     **/
    public static byte[] hashPassword(String password) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes("UTF-8"));
        return md.digest();
    }
};
