package com.example.fetchrewardsexercise.data.api

import com.example.fetchrewardsexercise.domain.models.Item
import retrofit2.Response
import retrofit2.http.GET

interface ItemApi {
    @GET("/hiring.json")
    suspend fun getItemList(): Response<List<Item>>
}