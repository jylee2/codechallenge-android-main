package com.sonder.data

import com.sonder.domain.data.SearchMockDataSource
import com.sonder.domain.models.SearchSectionResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMockDataSourceImpl @Inject constructor(
    private val mockResponses: MockResponses
) : SearchMockDataSource {
    override fun getErrorResult(): Flow<Exception> = flow {
        mockResponses.getErrorResult()
    }

    override fun getHorizontalCompactSearchResults(): Flow<SearchSectionResult> = flow {
        mockResponses.getHorizontalCompactSearchResults()
    }

    override fun getVerticalCompactSearchResults(): Flow<SearchSectionResult> = flow {
        mockResponses.getVerticalCompactSearchResults()
    }

    override fun getHorizontalDetailedSectionResults(): Flow<SearchSectionResult> = flow {
        mockResponses.getHorizontalDetailedSectionResults()
    }

    override fun getVerticalDetailedSectionResults(): Flow<SearchSectionResult> = flow {
        mockResponses.getVerticalDetailedSectionResults()
    }
} 