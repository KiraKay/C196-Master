package com.courseproject.utils;

import android.content.Context;

import androidx.annotation.Nullable;

import com.courseproject.database.AssessmentDatabase;
import com.courseproject.database.CourseDatabase;
import com.courseproject.database.TermDatabase;
import com.courseproject.model.Assessment;
import com.courseproject.model.Course;
import com.courseproject.model.Term;

import java.util.List;
import java.util.Map;

public class Utilities {

    public static Map<Integer, Term> getCompleteTerms(@Nullable Context context) {

        Map<Integer, Term> terms = (new TermDatabase(context)).get();
        Map<Integer, Course> courses = (new CourseDatabase(context)).get();
        Map<Integer, Assessment> assessments = (new AssessmentDatabase(context)).get();

        for (Assessment each: assessments.values()) {
            Course course = courses.get(each.getCourseId());
            if (course != null) {
                course.getAssessmentList().put(each.getId(), each);
            }
        }

        for (Course course: courses.values()) {
            Term term = terms.get(course.getTermId());
            if (term != null) {
                term.getCourseList().put(course.getCourseId(), course);
            }
        }

        return terms;

    }

}
