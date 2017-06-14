package edu.umd.cs.agileandroid.service.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Beijie on 4/12/2017.
 */

public class AgileAndroidDbHelper extends SQLiteOpenHelper {

    public AgileAndroidDbHelper(Context context) {
        super(context, "agile_android.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + AgileAndroidDbSchema.StoryTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                AgileAndroidDbSchema.StoryTable.Columns.ID + ", " +
                AgileAndroidDbSchema.StoryTable.Columns.SUMMARY + ", " +
                AgileAndroidDbSchema.StoryTable.Columns.ACCEPTANCE_CRITERIA + ", " +
                AgileAndroidDbSchema.StoryTable.Columns.STORY_POINTS + ", " +
                AgileAndroidDbSchema.StoryTable.Columns.PRIORITY + ", " +
                AgileAndroidDbSchema.StoryTable.Columns.STATUS + ", " +
                AgileAndroidDbSchema.StoryTable.Columns.TIME_CREATED + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //and leave it blank
    }
}
