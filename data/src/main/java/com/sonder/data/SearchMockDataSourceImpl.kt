package com.sonder.data

import com.sonder.domain.data.SearchMockDataSource
import com.sonder.domain.models.SearchSectionResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMockDataSourceImpl @Inject constructor(
    private val mockResponses: MockResponses
) : SearchMockDataSource {

    override fun getErrorResult(): Flow<Exception> = 
        mockResponses.getErrorResult()

    override fun getHorizontalCompactSearchResults(): Flow<SearchSectionResult> = 
        mockResponses.getHorizontalCompactSearchResults()

    override fun getVerticalCompactSearchResults(): Flow<SearchSectionResult> = 
        mockResponses.getVerticalCompactSearchResults()

    override fun getHorizontalDetailedSectionResults(): Flow<SearchSectionResult> = 
        mockResponses.getHorizontalDetailedSectionResults()

    override fun getVerticalDetailedSectionResults(): Flow<SearchSectionResult> = 
        mockResponses.getVerticalDetailedSectionResults()
} 