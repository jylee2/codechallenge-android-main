package com.sonder.domain.repositories

import com.sonder.domain.models.SearchSectionResult
import kotlinx.coroutines.flow.Flow

// TODO Implement SearchRepository. Change as needed.
interface SearchRepository {
    val searchQuery: Flow<String>
    suspend fun search(query: String): Flow<Result<List<SearchSectionResult>>>
    fun clearSectionSearchResults()
}