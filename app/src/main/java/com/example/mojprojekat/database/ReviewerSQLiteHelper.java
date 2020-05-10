package com.example.mojprojekat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReviewerSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_EMAILS = "emails";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FROM = "from";
    public static final String COLUMN_TO = "to";
    public static final String COLUMN_CC = "cc";
    public static final String COLUMN_BCC = "bcc";
    public static final String COLUMN_DATE_TIME = "dateTime";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_CONTENT = "content";

    private static final String DATABASE_NAME = "emails.db";
    //i pocetnu verziju baze. Obicno krece od 1
    private static final int DATABASE_VERSION = 1;

    private static final String DB_CREATE = "create table "
            + TABLE_EMAILS + "("
            + COLUMN_ID  + " integer primary key autoincrement , "
            + COLUMN_FROM + " text, "
            + COLUMN_TO + " text, "
            + COLUMN_CC + " text"
            + COLUMN_BCC + " text"
            + COLUMN_DATE_TIME + " datetime"
            + COLUMN_SUBJECT + " text"
            + COLUMN_CONTENT + " text"
            + ")";

    //Potrebno je dodati konstruktor zbog pravilne inicijalizacije
    public ReviewerSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Prilikom kreiranja baze potrebno je da pozovemo odgovarajuce metode biblioteke
    //prilikom kreiranja moramo pozvati db.execSQL za svaku tabelu koju imamo
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    //kada zelimo da izmenomo tabele, moramo pozvati drop table za sve tabele koje imamo
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMAILS);
        onCreate(db);
    }
}
