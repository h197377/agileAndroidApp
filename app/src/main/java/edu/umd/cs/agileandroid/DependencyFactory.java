package edu.umd.cs.agileandroid;


import android.content.Context;

import edu.umd.cs.agileandroid.service.StoryService;
import edu.umd.cs.agileandroid.service.TeamService;
import edu.umd.cs.agileandroid.service.impl.SQLiteStoryService;
import edu.umd.cs.agileandroid.service.impl.WebTeamService;

public class DependencyFactory {
    private static StoryService storyService;
    private static TeamService teamService;

    public static StoryService getStoryService(Context context) {
        if (storyService == null) {
            //storyService = new InMemoryStoryService(context);
            storyService = new SQLiteStoryService(context);
        }
        return storyService;
    }

    public static TeamService getTeamService(Context context) {
        if (teamService == null) {
            teamService = new WebTeamService();
        }
        return teamService;
    }
}
