package be.tim.beers

import android.view.Gravity
import androidx.navigation.findNavController
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("be.tim.beers", appContext.packageName)
    }

    @Test
    fun beersScreenClickOnHomeIconOpensNavigation() {
        // start up Beers screen
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        // Navigate to beers screen
        activityScenario.onActivity {
            it.findNavController(R.id.fragment).navigate(R.id.beersFragment)
        }

        // Check if left drawer is closed
        Espresso.onView(withId(R.id.drawer_layout))
                .check(ViewAssertions.matches(isClosed(Gravity.START)))

        // Open drawer
        Espresso.onView(
                withContentDescription(
                        activityScenario
                                .getToolbarNavigationContentDescription()
                )
        ).perform(ViewActions.click())

        // Check if drawer is open
        Espresso.onView(withId(R.id.drawer_layout))
                .check(ViewAssertions.matches(isOpen(Gravity.START)))
    }

}