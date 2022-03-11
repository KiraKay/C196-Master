package com.courseproject.model;

import android.database.Cursor;

import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Term implements Serializable {

    private int id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Map<Integer, Course> courseList;

    public Term(int id, String name, LocalDate startDate, LocalDate endDate) {

        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseList = new HashMap<>();

    }

    public Term(String name, LocalDate startDate, LocalDate endDate) {

        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseList = new HashMap<>();

    }

    public Term(Cursor cursor) {

        this(cursor.getInt(0), cursor.getString(1),
                Instant.ofEpochMilli(cursor.getLong(2)).atZone(
                        ZoneId.systemDefault()).toLocalDate(),
                Instant.ofEpochMilli(cursor.getLong(3)).atZone(
                        ZoneId.systemDefault()).toLocalDate());

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Map<Integer, Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(Map<Integer, Course> courseList) {
        this.courseList = courseList;
    }

}
