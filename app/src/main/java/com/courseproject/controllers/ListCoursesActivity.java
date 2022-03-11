package com.courseproject.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.courseproject.R;
import com.courseproject.model.Course;
import com.courseproject.model.Term;
import com.courseproject.utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListCoursesActivity extends AppCompatActivity {

    private ListView courseList;
    private Map<Integer, Term> terms;
    private List<Course> courseData;
    private Term term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_courses);

        courseList = findViewById(R.id.courseList);

        term = (Term) getIntent().getSerializableExtra("term");

        (findViewById(R.id.addCourseBTN)).setOnClickListener(each -> {

            Intent intent = new Intent(ListCoursesActivity.this,
                    AddCourseActivity.class);
            intent.putExtra("term", term);
            startActivity(intent);

        });
        updateUI();

        courseList.setOnItemClickListener((adapterView, view, position, l) -> {

            Course course = courseData.get(position);
            Intent intent = new Intent(ListCoursesActivity.this, DetailedCourseActivity.class);
            intent.putExtra("term", term);
            intent.putExtra("course", course);
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
        this.courseData = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        for (Course each: terms.get(term.getId()).getCourseList().values()) {
            names.add(each.getCourseTitle());
            courseData.add(each);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        courseList.setAdapter(adapter);

    }

}