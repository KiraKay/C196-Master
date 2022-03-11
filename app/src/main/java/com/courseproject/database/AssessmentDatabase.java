package com.courseproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.courseproject.model.Assessment;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssessmentDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "assdatabase";
    private static final String TABLE_NAME = "assessment";
    private static final String ID = "aid";
    private static final String TYPE = "atype";
    private static final String TITLE = "atitle";
    private static final String END = "aenddate";
    private static final String COURSE = "acourseid";
    private SQLiteDatabase sqLiteDatabase;

    public AssessmentDatabase(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 3);
        sqLiteDatabase = getReadableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TYPE + " VARCHAR(125),"
                + TITLE + " VARCHAR(255),"
                + END + " LONG,"
                + COURSE + " INTEGER"
                + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void add(Assessment data) {

        ContentValues cv = new ContentValues();
        cv.put(TYPE, data.getType());
        cv.put(TITLE, data.getTitle());
        cv.put(END, data.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        cv.put(COURSE, data.getCourseId());
        sqLiteDatabase.insert(TABLE_NAME, null, cv);

    }

    public void update(Assessment data) {

        remove(data);
        ContentValues cv = new ContentValues();
        cv.put(ID, data.getId());
        cv.put(TYPE, data.getType());
        cv.put(TITLE, data.getTitle());
        cv.put(END, data.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        cv.put(COURSE, data.getCourseId());
        sqLiteDatabase.insert(TABLE_NAME, null, cv);

    }

    public Map<Integer, Assessment> get() {

        Map<Integer, Assessment> list = new HashMap<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Assessment each = new Assessment(cursor);
                list.put(each.getId(), each);
            } while (cursor.moveToNext());
        }
        return list;

    }


    public void remove(int id) {

        sqLiteDatabase.delete(TABLE_NAME, ID + "=" + id, null);

    }

    public void remove(Assessment assessment) {

        if (assessment != null) {
            remove(assessment.getId());
        }

    }

}
