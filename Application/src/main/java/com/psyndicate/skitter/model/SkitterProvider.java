package com.psyndicate.skitter.model;

import com.google.inject.ImplementedBy;

import java.util.List;

//@ImplementedBy(MockSkitterProvider.class) // - Let the module decide this
public interface SkitterProvider {

    /**
     * Authenicate a user
     **/
    AuthToken authenticate(String username, byte[] passwordHash) throws SkitterException;

    /**
     * Get a list of skeets for this user.
     **/
    List<Skeet> getSkeets(AuthToken auth, long timestampStart) throws SkitterException;

    /**
     * Post a skeet.
     **/
    void post(AuthToken token, Skeet skeet) throws SkitterException;
}

