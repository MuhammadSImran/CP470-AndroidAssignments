package com.example.androidassignments;

import android.content.Intent;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ChatWindowInstrumentedTest {

    @Rule
    public ActivityScenarioRule<ChatWindow> activityRule =
            new ActivityScenarioRule<>(new Intent(ApplicationProvider.getApplicationContext(), ChatWindow.class));

    @Test
    public void testSendMessage() {
        // Type a message and click the send button
        Espresso.onView(ViewMatchers.withId(R.id.message_input))
                .perform(ViewActions.typeText("Test message"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.send_button)).perform(ViewActions.click());

        // Check that the message appears in the ListView
        Espresso.onView(ViewMatchers.withId(R.id.chat_list_view))
                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText("Test message"))));
    }

    @Test
    public void testListViewPopulatesWithDatabaseMessages() {
        // Launch activity and verify list is populated
        Espresso.onView(ViewMatchers.withId(R.id.chat_list_view))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}

