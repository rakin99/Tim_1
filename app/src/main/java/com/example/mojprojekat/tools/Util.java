package com.example.mojprojekat.tools;

import android.app.Activity;
import android.app.Service;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mojprojekat.database.DBContentProviderEmail;
import com.example.mojprojekat.database.DBContentProviderUser;
import com.example.mojprojekat.database.ReviewerSQLiteHelper;
import com.example.mojprojekat.model.Message;

import java.text.ParseException;

public class Util {

    public static void insertMessage(Service service, Message message) throws ParseException {
        ReviewerSQLiteHelper dbHelper = new ReviewerSQLiteHelper(service);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        {
            ContentValues entry = new ContentValues();
            entry.put(ReviewerSQLiteHelper.COLUMN_ID, message.getId());
            entry.put(ReviewerSQLiteHelper.COLUMN_FROM, message.getFrom());
            entry.put(ReviewerSQLiteHelper.COLUMN_TO, message.getTo());
            entry.put(ReviewerSQLiteHelper.COLUMN_CC, message.getCc());
            entry.put(ReviewerSQLiteHelper.COLUMN_BCC, message.getBcc());
            entry.put(ReviewerSQLiteHelper.COLUMN_DATE_TIME, DateUtil.formatTimeWithSecond(message.getDateTime()));
            entry.put(ReviewerSQLiteHelper.COLUMN_SUBJECT, message.getSubject());
            entry.put(ReviewerSQLiteHelper.COLUMN_CONTENT, message.getContent());
            entry.put(ReviewerSQLiteHelper.COLUMN_UNREAD, message.isUnread());
            entry.put(ReviewerSQLiteHelper.COLUMN_ACTIVE, message.isActive());

            service.getContentResolver().insert(DBContentProviderEmail.CONTENT_URI_EMAIL, entry);
        }
        db.close();
    }

    public static void initDBUsers(Activity activity){
        ReviewerSQLiteHelper dbHelperUser=new ReviewerSQLiteHelper(activity);
        SQLiteDatabase dbUser=dbHelperUser.getWritableDatabase();
        {
            ContentValues entryUser=new ContentValues();
            entryUser.put(ReviewerSQLiteHelper.COLUMN_SMTP,"Mika");
            entryUser.put(ReviewerSQLiteHelper.COLUMN_POP3_IMAP,"Mikic");
            entryUser.put(ReviewerSQLiteHelper.COLUMN_DISPLAY,"Slika lepog Mike");
            entryUser.put(ReviewerSQLiteHelper.COLUMN_USERNAME,"m");
            entryUser.put(ReviewerSQLiteHelper.COLUMN_PASSWORD,"m");

            activity.getContentResolver().insert(DBContentProviderUser.CONTENT_URI_USER,entryUser);

            entryUser=new ContentValues();
            entryUser.put(ReviewerSQLiteHelper.COLUMN_SMTP,"Mile");
            entryUser.put(ReviewerSQLiteHelper.COLUMN_POP3_IMAP,"Milic");
            entryUser.put(ReviewerSQLiteHelper.COLUMN_DISPLAY,"Slika lepog Mileta");
            entryUser.put(ReviewerSQLiteHelper.COLUMN_USERNAME,"lepimile@gmail.com");
            entryUser.put(ReviewerSQLiteHelper.COLUMN_DISPLAY,"0000");

            activity.getContentResolver().insert(DBContentProviderUser.CONTENT_URI_USER,entryUser);
        }
        dbUser.close();
    }

    public static ContentValues createContentValues(Activity activity, Message message) throws ParseException {
        ReviewerSQLiteHelper dbHelper = new ReviewerSQLiteHelper(activity);
        ContentValues entry = new ContentValues();
        Log.d("From: ",message.getFrom());
        Log.d("To: ",message.getTo());
        entry.put(ReviewerSQLiteHelper.COLUMN_FROM, message.getFrom());
        entry.put(ReviewerSQLiteHelper.COLUMN_TO, message.getTo());
        entry.put(ReviewerSQLiteHelper.COLUMN_CC, message.getCc());
        entry.put(ReviewerSQLiteHelper.COLUMN_BCC, message.getBcc());
        entry.put(ReviewerSQLiteHelper.COLUMN_DATE_TIME, DateUtil.formatTimeWithSecond(message.getDateTime()));
        entry.put(ReviewerSQLiteHelper.COLUMN_SUBJECT, message.getSubject());
        entry.put(ReviewerSQLiteHelper.COLUMN_CONTENT, message.getContent());
        entry.put(ReviewerSQLiteHelper.COLUMN_UNREAD, message.isUnread());
        entry.put(ReviewerSQLiteHelper.COLUMN_ACTIVE, message.isActive());

        return entry;
    }
}
