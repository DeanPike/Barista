package com.adevinta.android.barista.assertion

import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.adevinta.android.barista.internal.assertAny
import com.adevinta.android.barista.internal.util.resourceMatcher
import org.hamcrest.Matchers.not

object BaristaEnabledAssertions {

  @JvmStatic
  fun assertEnabled(resId: Int) {
    resId.resourceMatcher().assertAny(isEnabled())
  }

  @JvmStatic
  fun assertEnabled(text: String) {
    withText(text).assertAny(isEnabled())
  }

  @JvmStatic
  fun assertDisabled(resId: Int) {
    resId.resourceMatcher().assertAny(not(isEnabled()))
  }

  @JvmStatic
  fun assertDisabled(text: String) {
    withText(text).assertAny(not(isEnabled()))
  }
}