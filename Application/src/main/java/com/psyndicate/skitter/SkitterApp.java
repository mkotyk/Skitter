package com.psyndicate.skitter;

import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.psyndicate.skitter.model.AuthToken;
import com.psyndicate.skitter.model.SkitterProvider;

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

    public boolean isAuthenticated() {
        return ((token != null) && token.isValid());
    }
}
