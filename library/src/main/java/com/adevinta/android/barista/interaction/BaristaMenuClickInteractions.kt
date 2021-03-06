package com.adevinta.android.barista.interaction

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.adevinta.android.barista.internal.failurehandler.SpyFailureHandler
import com.adevinta.android.barista.internal.failurehandler.withFailureHandler
import com.adevinta.android.barista.internal.matcher.DisplayedMatchers.displayedAnd
import com.adevinta.android.barista.internal.matcher.HelperMatchers.menuIdMatcher
import com.adevinta.android.barista.internal.matcher.withCompatText
import org.hamcrest.Matcher
import org.hamcrest.Matchers.hasToString

object BaristaMenuClickInteractions {

  @JvmStatic
  fun clickMenu(@IdRes id: Int) {
    val spyFailureHandler = SpyFailureHandler()
    try {
      clickDisplayedView(withId(id), spyFailureHandler)
    } catch (noMatchingViewException: NoMatchingViewException) {
      try {
        clickOverflowListMenu(menuIdMatcher(id), spyFailureHandler)
      } catch (error: Exception) {
        spyFailureHandler.resendFirstError("Could not click on menu id, neither as action or overflow")
      }
    }
  }

  @JvmStatic
  fun clickMenu(text: String) {
    val spyFailureHandler = SpyFailureHandler()
    try {
      clickDisplayedView(withCompatText(text), spyFailureHandler)
    } catch (noMatchingViewException: NoMatchingViewException) {
      try {
        clickViewWithDescription(text, spyFailureHandler)
      } catch (noMatchingViewByTextException: NoMatchingViewException) {
        try {
          clickOverflowListMenu(hasToString<String>(text), spyFailureHandler)
        } catch (error: Exception) {
          spyFailureHandler.resendFirstError("Could not click on menu title <$text>, neither as action or overflow")
        }
      }
    }
  }

  @JvmStatic
  fun openMenu() {
    openOverflow()
  }

  private fun clickDisplayedView(matcher: Matcher<View>, spyFailureHandler: SpyFailureHandler) {
    onView(displayedAnd(matcher)).withFailureHandler(spyFailureHandler).perform(click())
  }

  private fun clickViewWithDescription(text: String, spyFailureHandler: SpyFailureHandler) {
    onView(displayedAnd(withContentDescription(text))).withFailureHandler(spyFailureHandler).perform(click())
  }

  private fun clickOverflowListMenu(itemMatcher: Matcher<*>, spyFailureHandler: SpyFailureHandler) {
    openOverflow()
    withFailureHandler(spyFailureHandler) {
      onData(itemMatcher).inRoot(isPlatformPopup()).perform(click())
    }
  }

  private fun openOverflow() {
    openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext<Context>())
  }
}
