package com.example.mojprojekat.tools;

import android.app.Activity;
import android.app.Service;
import android.database.Cursor;

import com.example.mojprojekat.database.DBContentProviderEmail;
import com.example.mojprojekat.database.ReviewerSQLiteHelper;
import com.example.mojprojekat.model.Message;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private  static DBContentProviderEmail dbContentProviderEmail;
    public static List<Message> messages=new ArrayList<Message>();

    public static void readMessages(Activity activity) throws ParseException {
        messages.clear();
        System.out.println("Poceo ucitavanje poruka iz baze!<---------------------------------------");
        String[] allColumns = { ReviewerSQLiteHelper.COLUMN_ID,
                ReviewerSQLiteHelper.COLUMN_FROM, ReviewerSQLiteHelper.COLUMN_TO, ReviewerSQLiteHelper.COLUMN_CC, ReviewerSQLiteHelper.COLUMN_BCC,
                ReviewerSQLiteHelper.COLUMN_DATE_TIME,  ReviewerSQLiteHelper.COLUMN_SUBJECT, ReviewerSQLiteHelper.COLUMN_CONTENT };

        Cursor cursor = activity.getContentResolver().query(dbContentProviderEmail.CONTENT_URI_EMAIL, allColumns, null, null,
                null);

        while(cursor.moveToNext()){
            Message message = createMessage(cursor);

            messages.add(message);
        }
        System.out.println("Zavrsio ucitavanje iz baze!<-------------------------------------------------");
    }

    public static Message createMessage(Cursor cursor) throws ParseException {
        Message message = new Message();
        message.setId(cursor.getLong(0));
        //Log.d("Id--------------------->",String.valueOf(message.getId()));
        message.setFrom(cursor.getString(1));
        message.setTo(cursor.getString(2));
        message.setCc(cursor.getString(3));
        message.setBcc(cursor.getString(4));
        message.setDateTime(cursor.getString(5));
        message.setSubject(cursor.getString(6));
        message.setContent(cursor.getString(7));
        return message;
    }

    public static Message getMessageById(Long id,Activity activity) throws ParseException {
        for (Message message:messages
             ) {
            if(message.getId()==id){
                return message;
            }
        }
        return null;
    }

    public static void addMessage(Service service,Message message){
        messages.add(message);
        Util.insertMessage(service,message);
    }
}
