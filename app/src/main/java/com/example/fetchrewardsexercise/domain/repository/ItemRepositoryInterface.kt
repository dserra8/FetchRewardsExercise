package com.example.fetchrewardsexercise.domain.repository

import android.content.ClipData
import com.example.fetchrewardsexercise.domain.models.Item
import com.example.fetchrewardsexercise.util.Resource
import kotlinx.coroutines.flow.Flow

interface ItemRepositoryInterface {
    suspend fun getItemList(): Resource<List<Item>>
}