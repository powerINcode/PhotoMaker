package com.example.photomaker

import android.provider.MediaStore
import android.support.test.uiautomator.UiDevice
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.times
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.feature.make.photo.impl.ui.MakePhotoActivity
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ApplicationInstrumentalTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(PhotoGalleryActivity::class.java)

    @Test
    fun checkNavigationToMakePhoto() {
        Intents.init()

        onView(withId(R.id.makeNewPhotoButton)).perform(click())

        intended(hasComponent(MakePhotoActivity::class.java.name))
    }

    @Test
    fun checkNavigationToCreatePhotoRightAfterLaunch() {
        Intents.init()

        onView(withId(R.id.makeNewPhotoButton)).perform(click())

        intended(IntentMatchers.hasAction(MediaStore.ACTION_IMAGE_CAPTURE))
    }

    @Test
    fun checkNavigationToCreatePhotoAfterButtonClick() {
        Intents.init()

        onView(withId(R.id.makeNewPhotoButton)).perform(click())

        intended(IntentMatchers.hasAction(MediaStore.ACTION_IMAGE_CAPTURE))

        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack()

        onView(withId(R.id.createPhotoButton)).perform(click())

        intended(IntentMatchers.hasAction(MediaStore.ACTION_IMAGE_CAPTURE), times(2))
    }

    @Test
    fun checkErrorWhenTryToSaveEmptyName() {
        Intents.init()

        onView(withId(R.id.makeNewPhotoButton)).perform(click())

        intended(IntentMatchers.hasAction(MediaStore.ACTION_IMAGE_CAPTURE))

        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack()

        onView(withId(R.id.errorTextView)).check(matches(withEffectiveVisibility(Visibility.GONE)))

        onView(withId(R.id.savePhotoButton)).perform(click())

        onView(withId(R.id.errorTextView)).check(matches(isDisplayed()))
    }
}