package com.courseproject.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.courseproject.R;
import com.courseproject.database.AssessmentDatabase;
import com.courseproject.database.CourseDatabase;
import com.courseproject.model.Assessment;
import com.courseproject.model.Course;
import com.courseproject.model.Term;

import java.time.LocalDate;

public class DetailedAssessmentActivity extends AppCompatActivity {

    private EditText type, endDate, title;
    private LocalDate end;
    private Term term;
    private Course course;
    private Assessment intentAss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_assessment);

        term = (Term) getIntent().getSerializableExtra("term");
        course = (Course) getIntent().getSerializableExtra("course");
        intentAss = (Assessment) getIntent().getSerializableExtra("assessment");

        title = findViewById(R.id.assTitle);
        type = findViewById(R.id.assType);
        endDate = findViewById(R.id.assEndDate);

        title.setText(intentAss.getTitle());
        type.setText(intentAss.getType());
        endDate.setText(intentAss.getEndDate().toString());

        (findViewById(R.id.deleteAssessmentBTN)).setOnClickListener(each -> {

            (new AssessmentDatabase(this)).remove(intentAss.getId());
            Toast.makeText(getApplicationContext(), "Deleted Successfully!",
                    Toast.LENGTH_LONG).show();
            finish();

        });

        (findViewById(R.id.editAssessmentBTN)).setOnClickListener(each -> {

            Intent intent = new Intent(DetailedAssessmentActivity.this, AddAssessmentActivity.class);
            intent.putExtra("term", term);
            intent.putExtra("course", course);
            intent.putExtra("assessment", intentAss);
            startActivity(intent);
            finish();

        });

        (findViewById(R.id.shareBTN)).setOnClickListener(each -> {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Assessment Title: " + intentAss.getTitle() +
                    ", Type: " + intentAss.getType() + ", End Date: " + intentAss.getEndDate().toString());
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, "Assessment Details");
            startActivity(shareIntent);

        });

    }
}