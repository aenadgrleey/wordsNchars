package com.example.wordsnchars


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.wordsnchars.R
import com.aendgrleey.wordsnchars.R as AppResources

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ThreeSpansWithDeletion {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(TextConspectusAddingActivity::class.java)

    @Test
    fun threeSpansWithDeletion() {
        val materialTextView = onView(
            allOf(
                withId(R.id.highlight), withText("H"),
                childAtPosition(
                    allOf(
                        withId(R.id.textEditor),
                        childAtPosition(
                            withClassName(`is`("android.widget.HorizontalScrollView")),
                            0
                        )
                    ),
                    2
                )
            )
        )
        materialTextView.perform(scrollTo(), click())

        val materialButton = onView(
            allOf(
                withId(R.id.colorBlue),
                childAtPosition(
                    allOf(
                        withId(R.id.holder),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(AppResources.id.editText),
                childAtPosition(
                    allOf(
                        withId(AppResources.id.scrollView2),
                        childAtPosition(
                            withId(AppResources.id.constraintLayout2),
                            0
                        )
                    ),
                    0
                )
            )
        )
        appCompatEditText.perform(
            scrollTo(),
            replaceText("centar@ff.uns.ac.rsлал"),
            closeSoftKeyboard()
        )

        val materialTextView2 = onView(
            allOf(
                withId(R.id.highlight), withText("H"),
                childAtPosition(
                    allOf(
                        withId(R.id.textEditor),
                        childAtPosition(
                            withClassName(`is`("android.widget.HorizontalScrollView")),
                            0
                        )
                    ),
                    2
                )
            )
        )
        materialTextView2.perform(scrollTo(), click())

        val materialButton2 = onView(
            allOf(
                withId(R.id.colorGreen),
                childAtPosition(
                    allOf(
                        withId(R.id.holder),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val appCompatEditText2 = onView(
            allOf(
                withId(AppResources.id.editText), withText("centar@ff.uns.ac.rsлал"),
                childAtPosition(
                    allOf(
                        withId(AppResources.id.scrollView2),
                        childAtPosition(
                            withId(AppResources.id.constraintLayout2),
                            0
                        )
                    ),
                    0
                )
            )
        )
        appCompatEditText2.perform(
            scrollTo(),
            replaceText("centar@ff.uns.ac.rsлалcentar@ff.uns.ac.rs")
        )

        val appCompatEditText3 = onView(
            allOf(
                withId(AppResources.id.editText), withText("centar@ff.uns.ac.rsлалcentar@ff.uns.ac.rs"),
                childAtPosition(
                    allOf(
                        withId(AppResources.id.scrollView2),
                        childAtPosition(
                            withId(AppResources.id.constraintLayout2),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(closeSoftKeyboard())

        val materialTextView3 = onView(
            allOf(
                withId(R.id.highlight), withText("H"),
                childAtPosition(
                    allOf(
                        withId(R.id.textEditor),
                        childAtPosition(
                            withClassName(`is`("android.widget.HorizontalScrollView")),
                            0
                        )
                    ),
                    2
                )
            )
        )
        materialTextView3.perform(scrollTo(), click())

        val materialButton3 = onView(
            allOf(
                withId(R.id.colorPink),
                childAtPosition(
                    allOf(
                        withId(R.id.holder),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val appCompatEditText4 = onView(
            allOf(
                withId(AppResources.id.editText), withText("centar@ff.uns.ac.rsлалcentar@ff.uns.ac.rs"),
                childAtPosition(
                    allOf(
                        withId(AppResources.id.scrollView2),
                        childAtPosition(
                            withId(AppResources.id.constraintLayout2),
                            0
                        )
                    ),
                    0
                )
            )
        )
        appCompatEditText4.perform(
            scrollTo(),
            replaceText("centar@ff.uns.ac.rsлалcentar@ff.uns.ac.rsпш")
        )

        val appCompatEditText5 = onView(
            allOf(
                withId(AppResources.id.editText), withText("centar@ff.uns.ac.rsлалcentar@ff.uns.ac.rsпш"),
                childAtPosition(
                    allOf(
                        withId(AppResources.id.scrollView2),
                        childAtPosition(
                            withId(AppResources.id.constraintLayout2),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(closeSoftKeyboard())

        val appCompatEditText6 = onView(
            allOf(
                withId(AppResources.id.editText), withText("centar@ff.uns.ac.rsлалcentar@ff.uns.ac.rsпш"),
                childAtPosition(
                    allOf(
                        withId(AppResources.id.scrollView2),
                        childAtPosition(
                            withId(AppResources.id.constraintLayout2),
                            0
                        )
                    ),
                    0
                )
            )
        )
        appCompatEditText6.perform(scrollTo(), click())

        val appCompatEditText7 = onView(
            allOf(
                withId(AppResources.id.editText), withText("centar@ff.uns.ac.rsлалcentar@ff.uns.ac.rsпш"),
                childAtPosition(
                    allOf(
                        withId(AppResources.id.scrollView2),
                        childAtPosition(
                            withId(AppResources.id.constraintLayout2),
                            0
                        )
                    ),
                    0
                )
            )
        )
        appCompatEditText7.perform(scrollTo(), click())

        val appCompatEditText8 = onView(
            allOf(
                withId(AppResources.id.editText), withText("centar@ff.uns.ac.rsлалcentar@ff.uns.ac.rsпш"),
                childAtPosition(
                    allOf(
                        withId(AppResources.id.scrollView2),
                        childAtPosition(
                            withId(AppResources.id.constraintLayout2),
                            0
                        )
                    ),
                    0
                )
            )
        )
        appCompatEditText8.perform(scrollTo(), replaceText("centar@ff.uns.ac.rsлалcentar@ff.unsпш"))

        val appCompatEditText9 = onView(
            allOf(
                withId(AppResources.id.editText), withText("centar@ff.uns.ac.rsлалcentar@ff.unsпш"),
                childAtPosition(
                    allOf(
                        withId(AppResources.id.scrollView2),
                        childAtPosition(
                            withId(AppResources.id.constraintLayout2),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText9.perform(closeSoftKeyboard())

        val editText = onView(
            allOf(
                withId(AppResources.id.editText), withText("centar@ff.uns.ac.rsлалcentar@ff.unsпш"),
                withParent(
                    allOf(
                        withId(AppResources.id.scrollView2),
                        withParent(withId(AppResources.id.constraintLayout2))
                    )
                ),
                isDisplayed()
            )
        )
        editText.check(matches(withText("centar@ff.uns.ac.rsлалcentar@ff.unsпш")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
