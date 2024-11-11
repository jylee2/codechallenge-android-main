package com.sonder.domain.repositories

import com.sonder.domain.data.SearchMockDataSource
import com.sonder.domain.models.SearchContentType
import com.sonder.domain.models.SearchRequestParams
import com.sonder.domain.models.SearchSectionResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val mockDataSource: SearchMockDataSource
) : SearchRepository {

    private val _searchQuery = MutableStateFlow("")
    override val searchQuery = _searchQuery.asStateFlow()

    override suspend fun search(query: String): Flow<Result<List<SearchSectionResult>>> = flow {
        _searchQuery.value = query

        val result = withContext(Dispatchers.IO) {
            delay(2000L) // Simulate network delay

            when (query.lowercase()) {
                "error" -> Result.failure(mockDataSource.getErrorResult().first())
                "mock" -> Result.success(
                    listOf(
                        getSearchResults(
                            SearchRequestParams(
                                sectionTitle = "Topics",
                                query = query,
                                size = 3,
                                contentTypes = listOf(SearchContentType.CATEGORY_HUB.value)
                            )
                        ),
                        getSearchResults(
                            SearchRequestParams(
                                sectionTitle = "Categories",
                                query = query,
                                size = 4,
                                contentTypes = listOf(
                                    SearchContentType.ARTICLE.value,
                                    SearchContentType.CATEGORY_HUB.value,
                                    SearchContentType.ACTION.value
                                )
                            )
                        ),
                        getSearchResults(
                            SearchRequestParams(
                                sectionTitle = "Tools and services",
                                query = query,
                                size = 6,
                                contentTypes = listOf(
                                    SearchContentType.ASSESSMENT.value,
                                    SearchContentType.ACTION.value,
                                    SearchContentType.CALL_PATHWAY.value,
                                    SearchContentType.SERIES.value,
                                    SearchContentType.ARTICLE.value
                                )
                            )
                        ),
                        getSearchResults(
                            SearchRequestParams(
                                sectionTitle = "Resources",
                                query = query,
                                size = 10,
                                contentTypes = listOf(
                                    SearchContentType.ARTICLE.value,
                                    SearchContentType.CALL_PATHWAY.value
                                )
                            )
                        )
                    )
                )

                else -> Result.success(emptyList())
            }
        }

        emit(result)
    }

    private suspend fun getSearchResults(params: SearchRequestParams): SearchSectionResult {
        return withContext(Dispatchers.IO) {
            when (params.sectionTitle) {
                "Topics" -> mockDataSource.getHorizontalCompactSearchResults().first()
                "Categories" -> mockDataSource.getVerticalCompactSearchResults().first()
                "Tools and services" -> mockDataSource.getHorizontalDetailedSectionResults().first()
                "Resources" -> mockDataSource.getVerticalDetailedSectionResults().first()
                else -> SearchSectionResult(
                    sectionTitle = params.sectionTitle,
                    sectionDescription = null,
                    items = emptyList()
                )
            }
        }
    }

    override fun clearSectionSearchResults() {
        _searchQuery.value = ""
    }
}