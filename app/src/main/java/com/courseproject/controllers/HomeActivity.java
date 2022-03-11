package com.courseproject.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.courseproject.R;
import com.courseproject.controllers.background.AlertService;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Course Console", "Course Console", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        if (!AlertService.isAlertServiceRunning(this)) {
            AlertService.startAlertService(this);
        }

        (findViewById(R.id.termsBTN)).setOnClickListener(each ->
                startActivity(new Intent(HomeActivity.this,
                        ListTermsActivity.class)));

    }
}