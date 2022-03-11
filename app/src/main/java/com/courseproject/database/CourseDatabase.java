package com.courseproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.courseproject.model.Assessment;
import com.courseproject.model.Course;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "coursesdatabase";
    private static final String TABLE_NAME = "coursetb";
    private static final String ID = "cid";
    private static final String TITLE = "ctitle";
    private static final String START = "cstartdate";
    private static final String END = "cenddate";
    private static final String STATUS = "cstatus";
    private static final String NAME = "ciname";
    private static final String PHONE = "ciphone";
    private static final String EMAIL = "ciemail";
    private static final String NOTE = "cinote";
    private static final String TERM = "atermid";
    private SQLiteDatabase sqLiteDatabase;

    public CourseDatabase(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 4);
        sqLiteDatabase = getReadableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TITLE + " VARCHAR(125),"
                + START + " LONG,"
                + END + " LONG,"
                + STATUS + " VARCHAR(125),"
                + NAME + " VARCHAR(125),"
                + PHONE + " VARCHAR(125),"
                + EMAIL + " VARCHAR(125),"
                + NOTE + " VARCHAR(255),"
                + TERM + " INTEGER"
                + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void add(Course data) {

        ContentValues cv = new ContentValues();
        cv.put(TITLE, data.getCourseTitle());
        cv.put(START, data.getStart().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        cv.put(END, data.getEnd().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        cv.put(STATUS, data.getStatus());
        cv.put(NAME, data.getInstructorName());
        cv.put(PHONE, data.getInstructorPhone());
        cv.put(EMAIL, data.getInstructorEmail());
        cv.put(NOTE, data.getNote());
        cv.put(TERM, data.getTermId());
        sqLiteDatabase.insert(TABLE_NAME, null, cv);

    }

    public void update(Course data) {

        remove(data);
        ContentValues cv = new ContentValues();
        cv.put(ID, data.getCourseId());
        cv.put(TITLE, data.getCourseTitle());
        cv.put(START, data.getStart().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        cv.put(END, data.getEnd().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        cv.put(STATUS, data.getStatus());
        cv.put(NAME, data.getInstructorName());
        cv.put(PHONE, data.getInstructorPhone());
        cv.put(EMAIL, data.getInstructorEmail());
        cv.put(NOTE, data.getNote());
        cv.put(TERM, data.getTermId());
        sqLiteDatabase.insert(TABLE_NAME, null, cv);

    }

    public Map<Integer, Course> get() {

        Map<Integer, Course> list = new HashMap<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Course each = new Course(cursor);
                list.put(each.getCourseId(), each);
            } while (cursor.moveToNext());
        }
        return list;

    }

    public void remove(int id) {

        sqLiteDatabase.delete(TABLE_NAME, ID + "=" + id, null);

    }

    public void remove(Course data) {

        if (data != null) {
            remove(data.getCourseId());
        }

    }

}
