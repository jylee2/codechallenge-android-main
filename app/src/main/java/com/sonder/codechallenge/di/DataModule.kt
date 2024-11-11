package com.sonder.codechallenge.di

import com.sonder.data.MockResponses
import com.sonder.data.SearchMockDataSourceImpl
import com.sonder.domain.data.SearchMockDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideSearchMockDataSource(): SearchMockDataSource {
        return SearchMockDataSourceImpl(MockResponses)
    }
} 