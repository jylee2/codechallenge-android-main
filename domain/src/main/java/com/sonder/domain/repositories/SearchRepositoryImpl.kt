package com.sonder.domain.repositories

import com.sonder.domain.models.SearchSectionResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor() : SearchRepository {

    private val _searchQuery = MutableStateFlow("")
    override val searchQuery = _searchQuery.asStateFlow()

    override suspend fun search(query: String): Flow<Result<List<SearchSectionResult>>> = flow {
        delay(2000L) // Simulate network delay
        emit(
            if (query == "error") Result.failure(Exception("Error fetching results"))
            else Result.success(emptyList())
        )
    }

    override fun clearSectionSearchResults() {
        _searchQuery.value = ""
    }

}