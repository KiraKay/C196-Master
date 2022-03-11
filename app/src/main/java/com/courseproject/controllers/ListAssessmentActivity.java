package com.courseproject.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.courseproject.R;
import com.courseproject.model.Assessment;
import com.courseproject.model.Course;
import com.courseproject.model.Term;
import com.courseproject.utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListAssessmentActivity extends AppCompatActivity {

    private ListView assessmentList;
    private Map<Integer, Term> terms;
    private List<Assessment> assessmentData;
    private Term term;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_assessment);

        assessmentList = findViewById(R.id.assessnebtList);

        term = (Term) getIntent().getSerializableExtra("term");
        course = (Course) getIntent().getSerializableExtra("course");

        (findViewById(R.id.addAssessmentBTN)).setOnClickListener(each -> {

            Intent intent = new Intent(ListAssessmentActivity.this,
                    AddAssessmentActivity.class);
            intent.putExtra("term", term);
            intent.putExtra("course", course);
            startActivity(intent);

        });
        updateUI();

        assessmentList.setOnItemClickListener((adapterView, view, position, l) -> {

            Assessment assessment = assessmentData.get(position);
            Intent intent = new Intent(ListAssessmentActivity.this,
                    DetailedAssessmentActivity.class);
            intent.putExtra("term", term);
            intent.putExtra("course", course);
            intent.putExtra("assessment", assessment);
            startActivity(intent);

        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        updateUI();

    }

    private void updateUI() {

        terms = Utilities.getCompleteTerms(this);
        this.assessmentData = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        for (Assessment each: terms.get(term.getId()).getCourseList().get(course.getCourseId()).getAssessmentList().values()) {
            names.add(each.getTitle());
            assessmentData.add(each);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        assessmentList.setAdapter(adapter);

    }

}