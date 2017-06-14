package edu.umd.cs.agileandroid.service.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Beijie on 4/12/2017.
 */

public class AgileAndroidDbSchema extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public AgileAndroidDbSchema(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = this.getWritableDatabase();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            getWritableDatabase();
        }
    }

    public static final class StoryTable {
        public static final String NAME = "agileAndroidTable";
        public static final class Columns {
            public static final String ID = "id",
                    STORY_POINTS = "story_points",
                    SUMMARY = "summary",
                    ACCEPTANCE_CRITERIA = "acceptance_criteria",
                    PRIORITY = "priority",
                    STATUS = "status",
                    TIME_CREATED = "time_created";


        }
    }
}
