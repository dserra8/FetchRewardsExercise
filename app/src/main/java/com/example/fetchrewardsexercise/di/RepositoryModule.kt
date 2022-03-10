package com.example.fetchrewardsexercise.di

import com.example.fetchrewardsexercise.data.repository.ItemRepository
import com.example.fetchrewardsexercise.data.api.ItemApi
import com.example.fetchrewardsexercise.domain.repository.ItemRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        api: ItemApi
    ) : ItemRepositoryInterface = ItemRepository(api)
}