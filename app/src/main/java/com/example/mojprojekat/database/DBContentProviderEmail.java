package com.example.mojprojekat.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.Nullable;

public class DBContentProviderEmail extends ContentProvider {

    private ReviewerSQLiteHelper database;

    private static final int EMAIL = 10;
    private static final int EMAIL_ID = 20;

    private static final String AUTHORITY = "com.example.mojprojekat.emails";

    private static final String EMAIL_PATH = "email";

    public static final Uri CONTENT_URI_EMAIL = Uri.parse("content://" + AUTHORITY + "/" + EMAIL_PATH);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, EMAIL_PATH, EMAIL);
        sURIMatcher.addURI(AUTHORITY, EMAIL_PATH + "/#", EMAIL_ID);
    }

    @Override
    public boolean onCreate() {
        database = new ReviewerSQLiteHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exist
        //checkColumns(projection);
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case EMAIL_ID:
                // Adding the ID to the original query
                queryBuilder.appendWhere(ReviewerSQLiteHelper.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                //$FALL-THROUGH$
            case EMAIL:
                // Set the table
                queryBuilder.setTables(ReviewerSQLiteHelper.TABLE_EMAILS);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri retVal = null;
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case EMAIL:
                id = sqlDB.insert(ReviewerSQLiteHelper.TABLE_EMAILS, null, values);
                retVal = Uri.parse(EMAIL_PATH + "/" + id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return retVal;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        int rowsDeleted = 0;
        switch (uriType) {
            case EMAIL:
                rowsDeleted = sqlDB.delete(ReviewerSQLiteHelper.TABLE_EMAILS,
                        selection,
                        selectionArgs);
                break;
            case EMAIL_ID:
                String idEmail = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(ReviewerSQLiteHelper.TABLE_EMAILS,
                            ReviewerSQLiteHelper.COLUMN_ID + "=" + idEmail,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(ReviewerSQLiteHelper.TABLE_EMAILS,
                            ReviewerSQLiteHelper.COLUMN_ID + "=" + idEmail
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        int rowsUpdated = 0;
        switch (uriType) {
            case EMAIL:
                rowsUpdated = sqlDB.update(ReviewerSQLiteHelper.TABLE_EMAILS,
                        values,
                        selection,
                        selectionArgs);
                break;
            case EMAIL_ID:
                String idMessage = uri.getLastPathSegment();
                System.out.println("\nidMessage je: "+idMessage+"<-------------------------------------------\n");
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(ReviewerSQLiteHelper.TABLE_EMAILS,
                            values,
                            ReviewerSQLiteHelper.COLUMN_ID + "=" + idMessage,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(ReviewerSQLiteHelper.TABLE_EMAILS,
                            values,
                            ReviewerSQLiteHelper.COLUMN_ID + "=" + idMessage
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
