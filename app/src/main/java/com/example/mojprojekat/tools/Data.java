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
    public static List<Message> newMessages=new ArrayList<Message>();
    public static long maxId=0;

    public static void readMessages(Activity activity,String keyword,String username) throws ParseException {
        messages.clear();
        System.out.println("Poceo ucitavanje poruka iz baze!<---------------------------------------");
        String[] allColumns = { ReviewerSQLiteHelper.COLUMN_ID,
                ReviewerSQLiteHelper.COLUMN_FROM, ReviewerSQLiteHelper.COLUMN_TO, ReviewerSQLiteHelper.COLUMN_CC, ReviewerSQLiteHelper.COLUMN_BCC,
                ReviewerSQLiteHelper.COLUMN_DATE_TIME,  ReviewerSQLiteHelper.COLUMN_SUBJECT, ReviewerSQLiteHelper.COLUMN_CONTENT, ReviewerSQLiteHelper.COLUMN_UNREAD, ReviewerSQLiteHelper.COLUMN_ACTIVE };

        String selectionClause=ReviewerSQLiteHelper.COLUMN_TO+" LIKE ?";
        String[] selectionArgs={"%"+username+"%"};
        String sort=ReviewerSQLiteHelper.COLUMN_DATE_TIME+" "+keyword;

        Cursor cursor = activity.getContentResolver().query(dbContentProviderEmail.CONTENT_URI_EMAIL, allColumns, selectionClause, selectionArgs,
                sort);

        while(cursor.moveToNext()){
            Message message = createMessage(cursor);
            if(message.getId()>maxId){
                maxId=message.getId();
            }
            if(message.isActive()){
                messages.add(message);
            }
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
        message.setDateTime(DateUtil.convertFromDMYHMS(cursor.getString(5)));
        message.setSubject(cursor.getString(6));
        message.setContent(cursor.getString(7));
        if(Integer.parseInt(cursor.getString(8))==1){
            message.setUnread(true);
        }else if (Integer.parseInt(cursor.getString(8))==0){
            message.setUnread(false);
        }
        if(Integer.parseInt(cursor.getString(9))==1){
            message.setActive(true);
        }else if (Integer.parseInt(cursor.getString(9))==0){
            message.setActive(false);
        }
        return message;
    }

    public static Message getMessageById(Long id) throws ParseException {
        for (Message message:messages
             ) {
            if(message.getId()==id){
                return message;
            }
        }
        return null;
    }

    public static void addMessage(Service service, Message message) throws ParseException {
        if(!(isInMessages(message))){
            newMessages.add(message);
            messages.add(message);
            System.out.println("\nUpisivanje u bazu poslate poruke!<---------------------------\n");
            System.out.println("Id poruke: "+message.getId());
            Util.insertMessage(service,message);
        }
    }

    public static boolean isInMessages(Message message){
        for (Message m:messages
             ) {
            if(m.getId()==message.getId()){
                return true;
            }
        }
        return false;
    }
}
