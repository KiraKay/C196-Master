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
import com.courseproject.database.CourseDatabase;
import com.courseproject.database.TermDatabase;
import com.courseproject.model.Course;
import com.courseproject.model.Term;

import java.time.LocalDate;

public class AddCourseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Term term;
    private EditText startDate, endDate, title, status, name, phone, email, note;
    private Button startDateBTN, endDateBTN, insertCourseBTN;
    private DatePickerDialog datePickerDialog;
    private boolean flag = true;
    private LocalDate start, end;
    private Course intentCourse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        term = (Term) getIntent().getSerializableExtra("term");
        intentCourse = (Course) getIntent().getSerializableExtra("course");

        title = findViewById(R.id.courseTitle);
        startDate = findViewById(R.id.courseStartDate);
        endDate = findViewById(R.id.courseEndDate);
        startDateBTN = findViewById(R.id.courseStartDateBTN);
        endDateBTN = findViewById(R.id.courseEndDateBTN);
        insertCourseBTN = findViewById(R.id.insertCourseBTN);
        status = findViewById(R.id.courseStatus);
        name = findViewById(R.id.courseName);
        phone = findViewById(R.id.coursePhone);
        email = findViewById(R.id.courseEmail);
        note = findViewById(R.id.courseNote);

        if (intentCourse != null) {
            title.setText(intentCourse.getCourseTitle());
            startDate.setText(intentCourse.getStart().toString());
            endDate.setText(intentCourse.getEnd().toString());
            status.setText(intentCourse.getStatus());
            name.setText(intentCourse.getInstructorName());
            phone.setText(intentCourse.getInstructorPhone());
            email.setText(intentCourse.getInstructorEmail());
            note.setText(intentCourse.getNote() == null ? "" : intentCourse.getNote());
            start = intentCourse.getStart();
            end = intentCourse.getEnd();
        }

        LocalDate now = LocalDate.now();
        datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,
                this, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        startDateBTN.setOnClickListener(each -> {
            flag = true;
            datePickerDialog.show();
        });

        endDateBTN.setOnClickListener(each -> {
            flag = false;
            datePickerDialog.show();
        });

        insertCourseBTN.setOnClickListener(each -> {

            String titleD = title.getText().toString();
            String statusD = status.getText().toString();
            String nameD = name.getText().toString();
            String phoneD = phone.getText().toString();
            String emailD = email.getText().toString();
            String noteD = note.getText().toString();
            if (titleD.isEmpty() || start == null || end == null ||
                statusD.isEmpty() || nameD.isEmpty() || phoneD.isEmpty() ||
                    emailD.isEmpty()) {
                Toast.makeText(getApplicationContext(), "No any field should be empty!",
                        Toast.LENGTH_LONG).show();
            } else {

                if (start.isAfter(end) || start.isEqual(end)) {
                    Toast.makeText(getApplicationContext(), "Dates should be valid!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (intentCourse != null) {
                    Course course = new Course(intentCourse.getCourseId(), titleD, start, end, statusD,
                            nameD, phoneD, emailD, noteD, intentCourse.getTermId());
                    (new CourseDatabase(this)).update(course);
                    Toast.makeText(getApplicationContext(), "Course is updated successfully!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Course course = new Course(titleD, start, end, statusD,
                            nameD, phoneD, emailD, noteD, term.getId());
                    (new CourseDatabase(this)).add(course);
                    Toast.makeText(getApplicationContext(), "Course is added successfully!",
                            Toast.LENGTH_LONG).show();
                }

            }

        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        if (flag) {
            start = LocalDate.of(year, month + 1, day);
            startDate.setText(start.toString());
        } else {
            end = LocalDate.of(year, month + 1, day);
            endDate.setText(end.toString());
        }

    }
}