package com.example.fetchrewardsexercise.domain.use_cases

import androidx.annotation.VisibleForTesting
import com.example.fetchrewardsexercise.domain.models.Item
import com.example.fetchrewardsexercise.domain.repository.ItemRepositoryInterface
import com.example.fetchrewardsexercise.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


/**
 * Use case will handle all the data manipulation
 */
class GetItemListUseCase(
    private val repo: ItemRepositoryInterface
) {

    /**
     * Overriding invoke operator so I can use this class like a function invocation: GetItemListUseCase()
     */
    operator fun invoke(): Flow<Resource<List<Item>>> {
        return flow {

            //Emitting Loading() before everything so the fragment can turn on the loading progress bar
            emit(Resource.Loading(emptyList()))

            //Theres only 2 possible types that response can be because I never return Loading() in repository
            when(val response = repo.getItemList()) {
                is Resource.Error -> {
                    //Emitting Error message
                    emit(Resource.Error(response.error ?: Throwable("Unknown Error")))
                }
                else -> {
                    //Filtering out data with blank name or null name
                    val list = response.data!!.filter { it.name != null && it.name != "" }

                    //Segmenting the list into multiple list by listID and sorting each one by name and merging them back into one list
                    val map = hashMapOf<Int,MutableList<Item>>()
                    separateLists(map, list)

                    //Emitting result
                    emit(Resource.Success(sortLists(map)))
                }
            }

        }.flowOn(Dispatchers.IO)
    }

    /**
     *  Segmenting the list by listID so I can sort each individual list by
     *  name. Using a hashmap so I can easily segment them.
     */

    fun separateLists( map: HashMap<Int,MutableList<Item>>, originalList: List<Item>) {
        originalList.forEach {
            //If list with listId does not exist, it will insert one
            //it will add the item to the corresponding list
            map.getOrPut(it.listId){
                mutableListOf()
            }.add(it)
        }
    }

    /**
     * Sorting each List by Name but using Id value because that's what differentiates the names
     * Then merging all list into one
     */
    private fun sortLists(map: HashMap<Int, MutableList<Item>>): List<Item> {
        val res = mutableListOf<Item>()
        map.forEach { list ->
            list.value.sortBy { it.id }
            res.addAll(list.value)
        }
        return res
    }
}