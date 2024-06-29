package com.example.myapplication;

import android.provider.BaseColumns;

public final class ContactContract {
    private ContactContract() {}

    public static class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "contacts";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_COMPANY = "company";
        public static final String COLUMN_POSITION = "position";
    }
}
