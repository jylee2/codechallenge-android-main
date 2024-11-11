package com.sonder.domain.repositories

import com.sonder.domain.models.SearchSectionResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    val searchQuery: Flow<String>
    suspend fun search(query: String): Flow<Result<List<SearchSectionResult>>>
    fun clearSectionSearchResults()
}