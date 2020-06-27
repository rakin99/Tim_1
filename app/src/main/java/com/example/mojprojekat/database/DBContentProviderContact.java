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

public class DBContentProviderContact extends ContentProvider {

    private ReviewerSQLiteHelper database;

    private static final int CONTACT = 50;
    private static final int CONTACT_Id = 60;

    private static final String AUTHORITY = "com.example.mojprojekat.contacts";

    private static final String CONTACT_PATH = "contacts";

    public static final Uri CONTENT_URI_CONTACT = Uri.parse("content://" + AUTHORITY + "/" + CONTACT_PATH);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, CONTACT_PATH, CONTACT);
        sURIMatcher.addURI(AUTHORITY, CONTACT_PATH + "/#", CONTACT_Id);
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
            case CONTACT_Id:
                // Adding the ID to the original query
                queryBuilder.appendWhere(ReviewerSQLiteHelper.COLUMN_Id + "="
                        + uri.getLastPathSegment());
                //$FALL-THROUGH$
            case CONTACT:
                // Set the table
                queryBuilder.setTables(ReviewerSQLiteHelper.TABLE_CONTACTS);
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
            case CONTACT:
                id = sqlDB.insert(ReviewerSQLiteHelper.TABLE_CONTACTS, null, values);
                retVal = Uri.parse(CONTACT_PATH + "/" + id);
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
            case CONTACT:
                rowsDeleted = sqlDB.delete(ReviewerSQLiteHelper.TABLE_CONTACTS,
                        selection,
                        selectionArgs);
                break;
            case CONTACT_Id:
                String idContact = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(ReviewerSQLiteHelper.TABLE_CONTACTS,
                            ReviewerSQLiteHelper.COLUMN_ID + "=" + idContact,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(ReviewerSQLiteHelper.TABLE_CONTACTS,
                            ReviewerSQLiteHelper.COLUMN_ID + "=" + idContact
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
            case CONTACT:
                rowsUpdated = sqlDB.update(ReviewerSQLiteHelper.TABLE_CONTACTS,
                        values,
                        selection,
                        selectionArgs);
                break;
            case CONTACT_Id:
                String idCinema = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(ReviewerSQLiteHelper.TABLE_CONTACTS,
                            values,
                            ReviewerSQLiteHelper.COLUMN_ID + "=" + idCinema,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(ReviewerSQLiteHelper.TABLE_CONTACTS,
                            values,
                            ReviewerSQLiteHelper.COLUMN_ID + "=" + idCinema
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
