package edu.umd.cs.agileandroid.service.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.umd.cs.agileandroid.model.Story;
import edu.umd.cs.agileandroid.service.StoryService;

/**
 * Created by Beijie on 4/12/2017.
 */

public class SQLiteStoryService implements StoryService {
    private SQLiteDatabase db;

    public SQLiteStoryService(Context context) {
        AgileAndroidDbHelper ah = new AgileAndroidDbHelper(context);
        db = ah.getWritableDatabase();
    }

    protected SQLiteDatabase getDatabase() {
        return this.db;
    }

    //example delete from InfoTable where name = "ABC" and id = "23"
    //delete("name = ? AND id = ?" , new String[] {"ABC", "23"});
    @Override
    public void addStoryToBacklog(Story story) {
        ContentValues cv = getContentValues(story);
        List<Story> queryList = null;
        String id = cv.get(AgileAndroidDbSchema.StoryTable.Columns.ID).toString();
        queryList = queryStories("id = ?", new String[]{id}, null);
        if (queryList.size() < 1) {
            db.insert(AgileAndroidDbSchema.StoryTable.NAME, null, cv);
        } else {
            db.update(AgileAndroidDbSchema.StoryTable.NAME,cv, "id = ?", new String[]{id});
        }
    }

    private List<Story> queryStories(String whereClause, String[] whereArgs, String orderBy) {
        List<Story> answer = new ArrayList<Story>();
        Cursor c = getDatabase().query(AgileAndroidDbSchema.StoryTable.NAME,null, whereClause, whereArgs, null, null, orderBy);
        StoryCursorWrapper wrapper = new StoryCursorWrapper(c);

        try {
            wrapper.moveToFirst();
            while (!c.isAfterLast()) {
                answer.add(wrapper.getStory());
                wrapper.moveToNext();
            }
        } finally {
            wrapper.close();
        }

        return answer;
    }

    @Override
    public Story getStoryById(String id) {
        Story s = null;
        List<Story> ls;
        if (id == null) {
            return s;
        } else {
            ls = queryStories("id = ?", new String[] {id}, null);
            for (Story story: ls) {
                if (story.getId().equals(id)) {
                    return story;
                }
            }
        }
        return s;
    }

    @Override
    public List<Story> getAllStories() {
        List<Story> ls;
        ls = queryStories(null, null, null);
        return ls;
    }

    @Override
    public List<Story> getCurrentSprintStories() {
        List<Story> ls, answer;
        ls = queryStories(null, null, null);
        answer = new ArrayList<Story>();
        for (Story s: ls) {
            if (s.getPriority() == Story.Priority.CURRENT) {
                answer.add(s);
            }
        }
        return answer;
    }

    private static ContentValues getContentValues(Story story) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AgileAndroidDbSchema.StoryTable.Columns.ID, story.getId());
        contentValues.put(AgileAndroidDbSchema.StoryTable.Columns.SUMMARY, story.getSummary());
        contentValues.put(AgileAndroidDbSchema.StoryTable.Columns.ACCEPTANCE_CRITERIA, story.getAcceptanceCriteria());
        contentValues.put(AgileAndroidDbSchema.StoryTable.Columns.STORY_POINTS, story.getStoryPoints());
        contentValues.put(AgileAndroidDbSchema.StoryTable.Columns.PRIORITY, story.getPriority().toString());
        contentValues.put(AgileAndroidDbSchema.StoryTable.Columns.STATUS, story.getStatus().toString());
        contentValues.put(AgileAndroidDbSchema.StoryTable.Columns.TIME_CREATED, story.getTimeCreated().getTime());

        return contentValues;
    }

    public class StoryCursorWrapper extends CursorWrapper{

        /**
         * Creates a cursor wrapper.
         *
         * @param cursor The underlying cursor to wrap.
         */
        public StoryCursorWrapper(Cursor cursor) {
            super(cursor);
        }




        public Story getStory() {
            String id = getString(getColumnIndex(AgileAndroidDbSchema.StoryTable.Columns.ID));
            String summary = getString(getColumnIndex(AgileAndroidDbSchema.StoryTable.Columns.SUMMARY));
            String acceptanceCriteria = getString(getColumnIndex(AgileAndroidDbSchema.StoryTable.Columns.ACCEPTANCE_CRITERIA));
            double storyPoints = getDouble(getColumnIndex(AgileAndroidDbSchema.StoryTable.Columns.STORY_POINTS));
            String priority = getString(getColumnIndex(AgileAndroidDbSchema.StoryTable.Columns.PRIORITY));
            String status = getString(getColumnIndex(AgileAndroidDbSchema.StoryTable.Columns.STATUS));
            long timeCreated = getLong(getColumnIndex(AgileAndroidDbSchema.StoryTable.Columns.TIME_CREATED));

            Story s = new Story();
            s.setId(id);
            s.setSummary(summary);
            s.setAcceptanceCriteria(acceptanceCriteria);
            s.setStoryPoints(storyPoints);
            s.setPriority(Story.Priority.valueOf(priority));
            s.setStatus(Story.Status.valueOf(status));
            s.setTimeCreated(new Date(timeCreated));

            return s;
        }
    }
}
