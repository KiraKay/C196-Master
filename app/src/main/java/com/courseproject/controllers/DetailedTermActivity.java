package com.courseproject.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.courseproject.R;
import com.courseproject.database.TermDatabase;
import com.courseproject.model.Term;

public class DetailedTermActivity extends AppCompatActivity {

    private EditText startDate, endDate, title;
    private Button listCoursesBTN;
    private Term term;
    private TextView errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_term);

        term = (Term) getIntent().getSerializableExtra("term");

        /*

        Programmatically TextView

         */
        LinearLayout layout = findViewById(R.id.detailedLayout);
        errorView = new TextView(this);
        errorView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        errorView.setGravity(Gravity.CENTER);
        errorView.setTextColor(Color.RED);
        errorView.setText("First delete all courses in this term.");
        errorView.setVisibility(View.GONE);
        if (layout != null) {
            layout.addView(errorView);
        }

        title = findViewById(R.id.termTitle);
        startDate = findViewById(R.id.termStartDate);
        endDate = findViewById(R.id.termEndDate);
        listCoursesBTN = findViewById(R.id.listCourseBTN);

        title.setText(term.getName());
        startDate.setText(term.getStartDate().toString());
        endDate.setText(term.getEndDate().toString());

        listCoursesBTN.setOnClickListener(each -> {

            errorView.setVisibility(View.GONE);
            Intent intent = new Intent(DetailedTermActivity.this, ListCoursesActivity.class);
            intent.putExtra("term", term);
            startActivity(intent);

        });

        (findViewById(R.id.deleteTermBTN)).setOnClickListener(each -> {

            if (term.getCourseList().isEmpty()) {
                (new TermDatabase(this)).remove(term.getId());
                errorView.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Deleted Successfully!",
                        Toast.LENGTH_LONG).show();
                finish();
            } else {
                errorView.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "First delete all courses in this term.",
                        Toast.LENGTH_LONG).show();
            }

        });

    }
}