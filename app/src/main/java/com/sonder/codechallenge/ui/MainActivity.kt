package com.sonder.codechallenge.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sonder.codechallenge.ui.components.SearchBar
import com.sonder.codechallenge.ui.components.SearchHeader
import com.sonder.codechallenge.ui.components.SearchResults
import com.sonder.codechallenge.ui.theme.SonderCodeChallengeTheme
import com.sonder.domain.models.SearchActionType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SonderCodeChallengeTheme {
                MainScreen(viewModel)
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainActivityViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { query ->
                    searchQuery = query
                    if (query.isEmpty()) {
                        viewModel.clearSearchQuery()
                    }
                },
                onSearch = { query ->
                    if (query.length > 2) {
                        viewModel.updateSearchQuery(query)
                    } else {
                        viewModel.clearSearchQuery()
                    }
                },
                onFocusChange = { }
            )

            when (state) {
                is SearchActivityStates.Started -> {
                    SearchHeader(
                        title = "Search our comprehensive library of resources",
                        isLoading = false
                    )
                }

                is SearchActivityStates.Searching -> {
                    SearchHeader(
                        title = "Searching for '${(state as SearchActivityStates.Searching).query}'",
                        isLoading = true
                    )
                }

                is SearchActivityStates.Results -> {
                    val resultsState = state as SearchActivityStates.Results
                    SearchHeader(
                        title = "Showing results for '${resultsState.query}'",
                        isLoading = false
                    )
                    SearchResults(
                        sections = resultsState.sections,
                        onItemClick = { item ->
                            // Handle item click based on action type
                            when (item.action?.type) {
                                SearchActionType.SERIES -> TODO()
                                SearchActionType.ARTICLE -> TODO()
                                SearchActionType.CATEGORY_HUB -> TODO()
                                SearchActionType.DEEPLINK -> TODO()
                                SearchActionType.ASSESSMENT -> TODO()
                                SearchActionType.PHONE_CALL -> TODO()
                                null -> Unit
                            }
                        }
                    )
                }

                is SearchActivityStates.Error -> {
                    SearchHeader(
                        title = (state as SearchActivityStates.Error).message,
                        isLoading = false
                    )
                }
            }
        }
    }
}