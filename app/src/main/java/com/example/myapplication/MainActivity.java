package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button contactButton = findViewById(R.id.contact_management_button);
        Button webSearchButton = findViewById(R.id.web_search_button);
        Button phoneStatusButton = findViewById(R.id.phone_status_button);
        Button aboutAuthorButton = findViewById(R.id.about_author_button);

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactManagementActivity.class);
                startActivity(intent);
            }
        });

        webSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebSearchActivity.class);
                startActivity(intent);
            }
        });

        phoneStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhoneStatusActivity.class);
                startActivity(intent);
            }
        });

        aboutAuthorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutAuthorActivity.class);
                startActivity(intent);
            }
        });
    }
}
