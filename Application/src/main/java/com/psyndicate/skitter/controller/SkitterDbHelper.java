package com.psyndicate.skitter.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.psyndicate.skitter.model.Skeet;

import java.util.ArrayList;
import java.util.List;

/**
 * Skitter Database Routines
 */
public class SkitterDbHelper extends SQLiteOpenHelper {
    public static abstract class SkeetDef implements BaseColumns {
        public static final String TABLE_NAME = "skeets";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_TEXT = "text";

    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SkeetDef.TABLE_NAME + " (" +
                    SkeetDef.COLUMN_NAME_TIMESTAMP + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    SkeetDef.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
                    SkeetDef.COLUMN_NAME_TEXT + TEXT_TYPE  +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SkeetDef.TABLE_NAME;


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Skitter.db";

    public SkitterDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addNewSkeetsToDb(List<Skeet> newSkeets) {
        SQLiteDatabase db = this.getWritableDatabase();
        for(int index = 0; index < newSkeets.size(); index++) {
            Skeet skeet = newSkeets.get(index);
            ContentValues contentValues = new ContentValues();
            contentValues.put(SkeetDef.COLUMN_NAME_TIMESTAMP, skeet.getTimestamp());
            contentValues.put(SkeetDef.COLUMN_NAME_USERNAME, skeet.getPoster());
            contentValues.put(SkeetDef.COLUMN_NAME_TEXT, skeet.getText());
            db.insert(SkeetDef.TABLE_NAME, null, contentValues);
        }
    }

    public List<Skeet> querySeenSkeetsDb() {
        ArrayList<Skeet> seenSkeets = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(SkeetDef.TABLE_NAME,
                                null, // Column (all)
                                null, // Selection (all)
                                null, // Selection args
                                null, // Group by
                                null, // Having
                                SkeetDef.COLUMN_NAME_TIMESTAMP + " DESC", // Order By
                                "100"); // Limit

        while(cursor.moveToNext()) {
            Skeet skeet = new Skeet(
                    cursor.getString(cursor.getColumnIndex(SkeetDef.COLUMN_NAME_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(SkeetDef.COLUMN_NAME_TEXT)),
                    cursor.getLong(cursor.getColumnIndex(SkeetDef.COLUMN_NAME_TIMESTAMP)));
            seenSkeets.add(skeet);
        }
        cursor.close();
        return seenSkeets;
    }
}
