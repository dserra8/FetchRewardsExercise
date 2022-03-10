package com.example.fetchrewardsexercise.domain.use_cases

import com.example.fetchrewardsexercise.data.repository.ItemRepositoryFake
import com.example.fetchrewardsexercise.domain.models.Item
import com.example.fetchrewardsexercise.domain.repository.ItemRepositoryInterface
import com.example.fetchrewardsexercise.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.testng.annotations.BeforeTest

@ExperimentalCoroutinesApi
class GetItemListUseCaseTest {

    private lateinit var getItemListUseCase: GetItemListUseCase

    private lateinit var repo: ItemRepositoryFake

    @Before
    fun setup() {
        repo = ItemRepositoryFake()

        getItemListUseCase = GetItemListUseCase(repo)
    }

    @Test
    fun `Call getItemListUseCase(), returns sorted and filtered list`() = runTest {
        //Getting all emitted values and storing them in list for easy access
        val results = getItemListUseCase().toList()

        //Checking that only 2 values are emitted
        assertThat(results.size).isEqualTo(2)

        //Checking if first emitted value is Loading class
        assertThat(results[0]).isInstanceOf(Resource.Loading::class.java)

        //Checking if first emitted value is empty list
        assertThat(results[0].data).isEqualTo(emptyList<Item>())

        //Checking if second emitted value is Success class
        assertThat(results[1]).isInstanceOf(Resource.Success::class.java)

        //Checking if last emitted value is the correct list
        assertThat(results[1].data).isEqualTo(repo.getCorrectList())
    }

    @Test
    fun `Call getItemListUseCase() with Network Error, returns error`() = runTest {
        repo.isNetworkError(true)

        val results = getItemListUseCase().toList()

        //Checking that only 2 values are emitted
        assertThat(results.size).isEqualTo(2)


        //Checking if first emitted value is Loading class
        assertThat(results[0]).isInstanceOf(Resource.Loading::class.java)

        //Checking if first emitted value of emptyList
        assertThat(results[0].data).isEqualTo(emptyList<Item>())

        //Checking if second emitted value is Error class
        assertThat(results[1]).isInstanceOf(Resource.Error::class.java)

        //Checking that an error has null data and correct message
        assertThat(results[1].data).isNull()
        assertThat(results[1].error?.message).isEqualTo("Network Error")
    }
}