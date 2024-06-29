package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;

    public ContactDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_CONTACTS_TABLE = "CREATE TABLE " +
                ContactContract.ContactEntry.TABLE_NAME + " (" +
                ContactContract.ContactEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContactContract.ContactEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ContactContract.ContactEntry.COLUMN_PHONE + " TEXT, " +
                ContactContract.ContactEntry.COLUMN_EMAIL + " TEXT, " +
                ContactContract.ContactEntry.COLUMN_COMPANY + " TEXT, " +
                ContactContract.ContactEntry.COLUMN_POSITION + " TEXT" +
                ");";
        db.execSQL(SQL_CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ContactContract.ContactEntry.TABLE_NAME);
        onCreate(db);
    }
}
