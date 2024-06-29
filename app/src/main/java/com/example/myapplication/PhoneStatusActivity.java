package com.example.myapplication;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.List;

public class PhoneStatusActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_status);

        TextView statusTextView = findViewById(R.id.phone_status_text);
        String status = getPhoneStatus();
        statusTextView.setText(status);

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhoneStatusActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private String getPhoneStatus() {
        StringBuilder status = new StringBuilder();

        // 안드로이드 버전
        status.append("안드로이드 버전: ").append(android.os.Build.VERSION.RELEASE).append("\n");

        // WiFi 상태 확인
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConnected = wifiInfo != null && wifiInfo.isConnected();
        status.append("WIFI: ").append(isWifiConnected ? "ON" : "OFF").append("\n");

        // LTE 상태 확인
        NetworkInfo lteInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isLteConnected = lteInfo != null && lteInfo.isConnected();
        status.append("LTE: ").append(isLteConnected ? "ON" : "OFF").append("\n");

        // SDCARD 용량 확인
        File path = Environment.getExternalStorageDirectory();
        status.append("SDCARD 용량(전체용량/남은용량): ")
                .append(path.getTotalSpace() / (1024 * 1024 * 1024)).append("GB / ")
                .append(path.getFreeSpace() / (1024 * 1024 * 1024)).append("GB\n");

        // 설치된 앱 수 확인
        int installedAppCount = getPackageManager().getInstalledPackages(0).size();
        status.append("설치한 app 수: ").append(installedAppCount).append("\n");

        // 실제 동작 중인 앱 수 확인
        int runningAppCount = getRunningAppCount();
        status.append("동작 중인 app 수: ").append(runningAppCount).append("\n");

        return status.toString();
    }

    private int getRunningAppCount() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        return runningAppProcesses != null ? runningAppProcesses.size() : 0;
    }
}
