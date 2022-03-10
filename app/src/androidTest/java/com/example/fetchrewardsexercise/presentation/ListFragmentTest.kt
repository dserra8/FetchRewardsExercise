package com.example.fetchrewardsexercise.presentation


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import com.example.fetchrewardsexercise.R
import com.example.fetchrewardsexercise.data.repository.ItemRepositoryFakeAndroid
import com.example.fetchrewardsexercise.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.fetchrewardsexercise.data.adapter.ItemListAdapter


@ExperimentalCoroutinesApi
@HiltAndroidTest
class ListFragmentTest {

    @get:Rule val hiltRule = HiltAndroidRule(this)


    @Inject
    lateinit var repo: ItemRepositoryFakeAndroid

    @Before
    fun setup() {
        hiltRule.inject()
    }

    //Check if error message appears when theres an error
    @Test
    fun checkErrorMessageAppears() {
        repo.isNetworkError(true)
        launchFragmentInHiltContainer<ListFragment>()
        onView(withId(R.id.errorTextView)).check(matches(isDisplayed()))

    }

    //Check if no error message appears if no error
    @Test
    fun checkErrorMessageDoesNotAppear() {
        launchFragmentInHiltContainer<ListFragment>()
        onView(withId(R.id.errorTextView)).check(matches(not(isDisplayed())))
    }

    //Check if recycler view is displayed
    @Test
    fun isRecyclerViewVisible() {
        launchFragmentInHiltContainer<ListFragment>()
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

    }

    //Checks if recycler view has items
    @Test
    fun checkRecyclerViewHasItems() {
        launchFragmentInHiltContainer<ListFragment>()
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.scrollToPosition<ItemListAdapter.ItemViewHolder>(3)
        )
    }


}