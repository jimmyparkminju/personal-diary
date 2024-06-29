package com.example.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {
    private EditText nameEditText, phoneEditText, emailEditText, companyEditText, positionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        nameEditText = findViewById(R.id.name_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        companyEditText = findViewById(R.id.company_edit_text);
        positionEditText = findViewById(R.id.position_edit_text);

        Button saveButton = findViewById(R.id.save_button);
        Button cancelButton = findViewById(R.id.cancel_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveContact() {
        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String company = companyEditText.getText().toString();
        String position = positionEditText.getText().toString();

        ContactDbHelper dbHelper = new ContactDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContactContract.ContactEntry.COLUMN_NAME, name);
        values.put(ContactContract.ContactEntry.COLUMN_PHONE, phone);
        values.put(ContactContract.ContactEntry.COLUMN_EMAIL, email);
        values.put(ContactContract.ContactEntry.COLUMN_COMPANY, company);
        values.put(ContactContract.ContactEntry.COLUMN_POSITION, position);

        long newRowId = db.insert(ContactContract.ContactEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            finish();
        }
    }
}
