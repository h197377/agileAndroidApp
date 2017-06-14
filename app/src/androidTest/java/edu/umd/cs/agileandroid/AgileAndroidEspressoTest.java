package edu.umd.cs.agileandroid;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.PositionAssertions.isAbove;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class AgileAndroidEspressoTest extends  BaseActivityEspressoTest{
    @Rule
    public ActivityTestRule<BacklogActivity> activityRule = new ActivityTestRule<BacklogActivity>(BacklogActivity.class);

    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("edu.umd.cs.agileandroid", appContext.getPackageName());
    }

    @Override
    public Activity getActivity() {
        return (Activity)activityRule.getActivity();
    }

//    @Test
//    public void testCreateStories(){
//        Espresso.closeSoftKeyboard();
//        //add story icon missing
//        onView(withId(R.id.menu_item_create_story)).check(matches(isDisplayed()));
//        onView(withId(R.id.menu_item_create_story)).perform(click());
//        //StoryActivity was not opened
//
//        assertEquals(getActivityInstance().getClass(),StoryActivity.class);
//        onView(withId(R.id.summary)).perform(typeText("story 1"));
//        onView(withId(R.id.criteria)).perform(typeText("s1"));
//        onView(withId(R.id.points)).perform(typeText("1"));
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.save_story_button)).perform(click());
//        //Story not added to backlog
//        onView(withText("story 1")).check(matches(isDisplayed()));
//        onView(withText("s1")).check(matches(isDisplayed()));
//        onView(withText("1.0")).check(matches(isDisplayed()));
//
//
//    }
//
//    @Test
//    public void testEditStory(){
//
//        onView(withText("story 1")).perform(click());
//        // Existing story’s data is incorrect in StoryActivity
//        onView(withText("story 1")).check(matches(isDisplayed()));
//        onView(withText("s1")).check(matches(isDisplayed()));
//        onView(withText("1.0")).check(matches(isDisplayed()));
//        onView(withId(R.id.radio_current)).check(matches(isNotChecked()));
//        onView(withId(R.id.radio_next)).check(matches(isNotChecked()));
//        onView(withId(R.id.radio_later)).check(matches(isChecked()));
//        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("To Do"))));
//
//        onView(withId(R.id.summary)).perform(clearText());
//        onView(withId(R.id.summary)).perform(typeText("story 2"));
//        onView(withId(R.id.criteria)).perform(clearText());
//        onView(withId(R.id.criteria)).perform(typeText("s2"));
//        onView(withId(R.id.points)).perform(clearText());
//        onView(withId(R.id.points)).perform((typeText("2")));
//        onView(withId(R.id.radio_next)).perform(click());
//        Espresso.closeSoftKeyboard();
//        // Changes not reflected in backlog
//
//        onView(withId(R.id.save_story_button)).perform(click());
//        onView(withText("story 2")).check(matches(isDisplayed()));
//        onView(withText("s2")).check(matches(isDisplayed()));
//        onView(withText("2.0")).check(matches(isDisplayed()));
//        onView(withText("NEXT")).check(matches(isDisplayed()));
//    }
//
//
//    @Test
//    public void testUiElements() {
//        onView(withId(R.id.menu_item_team_page)).perform(click());
//
//        Activity currentActivity = getActivityInstance();
//        assertTrue(currentActivity.getClass().isAssignableFrom(TeamActivity.class));
//
//        onView(withId(R.id.reminder)).check(matches(isDisplayed()));
//        onView(withId(R.id.close)).check(matches(isDisplayed()));
//        onView(withId(R.id.definition)).check(matches(isDisplayed()));
//
//        onView(withId(R.id.close)).perform(click());
//    }
//
//
//
//    @Test
//    public void testActiveSprint(){
//        //• only need to check one story per column
//        onView(withId(R.id.menu_item_create_story)).perform(click());
//        onView(withId(R.id.summary)).perform(typeText("story 3"));
//        onView(withId(R.id.criteria)).perform(typeText("s3"));
//        onView(withId(R.id.points)).perform(typeText("3"));
//        onView(withId(R.id.radio_current)).perform(click());
//        // No stories displayed
//
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.save_story_button)).perform(click());
//
//        onView(withId(R.id.menu_item_create_story)).perform(click());
//        onView(withId(R.id.summary)).perform(typeText("story 4"));
//        onView(withId(R.id.criteria)).perform(typeText("s4"));
//        onView(withId(R.id.points)).perform(typeText("4"));
//        onView(withId(R.id.radio_current)).perform(click());
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.spinner)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is("In Progress"))).perform(click());
//        onView(withId(R.id.save_story_button)).perform(click());
//
//        onView(withId(R.id.menu_item_create_story)).perform(click());
//        onView(withId(R.id.summary)).perform(typeText("story 5"));
//        onView(withId(R.id.criteria)).perform(typeText("test 5"));
//        onView(withId(R.id.points)).perform(typeText("5"));
//        onView(withId(R.id.radio_current)).perform(click());
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.spinner)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is("Done"))).perform(click());
//        onView(withId(R.id.save_story_button)).perform(click());
//
//        onView(withId(R.id.menu_item_active_sprint)).perform(click());
//
//        // Stories not displayed in correct column
//        onView(withText("Done")).check(isAbove(withText("story 5")));
//        onView(withText("In Progress")).check(isAbove(withText("story 4")));
//        onView(withText("To Do")).check(isAbove(withText("story 3")));
//
//    }
@Test
public void testUiElements() {
    // open create story screen
    onView(withId(R.id.menu_item_create_story)).perform(click());

    Espresso.closeSoftKeyboard();

    // summary label & field
    onView(withText(R.string.summary_label)).check(matches(isDisplayed()));
    onView(withId(R.id.summary)).check(matches(isDisplayed()));

    // criteria label & field
    onView(withText(R.string.criteria_label)).check(matches(isDisplayed()));
    onView(withId(R.id.criteria)).check(matches(isDisplayed()));

    // points label & field
    onView(withText(R.string.points_label)).check(matches(isDisplayed()));
    onView(withId(R.id.points)).check(matches(isDisplayed()));

    // priority label & radio group
    onView(withText(R.string.priority_label)).check(matches(isDisplayed()));
    onView(withId(R.id.radio_group)).check(matches(isDisplayed()));
    onView(withId(R.id.radio_current)).check(matches(isDisplayed()));
    onView(withId(R.id.radio_next)).check(matches(isDisplayed()));
    onView(withId(R.id.radio_later)).check(matches(isDisplayed()));

    // status label & spinner
    onView(withText(R.string.status_label)).check(matches(isDisplayed()));
    onView(withId(R.id.spinner)).check(matches(isDisplayed()));

    // attachment label & camera
    onView(withText(R.string.attachment_label)).check(matches(isDisplayed()));
    onView(withId(R.id.photo)).check(matches(isDisplayed()));
    onView(withId(R.id.camera)).check(matches(isDisplayed()));

    // save button
    onView(withId(R.id.save_story_button)).check(matches(isDisplayed()));
    onView(withText(R.string.save_button)).check(matches(isDisplayed()));

    // cancel button
    onView(withId(R.id.cancel_story_button)).check(matches(isDisplayed()));
    onView(withText(R.string.cancel_button)).check(matches(isDisplayed()));
}

}
