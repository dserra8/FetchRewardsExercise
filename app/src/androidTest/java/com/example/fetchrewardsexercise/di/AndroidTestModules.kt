package com.example.fetchrewardsexercise.di

import com.example.fetchrewardsexercise.data.repository.ItemRepositoryFakeAndroid
import com.example.fetchrewardsexercise.domain.repository.ItemRepositoryInterface
import com.example.fetchrewardsexercise.domain.use_cases.GetItemListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object TestRepositoryModule {
    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideTestRepository(): ItemRepositoryFakeAndroid = ItemRepositoryFakeAndroid()
}

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object AndroidAppModule {

    @Provides
    @Singleton
    fun provideGetItemListUseCase(
        repository: ItemRepositoryFakeAndroid
    ) = GetItemListUseCase(repository)

}