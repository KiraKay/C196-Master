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
import com.courseproject.database.TermDatabase;
import com.courseproject.model.Term;

import java.time.LocalDate;

public class AddTermActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText startDate, endDate, title;
    private Button startDateBTN, endDateBTN, insertTermBTN;
    private DatePickerDialog datePickerDialog;
    private boolean flag = true;
    private LocalDate start, end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        title = findViewById(R.id.termTitle);
        startDate = findViewById(R.id.termStartDate);
        endDate = findViewById(R.id.termEndDate);
        startDateBTN = findViewById(R.id.termStartDateBTN);
        endDateBTN = findViewById(R.id.termEndDateBTN);
        insertTermBTN = findViewById(R.id.insertTermBTN);

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

        insertTermBTN.setOnClickListener(each -> {

            String titleD = title.getText().toString();
            if (titleD.isEmpty() || start == null || end == null) {
                Toast.makeText(getApplicationContext(), "No any field should be empty!",
                        Toast.LENGTH_LONG).show();
            } else {

                if (start.isAfter(end) || start.isEqual(end)) {
                    Toast.makeText(getApplicationContext(), "Dates should be valid!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Term term = new Term(titleD, start, end);
                (new TermDatabase(this)).add(term);
                Toast.makeText(getApplicationContext(), "Term is added successfully!",
                        Toast.LENGTH_LONG).show();

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