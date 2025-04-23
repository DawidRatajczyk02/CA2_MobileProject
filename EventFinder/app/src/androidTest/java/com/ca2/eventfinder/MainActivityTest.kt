package com.ca2.eventfinder

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.PickerActions
import android.widget.DatePicker
import android.widget.TimePicker
import com.ca2.eventfinder.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    //RUN TEST MainActivityTest
    @Test
    fun testLanguageButton() {
        Thread.sleep(2000)
        onView(withId(R.id.languageButton)).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.recyclerView))
            .perform(
                androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        Thread.sleep(2000)
        onView(withId(R.id.detailButton)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.languageButton)).perform(click())
        onView(withId(R.id.languageButton)).perform(click())
    }

    @Test
    fun testAddEventButton() {
        Thread.sleep(2000)
        onView(withId(R.id.addEventButton)).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.inputTitle)).perform(typeText("Espresso Test Event"), closeSoftKeyboard())
        onView(withId(R.id.inputLocation)).perform(typeText("Dublin"), closeSoftKeyboard())
        onView(withId(R.id.inputDescription)).perform(typeText("Test description"), closeSoftKeyboard())
        onView(withId(R.id.inputCategory)).perform(typeText("Tech"), closeSoftKeyboard())
        onView(withId(R.id.inputPrice)).perform(typeText("20.0"), closeSoftKeyboard())

        onView(withId(R.id.inputDateTime)).perform(click())

        onView(isAssignableFrom(android.widget.DatePicker::class.java)).perform(PickerActions.setDate(2025, 4, 15))
        onView(withText("OK")).perform(click())

        onView(isAssignableFrom(android.widget.TimePicker::class.java)).perform(PickerActions.setTime(14, 30))
        onView(withText("OK")).perform(click())

        onView(withId(R.id.submitButton)).perform(click())
        Thread.sleep(1000)
        onView(withText("Espresso Test Event")).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.detailButton)).perform(click())
    }

    @Test
    fun testEditEventButton() {
        Thread.sleep(1000)
        onView(withText("Espresso Test Event")).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.editButton)).perform(click())

        onView(withId(R.id.titleEditText)).perform(replaceText("Latte Test Event"), closeSoftKeyboard())
        onView(withId(R.id.locationEditText)).perform(replaceText("Galway"), closeSoftKeyboard())
        onView(withId(R.id.descriptionEditText)).perform(replaceText("Test description for Latte"), closeSoftKeyboard())
        onView(withId(R.id.categoryEditText)).perform(replaceText("Technology"), closeSoftKeyboard())
        onView(withId(R.id.priceEditText)).perform(replaceText("30.0"), closeSoftKeyboard())

        onView(withId(R.id.dateEditText)).perform(click())
        onView(isAssignableFrom(android.widget.DatePicker::class.java)).perform(PickerActions.setDate(2025, 5, 20))
        onView(withText("OK")).perform(click())

        onView(isAssignableFrom(android.widget.TimePicker::class.java)).perform(PickerActions.setTime(14, 30))
        onView(withText("OK")).perform(click())

        onView(withId(R.id.saveButton)).perform(click())
        Thread.sleep(1000)
        onView(withText("Latte Test Event")).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.detailButton)).perform(click())
    }


    @Test
    fun testDeleteEventButton() {
        Thread.sleep(2000)
        onView(withText("Latte Test Event")).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.deleteButton)).perform(click())

        onView(withText(R.string.delete)).perform(click())

        Thread.sleep(1000)
    }
}
