package com.example.fetchrewardsexercise.data.repository

import com.example.fetchrewardsexercise.domain.models.Item
import com.example.fetchrewardsexercise.domain.repository.ItemRepositoryInterface
import com.example.fetchrewardsexercise.util.Resource

/**
 * Exactly the same as ItemRepositoryFake, but have to make a new class so I can
 * access it in integrated tests. Replaces ItemRepository and makes it more efficient by not having to call
 * real api.
 */
class ItemRepositoryFakeAndroid: ItemRepositoryInterface {
    //This will simulate a network error
    private var networkError = false

    //This list will represent the data from the json file
    private val itemList = listOf(
        Item(id = 1, listId = 1, name = "Item 1"),
        Item(id = 100, listId = 1, name = "Item 100"),
        Item(id = 25, listId = 1, name = "Item 25"),
        Item(id = 11, listId = 1, name = ""),
        Item(id = 110, listId = 1),
        Item(id = 2, listId = 2, name = "Item 2"),
        Item(id = 102, listId = 2, name = "Item 102"),
        Item(id = 26, listId = 2, name = "Item 26"),
        Item(id = 12, listId = 2, name = ""),
        Item(id = 111, listId = 2),
        Item(id = 3, listId = 3, name = "Item 3"),
        Item(id = 103, listId = 3, name = "Item 103"),
        Item(id = 27, listId = 3, name = "Item 27"),
        Item(id = 13, listId = 3, name = ""),
        Item(id = 112, listId = 3),
        Item(id = 4, listId = 4, name = "Item 4"),
        Item(id = 104, listId = 4, name = "Item 104"),
        Item(id = 28, listId = 4, name = "Item 28"),
        Item(id = 14, listId = 4, name = ""),
        Item(id = 113, listId = 4),
    )

    private val correctList = listOf(
        Item(id = 1, listId = 1, name = "Item 1"),
        Item(id = 25, listId = 1, name = "Item 25"),
        Item(id = 100, listId = 1, name = "Item 100"),
        Item(id = 2, listId = 2, name = "Item 2"),
        Item(id = 26, listId = 2, name = "Item 26"),
        Item(id = 102, listId = 2, name = "Item 102"),
        Item(id = 3, listId = 3, name = "Item 3"),
        Item(id = 27, listId = 3, name = "Item 27"),
        Item(id = 103, listId = 3, name = "Item 103"),
        Item(id = 4, listId = 4, name = "Item 4"),
        Item(id = 28, listId = 4, name = "Item 28"),
        Item(id = 104, listId = 4, name = "Item 104"),
    )

    fun isNetworkError(ans: Boolean) {
        networkError = ans
    }

    fun getCorrectList() = correctList

    override suspend fun getItemList(): Resource<List<Item>> {
        return if(networkError) {
            Resource.Error(Throwable("Network Error"))
        } else {
            Resource.Success(itemList)
        }
    }
}