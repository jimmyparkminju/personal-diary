package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ContactDetailActivity extends AppCompatActivity {
    private long contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        Intent intent = getIntent();
        contactId = intent.getLongExtra("contact_id", -1);
        String contactName = intent.getStringExtra("contact_name");
        String contactPhone = intent.getStringExtra("contact_phone");
        String contactEmail = intent.getStringExtra("contact_email");
        String contactCompany = intent.getStringExtra("contact_company");
        String contactPosition = intent.getStringExtra("contact_position");

        TextView nameTextView = findViewById(R.id.contact_name);
        TextView phoneTextView = findViewById(R.id.contact_phone);
        TextView emailTextView = findViewById(R.id.contact_email);
        TextView companyTextView = findViewById(R.id.contact_company);
        TextView positionTextView = findViewById(R.id.contact_position);

        nameTextView.setText(contactName);
        phoneTextView.setText(contactPhone);
        emailTextView.setText(contactEmail);
        companyTextView.setText(contactCompany);
        positionTextView.setText(contactPosition);

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button shareButton = findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareContact(contactName, contactPhone, contactEmail, contactCompany, contactPosition);
            }
        });

        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
    }

    private void shareContact(String name, String phone, String email, String company, String position) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBody = "이름: " + name + "\n" +
                "전화번호: " + phone + "\n" +
                "이메일: " + email + "\n" +
                "회사: " + company + "\n" +
                "직함: " + position;
        intent.putExtra(Intent.EXTRA_SUBJECT, "연락처 정보");
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent, "공유하기"));
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("연락처를 삭제하시겠습니까?");
        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteContact();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void deleteContact() {
        ContactDbHelper dbHelper = new ContactDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = ContactContract.ContactEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(contactId) };
        int deletedRows = db.delete(ContactContract.ContactEntry.TABLE_NAME, selection, selectionArgs);

        if (deletedRows > 0) {
            Toast.makeText(this, "연락처가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "연락처 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
