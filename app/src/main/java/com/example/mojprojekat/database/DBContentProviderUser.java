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

public class DBContentProviderUser extends ContentProvider{

    private ReviewerSQLiteHelper database;

    private static final int USER = 30;
    private static final int USER_ID = 40;

    private static final String AUTHORITY = "com.example.mojprojekat.users";

    private static final String USER_PATH = "users";

    public static final Uri CONTENT_URI_USER = Uri.parse("content://" + AUTHORITY + "/" + USER_PATH);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, USER_PATH, USER);
        sURIMatcher.addURI(AUTHORITY, USER_PATH + "/#", USER_ID);
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
            case USER_ID:
                // Adding the ID to the original query
                queryBuilder.appendWhere(ReviewerSQLiteHelper.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                //$FALL-THROUGH$
            case USER:
                // Set the table
                queryBuilder.setTables(ReviewerSQLiteHelper.TABLE_USERS);
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
            case USER:
                id = sqlDB.insert(ReviewerSQLiteHelper.TABLE_USERS, null, values);
                retVal = Uri.parse(USER_PATH + "/" + id);
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
            case USER:
                rowsDeleted = sqlDB.delete(ReviewerSQLiteHelper.TABLE_USERS,
                        selection,
                        selectionArgs);
                break;
            case USER_ID:
                String idUser = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(ReviewerSQLiteHelper.TABLE_USERS,
                            ReviewerSQLiteHelper.COLUMN_ID + "=" + idUser,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(ReviewerSQLiteHelper.TABLE_USERS,
                            ReviewerSQLiteHelper.COLUMN_ID + "=" + idUser
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
            case USER:
                rowsUpdated = sqlDB.update(ReviewerSQLiteHelper.TABLE_USERS,
                        values,
                        selection,
                        selectionArgs);
                break;
            case USER_ID:
                String idUser = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(ReviewerSQLiteHelper.TABLE_USERS,
                            values,
                            ReviewerSQLiteHelper.COLUMN_ID + "=" + idUser,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(ReviewerSQLiteHelper.TABLE_USERS,
                            values,
                            ReviewerSQLiteHelper.COLUMN_ID + "=" + idUser
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
