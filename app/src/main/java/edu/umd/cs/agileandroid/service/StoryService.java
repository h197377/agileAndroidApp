package edu.umd.cs.agileandroid.service;


import java.util.List;

import edu.umd.cs.agileandroid.model.Story;

public interface StoryService {
    public void addStoryToBacklog(Story story);
    public Story getStoryById(String id);
    public List<Story> getAllStories();
    public List<Story> getCurrentSprintStories();
}
