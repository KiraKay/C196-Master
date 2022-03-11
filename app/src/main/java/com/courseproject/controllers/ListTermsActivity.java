package com.courseproject.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.courseproject.R;
import com.courseproject.database.TermDatabase;
import com.courseproject.model.Term;
import com.courseproject.utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListTermsActivity extends AppCompatActivity {

    private ListView termList;
    private Map<Integer, Term> terms;
    private List<Term> termsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_terms);

        termList = findViewById(R.id.termList);

        (findViewById(R.id.addTermBTN)).setOnClickListener(each ->
                startActivity(new Intent(ListTermsActivity.this,
                        AddTermActivity.class)));

        updateUI();

        termList.setOnItemClickListener((adapterView, view, position, l) -> {

            Term term = termsData.get(position);
            Term object = terms.get(term.getId());
            Intent intent = new Intent(ListTermsActivity.this, DetailedTermActivity.class);
            intent.putExtra("term", object);
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
        this.termsData = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        for (Term term: terms.values()) {
            names.add(term.getName());
            termsData.add(term);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        termList.setAdapter(adapter);

    }

}