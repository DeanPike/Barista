package com.schibsted.spain.barista.sample;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition;
import static com.schibsted.spain.barista.interaction.BaristaSleepInteractions.sleep;
import static com.schibsted.spain.barista.interaction.BaristaViewPagerInteractions.swipeViewPagerBack;
import static com.schibsted.spain.barista.interaction.BaristaViewPagerInteractions.swipeViewPagerForward;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewWithDifferentDataInsideViewPagerTest {

  @Rule
  public ActivityTestRule<RecyclerViewsWithDifferentDataInsideViewPagerActivity> activityRule =
      new ActivityTestRule<>(RecyclerViewsWithDifferentDataInsideViewPagerActivity.class);

  @Test
  public void checkClickRecyclerViewItem() {
    swipeViewPagerForward(R.id.pager);
    sleep(500);

    assertDisplayedAtPosition(R.id.recycler, 1, "Marionberry");

    swipeViewPagerBack(R.id.pager);
    sleep(500);

    assertDisplayedAtPosition(R.id.recycler, 0, R.id.textview, "Apple");
  }
}