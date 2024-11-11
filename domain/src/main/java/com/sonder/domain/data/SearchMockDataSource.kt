package com.sonder.domain.data

import com.sonder.domain.models.SearchSectionResult
import kotlinx.coroutines.flow.Flow

interface SearchMockDataSource {
    fun getHorizontalCompactSearchResults(): Flow<SearchSectionResult>
    fun getVerticalCompactSearchResults(): Flow<SearchSectionResult>
    fun getHorizontalDetailedSectionResults(): Flow<SearchSectionResult>
    fun getVerticalDetailedSectionResults(): Flow<SearchSectionResult>
    fun getErrorResult(): Flow<Exception>
} 