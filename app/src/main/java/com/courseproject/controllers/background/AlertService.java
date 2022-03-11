package com.courseproject.controllers.background;

import static java.time.LocalDate.*;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.courseproject.R;
import com.courseproject.database.AssessmentDatabase;
import com.courseproject.database.CourseDatabase;
import com.courseproject.model.Assessment;
import com.courseproject.model.Course;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class AlertService extends IntentService {

    public AlertService() {

        super("AlertService");

    }

    // Starting the alert service.
    public static void startAlertService(Context context) {

        Intent intent = new Intent(context, AlertService.class);
        context.startService(intent);

    }

    // is service running
    public static boolean isAlertServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (AlertService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {

            AssessmentDatabase assDatabase = new AssessmentDatabase(this);
            CourseDatabase courseDatabase = new CourseDatabase(this);

            new Thread(() -> {

                while (true) {

                    Map<Integer, Assessment> assessments = assDatabase.get();
                    Map<Integer, Course> courses = courseDatabase.get();


                    LocalDateTime time = LocalDateTime.now();
                    LocalDate date = time.toLocalDate();

                    if (time.getHour() <= 22 ) {
                                 //Alerts for assessments are not triggering
                        for (Assessment each: assessments.values()) {
                            if (each.getEndDate().isEqual(date)){
                                generateNotification("Assessment Alert",
                                        "'" + each.getTitle() + "' has a deadline of today.");
                            }
                        }
                                  //This part is working for course alert
                        for (Course each: courses.values()) {
                            if (each.getStart().isEqual(date)){
                                generateNotification("Course Alert",
                                        "'" + each.getCourseTitle() + "' course started today.");
                            } else if (each.getEnd().isEqual(date)) {
                                generateNotification("Course Alert",
                                        "'" + each.getCourseTitle() + "' course will end today.");
                            }
                        }

                    }

                    try {
                        Thread.sleep(50000);
                    } catch (InterruptedException e) {}

                }

            }).start();


        }
    }

    protected void generateNotification(String title, String content) {

        final Intent emptyIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, "Course Console")
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        NotificationManagerCompat compat = NotificationManagerCompat.from(this);
        compat.notify(1, mBuilder.build());

    }

}