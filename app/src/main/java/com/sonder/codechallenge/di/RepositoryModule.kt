package com.sonder.codechallenge.di

import com.sonder.domain.data.SearchMockDataSource
import com.sonder.domain.repositories.SearchRepository
import com.sonder.domain.repositories.SearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideSearchRepository(
        mockDataSource: SearchMockDataSource
    ): SearchRepository {
        return SearchRepositoryImpl(mockDataSource)
    }
}