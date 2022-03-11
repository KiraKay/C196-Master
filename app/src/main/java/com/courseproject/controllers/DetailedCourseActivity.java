package com.courseproject.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.courseproject.R;
import com.courseproject.database.CourseDatabase;
import com.courseproject.database.TermDatabase;
import com.courseproject.model.Course;
import com.courseproject.model.Term;

public class DetailedCourseActivity extends AppCompatActivity {

    private EditText startDate, endDate, title, status, name, phone, email, note;
    private Term term;
    private Course intentCourse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_course);

        term = (Term) getIntent().getSerializableExtra("term");
        intentCourse = (Course) getIntent().getSerializableExtra("course");

        title = findViewById(R.id.courseTitle);
        startDate = findViewById(R.id.courseStartDate);
        endDate = findViewById(R.id.courseEndDate);
        status = findViewById(R.id.courseStatus);
        name = findViewById(R.id.courseName);
        phone = findViewById(R.id.coursePhone);
        email = findViewById(R.id.courseEmail);
        note = findViewById(R.id.courseNote);

        title.setText(intentCourse.getCourseTitle());
        startDate.setText(intentCourse.getStart().toString());
        endDate.setText(intentCourse.getEnd().toString());
        status.setText(intentCourse.getStatus());
        name.setText(intentCourse.getInstructorName());
        phone.setText(intentCourse.getInstructorPhone());
        email.setText(intentCourse.getInstructorEmail());
        note.setText(intentCourse.getNote());

        (findViewById(R.id.deleteCourseBTN)).setOnClickListener(each -> {

            if (intentCourse.getAssessmentList().isEmpty()) {
                (new CourseDatabase(this)).remove(intentCourse.getCourseId());
                Toast.makeText(getApplicationContext(), "Deleted Successfully!",
                        Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "First delete all assessments in this course",
                        Toast.LENGTH_LONG).show();
            }

        });

        (findViewById(R.id.editCourseBTN)).setOnClickListener(each -> {

            Intent intent = new Intent(DetailedCourseActivity.this, AddCourseActivity.class);
            intent.putExtra("term", term);
            intent.putExtra("course", intentCourse);
            startActivity(intent);
            finish();

        });

        (findViewById(R.id.listAssessmentBTN)).setOnClickListener(each -> {

            Intent intent = new Intent(DetailedCourseActivity.this, ListAssessmentActivity.class);
            intent.putExtra("term", term);
            intent.putExtra("course", intentCourse);
            startActivity(intent);
            finish();

        });

        (findViewById(R.id.shareCourseBTN)).setOnClickListener(each -> {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, intentCourse.toString());
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, "Course Details");
            startActivity(shareIntent);

        });


    }
}