package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ContactManagementActivity extends AppCompatActivity {
    private List<Contact> contacts;
    private ArrayAdapter<Contact> adapter;
    private ContactDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_management);

        dbHelper = new ContactDbHelper(this);

        contacts = loadContactsFromDb();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);

        ListView listView = findViewById(R.id.contact_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Contact selectedContact = contacts.get(position);
            showContactOptionsDialog(selectedContact);
        });

        FloatingActionButton fab = findViewById(R.id.add_contact_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactManagementActivity.this, AddContactActivity.class);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactManagementActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showContactOptionsDialog(Contact contact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(contact.getName())
                .setItems(new String[]{"상세 보기", "삭제", "공유"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // 상세 보기
                                Intent intent = new Intent(ContactManagementActivity.this, ContactDetailActivity.class);
                                intent.putExtra("contact_id", contact.getId());
                                intent.putExtra("contact_name", contact.getName());
                                intent.putExtra("contact_phone", contact.getPhone());
                                intent.putExtra("contact_email", contact.getEmail());
                                intent.putExtra("contact_company", contact.getCompany());
                                intent.putExtra("contact_position", contact.getPosition());
                                startActivity(intent);
                                break;
                            case 1: // 삭제
                                deleteContact(contact);
                                break;
                            case 2: // 공유
                                shareContact(contact);
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    private void deleteContact(Contact contact) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = ContactContract.ContactEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(contact.getId()) };
        int deletedRows = db.delete(ContactContract.ContactEntry.TABLE_NAME, selection, selectionArgs);

        if (deletedRows > 0) {
            contacts.remove(contact);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "연락처가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "연락처 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareContact(Contact contact) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBody = "이름: " + contact.getName() + "\n" +
                "전화번호: " + contact.getPhone() + "\n" +
                "이메일: " + contact.getEmail() + "\n" +
                "회사: " + contact.getCompany() + "\n" +
                "직함: " + contact.getPosition();
        intent.putExtra(Intent.EXTRA_SUBJECT, "연락처 정보");
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent, "공유하기"));
    }

    private List<Contact> loadContactsFromDb() {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                ContactContract.ContactEntry._ID,
                ContactContract.ContactEntry.COLUMN_NAME,
                ContactContract.ContactEntry.COLUMN_PHONE,
                ContactContract.ContactEntry.COLUMN_EMAIL,
                ContactContract.ContactEntry.COLUMN_COMPANY,
                ContactContract.ContactEntry.COLUMN_POSITION
        };

        Cursor cursor = db.query(
                ContactContract.ContactEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(ContactContract.ContactEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_PHONE));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_EMAIL));
            String company = cursor.getString(cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_COMPANY));
            String position = cursor.getString(cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_POSITION));
            contactList.add(new Contact(id, name, phone, email, company, position));
        }
        cursor.close();
        return contactList;
    }
}
