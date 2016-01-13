package com.psyndicate.skitter.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.psyndicate.skitter.Utils;
import com.psyndicate.skitter.model.AuthToken;
import com.psyndicate.skitter.model.Skeet;
import com.psyndicate.skitter.model.SkitterException;
import com.psyndicate.skitter.model.SkitterProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


/**
 * Main Skitter Application
 */
@Singleton
public class SkitterApp {
    private static final String TAG = "SkitterApp";
    private AuthToken token = null;
    private SkitterDbHelper skitterDbHelper; // Lazy created

    private SkitterProvider skitterProvider;
    private Context context;

    @Inject
    public SkitterApp(Context context, SkitterProvider skitterProvider) {
        this.context = context;
        this.skitterProvider = skitterProvider;
    }

    private SkitterDbHelper getSkitterDbHelper() {
        if(skitterDbHelper == null)
            skitterDbHelper = new SkitterDbHelper(this.context);
        return skitterDbHelper;
    }

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
        // Tree set to keep them sorted chronologically
        TreeSet<Skeet> skeets = new TreeSet<>();
        long lastSeenSkeet = 0;

        // Query old skeets from device db
        skeets.addAll(getSkitterDbHelper().querySeenSkeetsDb());
        if(skeets.size() > 0)
            lastSeenSkeet = skeets.first().getTimestamp();

        if(isAuthenticated()) {
            try {
                List<Skeet> newSkeets = skitterProvider.getSkeets(token, lastSeenSkeet);
                getSkitterDbHelper().addNewSkeetsToDb(newSkeets);
                skeets.addAll(newSkeets);
            }
            catch(SkitterException ex) {
                // Present this to the user?
                Log.e(TAG, "Unable to get new skeets", ex);
            }
        }
        return new ArrayList<>(skeets);
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
