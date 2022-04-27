package com.example.myapplication

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.myapplication.activities.LoginActivity
import com.example.myapplication.activities.MainActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class LoginActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun test_isActivityInView() {
        onView(withId(R.id.loginPage)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility() {
        onView(withId(R.id.authForm))
            .check(matches(isDisplayed()))

        onView(withId(R.id.loginFragment))
            .check(matches(isDisplayed()))

        onView(withId(R.id.Welcome))
            .check(matches(isDisplayed()))

        onView(withId(R.id.subtitle))
            .check(matches(isDisplayed()))

        onView(withId(R.id.login))
            .check(matches(isDisplayed()))

        onView(withId(R.id.password))
            .check(matches(isDisplayed()))

        onView(withId(R.id.login_button))
            .check(matches(isDisplayed()))

        onView(withId(R.id.to_register_button))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_Fill() {
        onView(withId(R.id.Welcome))
            .check(matches(withText(R.string.welcome)))

        onView(withId(R.id.subtitle))
            .check(matches(withText(R.string.Login_form_subtitle)))

    }
}