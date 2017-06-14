package edu.umd.cs.agileandroid;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import edu.umd.cs.agileandroid.model.Story;
import edu.umd.cs.agileandroid.service.impl.InMemoryStoryService;

import static junit.framework.Assert.assertEquals;

public class InMemoryStoryServiceTest {
    //variables for testing
    private InMemoryStoryService service = null;
    private Story story1, story2, story3, story4;

    @Before
    public void setup(){
        service = new InMemoryStoryService(null);
        story1 = new Story();
        story2 = new Story();
        story3 = new Story();
        story4 = new Story();
    }

    @After
    public void close(){
        service = null;
        story1 = null;
        story2 = null;
        story3 = null;
        story4 = null;
    }

    @Test
    public void testAddStoryToBacklog(){
        // Adding story does not increase backlog size
        service.addStoryToBacklog(story1);
        assertEquals(1, service.getAllStories().size());

        // Story with same ID added to backlog again, should still = 1
        service.addStoryToBacklog(story1);
        assertEquals(1, service.getAllStories().size());
    }

    @Test
    public void testGetStoryById(){
        // Does not find story with ID in backlog
        service.addStoryToBacklog(story1);
        // Finds story with different ID
        service.addStoryToBacklog(story2);
        service.addStoryToBacklog(story3);
        service.addStoryToBacklog(story4);
        assertEquals(service.getStoryById(story1.getId()),story1);
        assertEquals(service.getStoryById(story2.getId()),story2);
        assertEquals(service.getAllStories().get(2).getId(),story3.getId());
        assertEquals(service.getAllStories().get(3).getId(),story4.getId());
    }



    @Test
    public void testGetAllStories(){
        service.addStoryToBacklog(story3);
        service.addStoryToBacklog(story2);
        service.addStoryToBacklog(story4);

        service.addStoryToBacklog(story1);
        //Does not return entire backlog
        assertEquals(service.getCurrentSprintStories().size(),0);
        assertEquals(service.getAllStories().size(),4);
        //Stories returned not in correct sort order
        assertEquals(service.getAllStories().get(3),story1);
        assertEquals(service.getAllStories().get(2),story4);
        assertEquals(service.getAllStories().get(1),story2);
        assertEquals(service.getAllStories().get(0),story3);
    }


    @Test
    public void testGetCurrentSprintStories(){
        story1.setPriorityCurrent();
        story2.setPriorityNext();
        story3.setPriorityLater();
        story4.setPriorityLater();
        // Returns stories with next/later priority
        service.addStoryToBacklog(story3);
        service.addStoryToBacklog(story4);
        service.addStoryToBacklog(story1);
        service.addStoryToBacklog(story2);


        assertEquals(service.getAllStories().size(),4);
        // Returns subset of all current priority stories
        assertEquals(service.getCurrentSprintStories().size(),1);
        assertEquals(service.getCurrentSprintStories().get(0).getId(),story1.getId());

    }
}


