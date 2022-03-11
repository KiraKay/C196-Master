package com.courseproject.model;

import android.database.Cursor;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class Assessment implements Serializable {

    private int id;
    private String type;
    private String title;
    private LocalDate endDate;
    private int courseId;

    public Assessment(int id, String type, String title, LocalDate endDate, int courseId) {

        this.id = id;
        this.type = type;
        this.title = title;
        this.endDate = endDate;
        this.courseId = courseId;

    }

    public Assessment(String type, String title, LocalDate endDate, int courseId) {

        this.type = type;
        this.title = title;
        this.endDate = endDate;
        this.courseId = courseId;

    }

    public Assessment(Cursor cursor) {

        this(cursor.getInt(0), cursor.getString(1),
                cursor.getString(2),
                Instant.ofEpochMilli(cursor.getLong(3)).atZone(
                        ZoneId.systemDefault()).toLocalDate(),
                cursor.getInt(4));

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
