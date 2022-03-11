package com.courseproject.model;

import android.database.Cursor;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course implements Serializable {

    private int courseId;
    private String courseTitle;
    private LocalDate start;
    private LocalDate end;
    private String status;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private String note;
    private Map<Integer, Assessment> assessmentList;
    private int termId;

    public Course(int courseId, String courseTitle, LocalDate start, LocalDate end, String status, String instructorName, String instructorPhone, String instructorEmail,
                  String note, int termId) {

        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.start = start;
        this.end = end;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.assessmentList = new HashMap<>();
        this.note = note;
        this.termId = termId;

    }

    public Course(String courseTitle, LocalDate start, LocalDate end, String status, String instructorName, String instructorPhone, String instructorEmail, String note, int termId) {

        this.courseTitle = courseTitle;
        this.start = start;
        this.end = end;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.note = note;
        this.assessmentList = new HashMap<>();
        this.termId = termId;

    }

    public Course(Cursor cursor) {

        this(cursor.getInt(0), cursor.getString(1),
                Instant.ofEpochMilli(cursor.getLong(2)).atZone(
                        ZoneId.systemDefault()).toLocalDate(),
                Instant.ofEpochMilli(cursor.getLong(3)).atZone(
                        ZoneId.systemDefault()).toLocalDate(),
                cursor.getString(4), cursor.getString(5),
                cursor.getString(6), cursor.getString(7),
                cursor.getString(8),
                cursor.getInt(9));

    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public Map<Integer, Assessment> getAssessmentList() {
        return assessmentList;
    }

    public void setAssessmentList(Map<Integer, Assessment> assessmentList) {
        this.assessmentList = assessmentList;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Course Title: " + courseTitle  +
                ", Start Date: " + start.toString() +
                ", End Date: " + end.toString() +
                ", Status: " + status +
                ", Instructor Name: " + instructorName +
                ", Instructor Phone: " + instructorPhone +
                ", Instructor Email: " + instructorEmail +
                ", Optional Node: " + note;
    }
}
