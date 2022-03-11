package com.courseproject.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.courseproject.R;
import com.courseproject.database.AssessmentDatabase;
import com.courseproject.database.TermDatabase;
import com.courseproject.model.Assessment;
import com.courseproject.model.Course;
import com.courseproject.model.Term;

import java.time.LocalDate;

public class AddAssessmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText type, endDate, title;
    private Button endDateBTN, insertAssessmentBTN;
    private DatePickerDialog datePickerDialog;
    private LocalDate end;
    private Term term;
    private Course course;
    private Assessment intentAss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        term = (Term) getIntent().getSerializableExtra("term");
        course = (Course) getIntent().getSerializableExtra("course");
        intentAss = (Assessment) getIntent().getSerializableExtra("assessment");

        title = findViewById(R.id.assTitle);
        type = findViewById(R.id.assType);
        endDate = findViewById(R.id.assEndDate);
        endDateBTN = findViewById(R.id.assEndDateBTN);
        insertAssessmentBTN = findViewById(R.id.insertAssessmentBTN);

        if (intentAss != null) {
            title.setText(intentAss.getTitle());
            type.setText(intentAss.getType());
            endDate.setText(intentAss.getEndDate().toString());
            end = intentAss.getEndDate();
        }

        LocalDate now = LocalDate.now();
        datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,
                this, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        endDateBTN.setOnClickListener(each -> {
            datePickerDialog.show();
        });

        insertAssessmentBTN.setOnClickListener(each -> {

            String titleD = title.getText().toString();
            String typeD = type.getText().toString();
            if (titleD.isEmpty() || end == null || typeD.isEmpty()) {
                Toast.makeText(getApplicationContext(), "No any field should be empty!",
                        Toast.LENGTH_LONG).show();
            } else {

                if (intentAss != null) {
                    Assessment ass = new Assessment(intentAss.getId(), titleD, typeD, end, intentAss.getCourseId());
                    (new AssessmentDatabase(this)).update(ass);
                    Toast.makeText(getApplicationContext(), "Assessment is updated successfully!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Assessment ass = new Assessment(titleD, typeD, end, course.getCourseId());
                    (new AssessmentDatabase(this)).add(ass);
                    Toast.makeText(getApplicationContext(), "Assessment is added successfully!",
                            Toast.LENGTH_LONG).show();
                }

            }

        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        end = LocalDate.of(year, month + 1, day);
        endDate.setText(end.toString());

    }

}