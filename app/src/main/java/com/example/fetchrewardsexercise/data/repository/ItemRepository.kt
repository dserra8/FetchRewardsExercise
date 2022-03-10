package com.example.fetchrewardsexercise.data.repository

import com.example.fetchrewardsexercise.data.api.ItemApi
import com.example.fetchrewardsexercise.domain.models.Item
import com.example.fetchrewardsexercise.domain.repository.ItemRepositoryInterface
import com.example.fetchrewardsexercise.util.Resource
import retrofit2.Response

/**
 * Repository class that conforms to ItemRepositoryInterface.
 * Repository purpose is to gather all data from every source and send it to UseCase/ViewModel
 */
class ItemRepository(
    private val api: ItemApi
): ItemRepositoryInterface {

    override suspend fun getItemList(): Resource<List<Item>> {

        //Try catch block will return Error() if error is detected, otherwise it continuous
        val response: Response<List<Item>>
        try {
            response = api.getItemList()
        } catch (e: Exception) {
            return Resource.Error(e)
        }

        //If response is successful, then I return Success(data)
        //If for some reason, data is still null, I return an emptyList to avoid crash
        return if(response.isSuccessful){
            Resource.Success(response.body() ?: emptyList())
        } else {
            Resource.Error(Throwable("Unknown Error"))
        }
    }
}