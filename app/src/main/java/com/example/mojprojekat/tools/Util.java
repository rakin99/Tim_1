package com.example.mojprojekat.tools;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.mojprojekat.database.DBContentProviderEmail;
import com.example.mojprojekat.database.ReviewerSQLiteHelper;

public class Util {

    public static void initDB(Activity activity) {
        ReviewerSQLiteHelper dbHelper = new ReviewerSQLiteHelper(activity);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        {
            ContentValues entry = new ContentValues();
            entry.put(ReviewerSQLiteHelper.COLUMN_FROM, "Mile");
            entry.put(ReviewerSQLiteHelper.COLUMN_TO, "Mika");
            entry.put(ReviewerSQLiteHelper.COLUMN_CC, "CC");
            entry.put(ReviewerSQLiteHelper.COLUMN_BCC, "BCC");
            entry.put(ReviewerSQLiteHelper.COLUMN_DATE_TIME, "datetime");
            entry.put(ReviewerSQLiteHelper.COLUMN_SUBJECT, "Veliki problem");
            entry.put(ReviewerSQLiteHelper.COLUMN_CONTENT, "Dobar dan, imam veliki problem, koji ne mogu nikako resiti.");

            activity.getContentResolver().insert(DBContentProviderEmail.CONTENT_URI_EMAIL, entry);

            entry = new ContentValues();
            entry.put(ReviewerSQLiteHelper.COLUMN_FROM, "Zika");
            entry.put(ReviewerSQLiteHelper.COLUMN_TO, "Mika");
            entry.put(ReviewerSQLiteHelper.COLUMN_CC, "CC");
            entry.put(ReviewerSQLiteHelper.COLUMN_BCC, "BCC");
            entry.put(ReviewerSQLiteHelper.COLUMN_DATE_TIME, "datetime");
            entry.put(ReviewerSQLiteHelper.COLUMN_SUBJECT, "Mali problem");
            entry.put(ReviewerSQLiteHelper.COLUMN_CONTENT, "Dobar dan, imam mali problem, koji mogu resiti, ali mi se ne da.");

            activity.getContentResolver().insert(DBContentProviderEmail.CONTENT_URI_EMAIL, entry);
        }

        db.close();
    }
}
