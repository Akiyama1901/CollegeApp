package com.example.collegeapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CourseDbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "courseSQLite.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME_COURSE = "course";
    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME_COURSE + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "weekDay INTEGER, " +
            "courseName TEXT, " +
            "section INTEGER, " +
            "sectionSpan INTEGER, " +
            "classRoom TEXT, " +
            "courseFlag INTEGER)";

    public CourseDbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public long insertCourse(Course course) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("weekDay", course.getWeekDay());
        values.put("courseName", course.getCourseName());
        values.put("section", course.getSection());
        values.put("sectionSpan", course.getSectionSpan());
        values.put("classRoom", course.getClassRoom());
        values.put("courseFlag", course.getCourseFlag());
        return db.insert(TABLE_NAME_COURSE, null, values);
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_COURSE, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                int weekDay = cursor.getInt(cursor.getColumnIndex("weekDay"));
                String courseName = cursor.getString(cursor.getColumnIndex("courseName"));
                int section = cursor.getInt(cursor.getColumnIndex("section"));
                int sectionSpan = cursor.getInt(cursor.getColumnIndex("sectionSpan"));
                String classRoom = cursor.getString(cursor.getColumnIndex("classRoom"));
                int courseFlag = cursor.getInt(cursor.getColumnIndex("courseFlag"));
                courses.add(new Course(id, courseName, section, sectionSpan, weekDay, classRoom, courseFlag));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return courses;
    }

}