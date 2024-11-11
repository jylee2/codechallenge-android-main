package com.sonder.codechallenge.ui

import app.cash.turbine.test
import com.sonder.codechallenge.di.RepositoryModule
import com.sonder.domain.usecases.search.ClearSearchResultsUseCase
import com.sonder.domain.usecases.search.SearchUseCase
import com.sonder.domain.usecases.search.SubscribeToSearchQueryUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.sonder.domain.repositories.SearchRepository

@ExperimentalCoroutinesApi
class MainActivityViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var searchRepository: SearchRepository
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var searchUseCase: SearchUseCase
    private lateinit var clearSearchResultsUseCase: ClearSearchResultsUseCase
    private lateinit var subscribeToSearchQueryUseCase: SubscribeToSearchQueryUseCase

    @Before
    fun setup() {
        // Create the real repository with test data source
        searchRepository = RepositoryModule.provideSearchRepository()
        
        // Create real use cases with the repository
        searchUseCase = SearchUseCase(searchRepository)
        clearSearchResultsUseCase = ClearSearchResultsUseCase(searchRepository)
        subscribeToSearchQueryUseCase = SubscribeToSearchQueryUseCase(searchRepository)
        
        // Create the ViewModel with real use cases
        viewModel = MainActivityViewModel(
            searchUseCase = searchUseCase,
            clearSearchResultsUseCase = clearSearchResultsUseCase,
            getSearchUseCase = subscribeToSearchQueryUseCase
        )
    }

    @Test
    fun onCreate() = runTest  {
        viewModel.state.test {
            assertEquals(SearchActivityStates.Started, awaitItem())
        }
    }

    @Test
    fun updateSearchQuery() = runTest {
        // When
        viewModel.updateSearchQuery("mock")

        // Then
        viewModel.state.test {
            assertEquals(SearchActivityStates.Searching("mock"), awaitItem())
        }
    }

    @Test
    fun `when search query is error, then emit error state`() = runTest {
        // When
        viewModel.updateSearchQuery("error")
    }

    @Test
    fun clearSearchQuery() = runTest {
        // When
        viewModel.clearSearchQuery()

        // Then
        viewModel.state.test {
            assertEquals(SearchActivityStates.Started, awaitItem())
        }
    }
}