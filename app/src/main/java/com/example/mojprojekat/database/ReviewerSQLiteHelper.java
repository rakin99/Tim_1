package com.example.mojprojekat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReviewerSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_EMAILS = "email";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FROM = "_from";
    public static final String COLUMN_TO = "_to";
    public static final String COLUMN_CC = "cc";
    public static final String COLUMN_BCC = "bcc";
    public static final String COLUMN_DATE_TIME = "dateTime";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_UNREAD = "unread";
    public static final String COLUMN_ACTIVE = "active";
    public static final String COLUMN_ID_FOLDER="idFolder";

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_SMTP = "first";
    public static final String COLUMN_POP3_IMAP = "last";
    public static final String COLUMN_DISPLAY = "display";
    public static final String COLUMN_USERNAME = "email";
    public static final String COLUMN_PASSWORD = "password";

    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_Id = "contact_id";
    public static final String COLUMN_FIRST = "_first";
    public static final String COLUMN_LAST = "_last";
    public static final String COLUMN_EMAIL = "email";


    private static final String DATABASE_NAME = "emails.db";
    //i pocetnu verziju baze. Obicno krece od 1
    private static final int DATABASE_VERSION = 1;

    private static final String DB_CREATE_EMAILS = "create table "
            + TABLE_EMAILS + "("
            + COLUMN_ID  + " integer primary key, "
            + COLUMN_FROM + " text, "
            + COLUMN_TO + " text, "
            + COLUMN_CC + " text,"
            + COLUMN_BCC + " text,"
            + COLUMN_DATE_TIME + " datetime,"
            + COLUMN_SUBJECT + " text,"
            + COLUMN_CONTENT + " text,"
            + COLUMN_UNREAD + " boolean,"
            + COLUMN_ACTIVE + " boolean,"
            + COLUMN_ID_FOLDER + " integer"
            + ")";

    private static final String DB_CREATE_ACCOUNT = "create table "
            + TABLE_USERS + "("
            + COLUMN_ID  + " integer primary key autoincrement , "
            + COLUMN_SMTP + " text, "
            + COLUMN_POP3_IMAP + " text, "
            + COLUMN_DISPLAY + " text,"
            + COLUMN_USERNAME + " text,"
            + COLUMN_PASSWORD + " text"
            + ")";

    private static final String DB_CREATE_CONTACTS = "create table "
            + TABLE_CONTACTS + "("
            + COLUMN_Id  + " integer primary key autoincrement , "
            + COLUMN_FIRST + " text, "
            + COLUMN_LAST + " text, "
            + COLUMN_EMAIL + " text"
            + ")";

    //Potrebno je dodati konstruktor zbog pravilne inicijalizacije
    public ReviewerSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Prilikom kreiranja baze potrebno je da pozovemo odgovarajuce metode biblioteke
    //prilikom kreiranja moramo pozvati db.execSQL za svaku tabelu koju imamo
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_EMAILS);
        db.execSQL(DB_CREATE_ACCOUNT);
        db.execSQL(DB_CREATE_CONTACTS);
    }

    //kada zelimo da izmenomo tabele, moramo pozvati drop table za sve tabele koje imamo
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMAILS+TABLE_USERS+TABLE_CONTACTS);
        onCreate(db);
    }
}
