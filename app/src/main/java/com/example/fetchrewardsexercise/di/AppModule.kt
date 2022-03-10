package com.example.fetchrewardsexercise.di

import com.example.fetchrewardsexercise.domain.repository.ItemRepositoryInterface
import com.example.fetchrewardsexercise.domain.use_cases.GetItemListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGetItemListUseCase(
        repositoryInterface: ItemRepositoryInterface
    ) = GetItemListUseCase(repositoryInterface)

}