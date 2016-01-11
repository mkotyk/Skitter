package com.psyndicate.skitter.controller;

import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.psyndicate.skitter.Utils;
import com.psyndicate.skitter.model.AuthToken;
import com.psyndicate.skitter.model.Skeet;
import com.psyndicate.skitter.model.SkitterException;
import com.psyndicate.skitter.model.SkitterProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Skitter Application
 */
@Singleton
public class SkitterApp {
    private static final String TAG = "SkitterApp";
    private AuthToken token = null;

    @Inject
    SkitterProvider skitterProvider;

    public boolean login(String username, String password) {
        try {
            this.token = skitterProvider.authenticate(username, Utils.hashPassword(password));
        }
        catch(Exception ex) {
            Log.e(TAG, "Error authenticating", ex);
        }
        return isAuthenticated();
    }

    public List<Skeet> getSkeets() {
        List<Skeet> skeets = new ArrayList<>();

        // TODO: Query old skeets from device db

        long lastSeenSkeet = 0;

        if(isAuthenticated()) {
            try {
                skeets.addAll(skitterProvider.getSkeets(token, lastSeenSkeet));
            }
            catch(SkitterException ex) {
                // Present this to the user?
                Log.e(TAG, "Unable to get new skeets", ex);
            }
        }
        return skeets;
    }

    public boolean isAuthenticated() {
        return ((token != null) && token.isValid());
    }

    public Boolean post(String message) {
        if(isAuthenticated()) {
            try {
                skitterProvider.post(token, new Skeet(message));
                return true;
            }
            catch(SkitterException ex) {
                Log.e(TAG, "Unable to post skeet", ex);
            }
        }
        return false;
    }
}
