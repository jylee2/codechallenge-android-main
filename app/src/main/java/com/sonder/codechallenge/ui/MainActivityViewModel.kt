package com.sonder.codechallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonder.domain.models.SearchSectionResult
import com.sonder.domain.usecases.search.ClearSearchResultsUseCase
import com.sonder.domain.usecases.search.SearchUseCase
import com.sonder.domain.usecases.search.SubscribeToSearchQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SearchActivityStates {
    data object Started : SearchActivityStates()
    data class Searching(val query: String) : SearchActivityStates()
    data class Results(
        val query: String,
        val sections: List<SearchSectionResult>
    ) : SearchActivityStates()

    data class Error(val message: String) : SearchActivityStates()
}

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val clearSearchResultsUseCase: ClearSearchResultsUseCase,
    private val searchUseCase: SearchUseCase,
    private val getSearchUseCase: SubscribeToSearchQueryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<SearchActivityStates>(SearchActivityStates.Started)
    val state = _state.asStateFlow()

    fun onCreate() {
        _state.value = SearchActivityStates.Started
    }

    fun updateSearchQuery(query: String) {
        viewModelScope.launch {
            _state.value = SearchActivityStates.Searching(query)
            
            searchUseCase(query).collect { result ->
                _state.value = when {
                    result.isSuccess -> {
                        val sections = result.getOrNull().orEmpty()
                        if (sections.isEmpty()) {
                            SearchActivityStates.Error("We apologise, but it seems like we're facing a temporary hiccup loading search results.")
                        } else {
                            SearchActivityStates.Results(query, sections)
                        }
                    }
                    result.isFailure -> {
                        SearchActivityStates.Error("We apologise, but it seems like we're facing a temporary hiccup loading search results.")
                    }
                    else -> {
                        SearchActivityStates.Error("We apologise, but it seems like we're facing a temporary hiccup loading search results.")
                    }
                }
            }
        }
    }

    fun clearSearchQuery() {
        clearSearchResults()
        _state.value = SearchActivityStates.Started
    }

    private fun clearSearchResults() {
        viewModelScope.launch {
            clearSearchResultsUseCase.execute(Unit)
        }
    }

}