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
import com.courseproject.model.Term;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TermDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "termdatabase";
    private static final String TABLE_NAME = "term";
    private static final String ID = "tid";
    private static final String NAME = "tname";
    private static final String START = "tstartdate";
    private static final String END = "tenddate";
    private SQLiteDatabase sqLiteDatabase;

    public TermDatabase(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 3);
        sqLiteDatabase = getReadableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME + " VARCHAR(125),"
                + START + " LONG,"
                + END + " LONG"
                + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void add(Term data) {

        ContentValues cv = new ContentValues();
        cv.put(NAME, data.getName());
        cv.put(START, data.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        cv.put(END, data.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        sqLiteDatabase.insert(TABLE_NAME, null, cv);

    }

    public void update(Term data) {

        remove(data);
        ContentValues cv = new ContentValues();
        cv.put(ID, data.getId());
        cv.put(NAME, data.getName());
        cv.put(START, data.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        cv.put(END, data.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        sqLiteDatabase.insert(TABLE_NAME, null, cv);

    }

    public Map<Integer, Term> get() {

        Map<Integer, Term> list = new HashMap<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Term each = new Term(cursor);
                list.put(each.getId(), each);
            } while (cursor.moveToNext());
        }
        Log.d("TestingDB", "Read..");
        return list;

    }

    public void remove(int id) {

        sqLiteDatabase.delete(TABLE_NAME, ID + "=" + id, null);

    }

    public void remove(Term data) {

        if (data != null) {
            remove(data.getId());
        }

    }

}
